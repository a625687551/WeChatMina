package edu.buaa.scse.niu.wechat.engine.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import edu.buaa.scse.niu.wechat.common.utils.UUIDGenerator;
import edu.buaa.scse.niu.wechat.engine.service.PasswordService;

@Service
public class PasswordServiceImpl implements PasswordService {

	private Random random = new Random();

	private String encryptType = "SHA-1";

	public PasswordServiceImpl(){
		
	}
	
	public PasswordServiceImpl(String encryptType) {
		this.encryptType = encryptType;
	}

	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static void main(String[] args) {
		String orginal = "hello this is the orginal pwd";
		String niu = "niutianfang";
		String bh = "beihang";

		PasswordServiceImpl ps = new PasswordServiceImpl("SHA-1");

		String salt = ps.getSalt("salt", "niu", "buaa");
		String randSalt = ps.getRandomSalt();
		String randSalt2 = ps.getRandomSalt();

		long start=System.currentTimeMillis();
		String encrypt = ps.encrypt(orginal, randSalt);
		
		
		
		String encrypt2 = ps.encrypt(orginal, randSalt2);
		String eniu = ps.encrypt(niu, salt);
		String ebh = ps.encrypt(bh, salt);
		long time=System.currentTimeMillis()-start;
		System.out.println("encrypt time="+time);

		System.out.println(orginal + " to " + encrypt);
		System.out.println(orginal + " to " + encrypt2);
		System.out.println(niu + " to " + eniu);
		System.out.println(bh + " to " + ebh);
	}

	@Override
	public String getSalt(String... key) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			sb.append(key[i]);
		}
		sb.append(random.nextInt(1000));
		return sb.toString();
	}

	@Override
	public String encrypt(String key) {
		try {
			MessageDigest md = MessageDigest.getInstance(encryptType);
			md.update(key.getBytes());
			// 获得密文
			byte[] bts = md.digest();
			// 把密文转换成十六进制的字符串形式
			int j = bts.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = bts[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String mixSalt(String key, String salt) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0, j = 0, size = key.length(); i < salt.length(); i++) {
			sb.append(salt.charAt(i));
			if (j < size) {
				sb.append(key.charAt(j++));
			}
		}
		return sb.toString();
	}

	@Override
	public String encrypt(String key, String salt) {
		String mix = mixSalt(key, salt);
		String res = encrypt(mix);
		return res;
	}

	@Override
	public String getRandomSalt() {
		return UUIDGenerator.getNext32();
	}

}
