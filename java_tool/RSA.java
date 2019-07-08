package io.github.jnan88.crypto;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * 描述：RSA加密解密工具类
 * 
 * <pre>
    RSA	:  标准签名算法名称：SHA1WithRSA	      对 RSA 密钥的长度不限制，推荐使用 2048 位以上
 * RSA2: 标准签名算法名称：SHA256WithRSA	  强制要求 RSA 密钥的长度至少为 2048
 * </pre>
 * 
 * @author jnan88@intbee.com
 * @version: 0.0.1 2019年7月8日-下午1:15:46
 *
 */
public class RSA {
	/**
	 * SHA1WithRSA 算法请求类型
	 */
	public static final String	SIGN_TYPE_RSA				= "RSA";
	/**
	 * SHA256WithRSA 算法请求类型
	 */
	public static final String	SIGN_TYPE_RSA2				= "RSA2";

	public static final String	SIGN_ALGORITHMS_SHA1RSA		= "SHA1WithRSA";
	public static final String	SIGN_ALGORITHMS_MD5RSA		= "MD5WithRSA";
	public static final String	SIGN_ALGORITHMS_SHA256RSA	= "SHA256WithRSA";

	/**
	 * 
	 * @param keySize
	 *            密钥长度，DH算法的默认密钥长度是1024 密钥长度必须是64的倍数，在512到65536位之间
	 * @return
	 * @throws Exception
	 */
	public static PairKeys initKey(int keySize) throws Exception {
		// 实例化密钥生成器
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(SIGN_TYPE_RSA);
		// 初始化密钥生成器
		keyPairGenerator.initialize(keySize);
		// 生成密钥对
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		// 甲方公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// 甲方私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		return PairKeys.of(publicKey, privateKey);
	}

	static class PairKeys {
		RSAPublicKey	pubKey;
		RSAPrivateKey	priKey;

		/**
		 * 
		 */
		public static PairKeys of(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
			PairKeys keys = new PairKeys();
			keys.pubKey = publicKey;
			keys.priKey = privateKey;
			return keys;
		}

		public byte[] encodePublicKey() {
			return pubKey.getEncoded();
		}

		public byte[] encodePrivateKey() {
			return priKey.getEncoded();
		}
	}

	/**
	 * 私钥加密
	 *
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 */
	public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
		// 生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 公钥加密
	 *
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 */
	public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {

		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
		// 初始化公钥
		// 密钥材料转换
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		// 产生公钥
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

		// 数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return cipher.doFinal(data);
	}

	/**
	 * 私钥解密
	 *
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 */
	public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
		KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
		// 生成私钥
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 公钥解密
	 *
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 */
	public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {
		// 实例化密钥工厂
		KeyFactory keyFactory = KeyFactory.getInstance(SIGN_TYPE_RSA);
		// 初始化公钥
		// 密钥材料转换
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
		// 产生公钥
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		// 数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
		return cipher.doFinal(data);
	}

	/**
	 * 解析密钥
	 * 
	 * @param algorithm
	 * @param base64Text
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, byte[] encodedKey) throws Exception {
		if (encodedKey == null || isBlank(algorithm)) {
			return null;
		}
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
	}

	/**
	 * 解析公钥
	 * 
	 * @param algorithm
	 * @param base64Text
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKeyFromX509(String algorithm, byte[] encodedKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
	}

	/**
	 * RSA2 签名
	 * 
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static String rsa256Sign(String content, String privateKey) throws IllegalArgumentException {
		return rsaSign(content, privateKey, SIGN_ALGORITHMS_SHA256RSA);
	}

	/**
	 * RSA 签名
	 * 
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static String rsaSign(String content, String privateKey) throws IllegalArgumentException {
		return rsaSign(content, privateKey, SIGN_ALGORITHMS_SHA1RSA);
	}

	/**
	 * 
	 * @param content
	 * @param privateKey
	 * @param signatureName
	 *            例如：MD5WithRSA、SHA1WithRSA、SHA256WithRSA
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static String rsaSign(String content, String privateKey, String signatureName)
			throws IllegalArgumentException {
		try {
			byte[] encodedKey = Base64.getDecoder().decode(privateKey);
			PrivateKey priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, encodedKey);
			java.security.Signature signature = java.security.Signature.getInstance(signatureName);
			signature.initSign(priKey);
			signature.update(content.getBytes(StandardCharsets.UTF_8));
			byte[] signed = signature.sign();
			return Base64.getEncoder().encodeToString((signed));
		} catch (Exception e) {
			throw new IllegalArgumentException("RSAcontent = " + content, e);
		}
	}

	/**
	 * RSA2 验签
	 * 
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean rsa256Check(String content, String sign, String publicKey) throws IllegalArgumentException {
		return rsaCheck(content, sign, publicKey, SIGN_ALGORITHMS_SHA256RSA);
	}

	/**
	 * RSA 验签
	 * 
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean rsaCheck(String content, String sign, String publicKey) throws IllegalArgumentException {
		return rsaCheck(content, sign, publicKey, SIGN_ALGORITHMS_SHA1RSA);
	}

	/**
	 * RSA 验签
	 * 
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @param signatureName
	 *            例如：MD5WithRSA、SHA1WithRSA、SHA256WithRSA
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean rsaCheck(String content, String sign, String publicKey, String signatureName)
			throws IllegalArgumentException {
		try {
			byte[] encodedKey = Base64.getDecoder().decode(publicKey);
			PublicKey pubKey = getPublicKeyFromX509(SIGN_TYPE_RSA, encodedKey);

			java.security.Signature signature = java.security.Signature.getInstance(signatureName);
			signature.initVerify(pubKey);
			signature.update(content.getBytes(StandardCharsets.UTF_8));
			return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
		} catch (Exception e) {
			throw new IllegalArgumentException("RSAcontent = " + content + ",sign=" + sign, e);
		}
	}

	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

}
