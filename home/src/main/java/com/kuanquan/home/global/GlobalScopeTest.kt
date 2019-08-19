package com.kuanquan.home.global

import com.base.library.utils.LogUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlin.system.measureTimeMillis

class GlobalScopeTest {

    fun start() {
        // 通常启动协程有 launch 和 async 方法
        GlobalScope.launch {
            LogUtil.e("挂起之前")
            delay(10000)  // 非阻塞 delay函数就是一个挂起函数，它用suspend关键字修饰，挂起函数只能从一个协程代码内部调用，普通代码不能调用
            LogUtil.e("挂起之后")
        }
    }

    fun main() {
        GlobalScope.launch {
            // 在后台启动一个新的协程并继续
            delay(1000)
            println("world")
        }

        println("hello")  // 主线程中的代码会立即执行
//        runBlocking {     // 但是这个表达式阻塞了主线程
//            delay(2000)  // ……我们延迟 2 秒来保证 JVM 的存活
//        }
    }

    // 调用了 runBlocking 的主线程会一直 阻塞 直到 runBlocking 内部的协程执行完毕
    // 这里的 runBlocking<Unit> { .. } 作为用来启动顶层主协程的适配器。我们显式指定了其返回类型 Unit，因为在 Kotlin 中 main 函数必须返回 Unit 类型
    fun test() = runBlocking<Unit> {
        // 开始执行主协程
        GlobalScope.launch {
            // 在后台启动一个新的协程并继续
            delay(1000L)  // 非阻塞挂起函数
            println("world")
        }
        println("hello")
        delay(2000L)
    }

    // TODO 等待一个作业  这里的 runBlocking<Unit> { .. } 作为用来启动顶层主协程的适配器
    fun joinTo() = runBlocking<Unit> {
        // 延迟一段时间来等待另一个协程运行并不是一个好的选择。让我们显式（以非阻塞方式）等待所启动的后台 Job 执行结束
        val job = GlobalScope.launch {
            delay(1000L)
            println("world")
        }

        println("hello")
        job.join() // 等待直到子协程执行结束
    }

    // TODO 结构化并发
    // 我们可以在执行操作所在的指定作用域内启动协程， 而不是像通常使用线程（线程总是全局的）那样在 GlobalScope 中启动
    // 使用 runBlocking 协程构建器将 mainTest 函数转换为协程
    // 包括 runBlocking 在内的每个协程构建器都将 CoroutineScope 的实例添加到其代码块所在的作用域中。
    // 我们可以在这个作用域中启动协程而无需显式 join 之，因为外部协程（示例中的 runBlocking）直到在其作用域中启动的所有协程都执行完毕后才会结束
    fun mainTest() = runBlocking {
        // this: CoroutineScope
        launch {
            // 在 runBlocking 范围内启动一个新的协同程序, 而不是在 GlobalScope 中启动
            delay(1000L)
            println("结构化并发 end!")
        }
        println("结构化并发 start,")
    }

    // TODO 作用域构建器
    // 除了由不同的构建器提供协程作用域之外，还可以使用 coroutineScope 构建器声明自己的作用域。
    // coroutineScope 会创建一个协程作用域并且在所有已启动子协程执行完毕之前不会结束。
    // runBlocking 与 coroutineScope 的主要区别在于后者在等待所有子协程执行完毕时不会阻塞当前线程。
    fun scopeTest() = runBlocking {
        launch {
            delay(100L)
            println("runBlocking 作用域构建器 launch!")
        }

        coroutineScope {
            // 创建一个协程作用域

            launch {
                delay(200L)
                println("coroutineScope 作用域构建器 launch!")
            }

            delay(50L)
            println("coroutineScope 作用域构建器 scope!")  // 这一行会在内嵌 launch 之前输出
        }

        println("runBlocking 作用域构建器 scope!")  // 这一行在内嵌 launch 执行完毕后才输出
    }

    // TODO 提取函数重构
    fun restructure() = runBlocking {
        launch {
            doWorld()
        }
        println("提取函数重构 -> hello")
    }

    // TODO 这是一个挂起函数 用 suspend 修饰
    suspend fun doWorld() {
        delay(1000L)
        println("提取函数重构 = world")
    }

    // TODO 全局协程 像 守护线程
    fun daemonThread() = runBlocking {
        GlobalScope.launch {
            repeat(10) { i ->
                println("全局协程 -> $i")
                kotlinx.coroutines.delay(500L)
            }
        }
        delay(1300L)
        // 这样可以执行三次，全局协程 失效时间 1300 上面的计数方法是 500毫秒一次，所以可以执行三次
    }

    // TODO lambda 表达式 闭包函数的使用
    fun bolock() {
        // Lambda 表达式或者匿名函数（以及局部函数和对象表达式） 可以访问其 闭包 ，
        // 即在外部作用域中声明的变量。 与 Java 不同的是可以修改闭包中捕获的变量
        var ints = intArrayOf(1, 2, 3)
        var sum = 0
        ints.filter { it > 0 }.forEach {
            // 对数组过滤求和
            sum += it
        }
        LogUtil.e("和 = $sum")
    }

    // TODO 取消协程
    fun cancel() = runBlocking {
        val job = launch {
            repeat(10) {
                println("取消协程 -> $it")
                delay(500L)
            }
        }
//        delay(1300L)
        println("main: I'm tired of waiting!")
//        job.cancel() // 取消该作业
//        job.join() // 等待作业执行结束
        job.cancelAndJoin() // 合并了对 cancel 以及 join 的调用   取消一个作业并且等待它结束
        println("main: Now I can quit.")
    }

