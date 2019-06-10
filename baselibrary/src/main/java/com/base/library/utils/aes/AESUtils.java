package com.base.library.utils.aes;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * AES加密工具类
 * @author relieved-gao
 * @version 1.0
 */
public class AESUtils {


    public final static Base64 base64 = new Base64();

    private static final String AES = "AES";

    private static final String CRYPT_KEY = "1q2w89L6BkRAazsx";

    private static final String IV_STRING = "dMitHO!@#$%YVE0o";
    
    private static final String CHAR_SET = "UTF-8";

    /**
     * 加密
     *
     * @param content 加密内容
     * @return 密文
     * @throws Exception e
     */
    public static String encrypt(String content) {
        byte[] encryptedBytes = new byte[0];
        try {
            byte[] byteContent = content.getBytes(CHAR_SET);
            // 注意，为了能与 iOS 统一
            // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
            byte[] enCodeFormat = CRYPT_KEY.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, AES);
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            // 指定加密的算法、工作模式和填充方式
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            encryptedBytes = cipher.doFinal(byteContent);
            // 同样对加密后数据进行 base64 编码
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {

        }
        return base64.encode(encryptedBytes);
    }

    /**
     * 解密
     *
     * @param content 密文
     * @return 明文
     * @throws Exception e
     */
    public static String decrypt(String content) {
        // base64 解码
        try {
            byte[] encryptedBytes = base64.decode(content);
            byte[] enCodeFormat = CRYPT_KEY.getBytes();
            SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, AES);
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] result = cipher.doFinal(encryptedBytes);

            return new String(result, CHAR_SET);
        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {

        }
        return null;
    }

	public static void main(String[] args) {
		String data = "空山新雨后,天气晚来秋";
		System.out.println("=============origin<<<<<<<<<<"+data);
		String encrypt = encrypt(data);
		System.out.println("=============after encrypt<<<<<<<<<<"+encrypt);
		System.out.println("=============after decrypt<<<<<<<<<<"+decrypt(encrypt));
	}
}