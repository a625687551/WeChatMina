package edu.buaa.scse.niu.wechat.engine.service;

/**
 * 加密盐生成器接口
 * @author Niu
 *
 */
public interface PasswordService {

	/**
	 * 生成完全随机的加密盐
	 * @return
	 */
	public String getRandomSalt();
	/**
	 * 根据给定关键字数组生成相应加密盐
	 * @param key
	 * @return
	 */
	public String getSalt(String... key);
	
	/**
	 * 获得给定关键字的加密字符串
	 * @param key
	 * @return
	 */
	public String encrypt(String key);
	
	/**
	 * 根据给定关键字及加密盐获得加密后字符串
	 * @param key
	 * @param salt
	 * @return
	 */
	public String encrypt(String key,String salt);
	
	/**
	 * 将加密盐与关键字混合成新字符串
	 * @param key
	 * @param salt
	 * @return
	 */
	public String mixSalt(String key,String salt);
	
}