    // TODO 通道  延期的值提供了一种便捷的方法使单个值在多个协程之间进行相互传输。 通道提供了一种在流中传输值的方法
    // 一个 Channel 是一个和 BlockingQueue 非常相似的概念。
    // 其中一个不同是它代替了阻塞的 put 操作并提供了挂起的 send，还替代了阻塞的 take 操作并提供了挂起的 receive
    // 通道基础
    fun channelBasics() = runBlocking {
        val channel = Channel<Int>()
        launch {
            for (i in 1..5) channel.send(i * i)
        }
        repeat(5) {
            println(channel.receive())
        }
        println("done")
    }

    // TODO 关闭与迭代通道
    fun channelClose() = runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) channel.send(x * x)
            channel.close()
        }

        for (y in channel) println(y)
        println("done")
    }


    // TODO 构建通道生产者
    fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
        for (x in 1..5) send(x * x)
    }

    fun channelProduce() = runBlocking {
        val squares = produceSquares()
        squares.consumeEach { println(it) }
        println("Done!")
    }

    // TODO 管道 管道是一种一个协程在流中开始生产可能无穷多个元素的模式：
    fun CoroutineScope.produceNumbers() = produce<Int> {
        var x = 1
        while (true) send(x++) // 在流中开始从 1 生产无穷多个整数
    }

    fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
        for (x in numbers) send(x * x)
    }

    fun conduit() = runBlocking {
        val numbers = produceNumbers() // 从 1 开始生产整数
        val squares = square(numbers) // 对整数做平方
        for (i in 1..5) println(squares.receive()) // 打印前 5 个数字
        println("Done!") // 我们的操作已经结束了
        coroutineContext.cancelChildren() // 取消子协程
    }

    // TODO 扇出
    // 多个协程也许会接收相同的管道，在它们之间进行分布式工作。 让我们启动一个定期产生整数的生产者协程 （每秒十个数字）：
    fun CoroutineScope.produceNum() = produce<Int> {
        var x = 1 // start from 1
        while (true) {
            send(x++) // 产生下一个数字
            delay(100) // 等待 0.1 秒
        }
        // 此函数就是一个管道
    }

    // 接下来我们可以得到几个生产者协程。在这个示例中，它们只是打印它们的 id 和接收到的数字：
    fun CoroutineScope.launchprocessor(id: Int, channel: ReceiveChannel<Int>) = launch {
        for (msg in channel) {
            println("Processor #$id received $msg")
        }
    }

    fun fanOut() = runBlocking<Unit> {
        val producer = produceNum()
        repeat(5) { launchprocessor(it, producer) }
        delay(950)
        producer.cancel() // 取消协程生产者从而将它们全部杀死
    }

    // TODO 扇入 多个协程可以发送到同一个通道。
    // 创建一个字符串通道，和一个在这个通道中以指定的延迟反复发送一个指定字符串的 挂起函数：
    suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
        while (true) {
            delay(time)    // 延迟时间
            channel.send(s) // 发送一个字符串
        }
    }

    // 启动了2个发送字符串的协程
    fun fanIn() = runBlocking {
        val channel = Channel<String>()
        launch {
            // 开启一个协程
            sendString(channel, "协程1", 1000)
        }

        launch {
            // 开启另外一个协程
            sendString(channel, "协程2", 1000)
        }

        repeat(5) {
            // 接收前五个
            println(channel.receive())
        }

        coroutineContext.cancelChildren() // 取消所有子协程来让主协程结束
    }

    // TODO 带缓冲的通道
    fun channelCache() = runBlocking {
        val channel = Channel<Int>(4) // 设置缓存大小为 4   启动带缓冲的通道
        val send = launch {  // 启动发送者协程
            repeat(10){
                println("$it")   // 在每一个元素发送前打印它们
                channel.send(it) // 将在缓存区占满时挂起
            }
        }
        // 没有接收到东西，只是等待
        delay(1000)
//        for(xx in channel) println("接收缓存的携程发送数据 -> $xx")
//        send.cancel() // 取消发送者协程
        coroutineContext.cancelChildren()
    }

    // TODO   使用 async 并发  使用协程进行并发总是显式的
    suspend fun oneInt(): Int{
        delay(500)
        return 10
    }
    suspend fun twoInt(): Int{
        delay(500)
        return 18
    }

    fun sumInt() = runBlocking {
        val time = measureTimeMillis {
            val jobOne = async {
                oneInt()
            }
            val jobTwo = async {
                twoInt()
            }
            println("两数之和 -> ${jobOne.await() + jobTwo.await()}")
        }
        println("Completed in $time ms")
    }

    // TODO 使用 async 的结构化并发
    // 由于 async 被定义为了 CoroutineScope 上的扩展，我们需要将它写在作用域内，并且这是 coroutineScope 函数所提供的
    suspend fun concurrentSum(): Int = coroutineScope {
        val one = async { oneInt() }
        val two = async { twoInt() }
        one.await() + two.await()
    }

    fun structuralization() = runBlocking {
        val time = measureTimeMillis {
            val concurrentSum = concurrentSum()
            println("两数之和 -> $concurrentSum")
        }
        println("所用时间 -> $time")
    }


}