package com.sens.utils;

public class AES128forMysqlUtils {
   // private static final String secretKey = "7dac8ceb5d56748200a9d05c09ea48cf01e978634729d195ac84f828618421f5";
	
    /*
    private SecretKeySpec byte16key;
	
	// 기본 생성자
	public AES128forMysqlUtils() {
		this.byte16key = generateAES128Key(secretKey,"UTF-8");
	}
	// 매개변수 생성자(키값을 원하는 형태로 인코딩)
	public AES128ForMySqlUtil(String encoding) {
		this.byte16key = generateAES128Key(secretKey, encoding);
	}
	
	
	 원래 AES128 의 키는 16byte 이어야 하지만, MySQL 에서는 16byte이상의 키를 사용할 수 있도록 지원되고 있다.
	 (실제로는 16byte 키로 강제로 변환된다. MySql에서 사용되는 방식과 동일하게 맞춤)

	private static SecretKeySpec generateAES128Key(final String key, final String encoding) {
		try {
			final byte[] finalKey = new byte[16];
			int i = 0;
			for(byte b : key.getBytes(encoding)) {
				finalKey[i++ % 16] ^= b;
			}
			return new SecretKeySpec(finalKey, "AES");
		}catch(UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	// SELECT HEX(AES_ENCRYPT('안녕하세요',@secretKey));
	public String getEncryt(String origin) {
		String encStr = null;
		try {
			final Cipher encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, this.byte16key);
			encStr = new String(Hex.encodeHex(encryptCipher.doFinal(origin.getBytes("UTF-8")))).toUpperCase();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return encStr;
	}
	
	// SELECT CAST(AES_DECRYPT(UNHEX(encStr), @secretKey) AS CHAR);
	public String getDecrypt(String encStr) {
		String decStr = null;
		try {
			final Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			decryptCipher.init(Cipher.DECRYPT_MODE, this.byte16key);
			decStr = new String(decryptCipher.doFinal(Hex.decodeHex(encStr.toCharArray())), CharEncoding.UTF_8);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return decStr;
	}
		
		*/
}
