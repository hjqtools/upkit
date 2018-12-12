package upkit.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;

public class AESCrypto {

	private static final String UTF_8 = "utf-8";

	private static final String KEY_ALGORITHM = "AES";

	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	private static final String URL_ENCODING_KEY = "av45k1pfb024xa3b";// 128位秘钥

	/**
	 * 加密 1.转JSON 2.AES加密 3.base64编码
	 * 
	 * @param params
	 * @return
	 */
	public static String paramEncode(Map<String, String> params) {
		try {
			// 转json
			String json = JSON.toJSONString(params);
			String base64 = cipher(json);
			return base64;
		} catch (Exception e) {
			throw new IllegalStateException("encoding data error,data:" + params, e);
		}
	}

	/**
	 * @param strInfo
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String cipher(String strInfo) throws NoSuchAlgorithmException, NoSuchPaddingException,
			UnsupportedEncodingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// AES加密
		Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
		Key key = new SecretKeySpec(URL_ENCODING_KEY.getBytes(UTF_8), KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] aesData = cipher.doFinal(strInfo.getBytes(UTF_8));
		// base64编码
		String base64 = Base64.encodeBase64URLSafeString(aesData);
		return base64;
	}

	/**
	 * 解密参数
	 * 
	 * @param data
	 * @return
	 */
	public static String decrypt(String data) {
		try {
			byte[] base64 = Base64.decodeBase64(data);
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			Key key = new SecretKeySpec(URL_ENCODING_KEY.getBytes(UTF_8), KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 执行操作
			return new String(cipher.doFinal(base64), UTF_8);
		} catch (Exception e) {
			throw new IllegalStateException("encoding data error,data:" + data, e);
		}
	}

	/**
	 * 解密参数
	 * 
	 * @param data
	 * @return
	 */
	public static Map<String, String> paramDecode(String data) {
		try {
			byte[] base64 = Base64.decodeBase64(data);
			Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
			Key key = new SecretKeySpec(URL_ENCODING_KEY.getBytes(UTF_8), KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 执行操作
			String json = new String(cipher.doFinal(base64), UTF_8);
			@SuppressWarnings("unchecked")
			Map<String, String> result = JSON.parseObject(json, HashMap.class);
			return result;
		} catch (Exception e) {
			throw new IllegalStateException("encoding data error,data:" + data, e);
		}
	}

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		Map<String, String> test = new HashMap<String, String>();
		// test.put("channelId", "test");
		// test.put("env", "test");
		test.put("userid", "1234342343");
		test.put("passwdr", "123424");
		// System.out.println(paramDecode("H9uFvW2rFJJQ1WulxOEww8-j46gdiHReICuPiXQEsakkD2RzCRxPB9vCJbkgoaAl"));
//		System.out.println(paramDecode("aJtNpxSf6i-O9o1ukESYlK9S0Stab8GQ0PH-5vWikIYcZBWat4TQIDKFFx19rU-s"));
		
//		String cipher = cipher("https://login.cloud.huawei.com/oauth2/v2/authorize?response_type=code&client_id=12345&redirect_uri=http%3A%2F%2Fwww.example.com%2Foauth_redirect&scope=https%3a%2f%2fwww.huawei.com%2fhealth%2fprofile.readonly+https%3a%2f%2fwww.huawei.com%2fhealth%2fsport.readonly&display=mobile");
//		System.out.println(cipher);
//		
//		String decrypt = decrypt("il8uLLpO2RQmwRfKVJlti074L3LVNW8JpskY2gmKveASJNbPWraGIJOOLRdNFAqEQpByXq7tOC89hA5eCyqvfdMmTojeRlSF0INo4qI7_ZSdWutJpdLO5jhiG-tmRRBk9OLfbB0KCP8FyT5wQuqvRpDXyBslgltw_P5q5JmxJeIjbbnJnt4_XDkA9aRPB2DuOEPPaTqjo4ei7r3dHJlEPhbUgMum4kQJ2psebeN2N9yb3Ly6lo-EgLMZdQzgqg592UiTDjLreypOJg7lVej27Ft_FI2tPEp1gvVUdnZe-psCxDIH8N72R4eqK2lJ1oyfT20kvwqyqKCOcDVbImXb8Rv__46GCMCgUwjWaUKVlFumPjQx2yNFhmhDS7olVsfH\n" + 
//				"");
//		System.out.println(decrypt);
		
		Map<String, String> user = new HashMap<String, String>();
		test.put("userPhoneNum", "15712653456");
		test.put("userPassword", "234dsfsdfd");
		System.out.println(paramEncode(test));
			
		String a = cipher("15712653456");
		System.out.println(a);
		String b  = decrypt(a);
		System.out.println(b);
		 
		 
		 // System.out.println(paramDecode(paramEncode(test)));
	}
}
