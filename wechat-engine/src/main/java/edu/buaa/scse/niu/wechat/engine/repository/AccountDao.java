package edu.buaa.scse.niu.wechat.engine.repository;

import edu.buaa.scse.niu.wechat.engine.entity.Account;

public interface AccountDao extends BaseDao<Account> {
	
	public Account findByName(String name);

	public Account findByEmail(String email);

	public Account findByPhone(String phone);
	
	/**
	 * 通过唯一字段查询 包括email phone name
	 * @param key
	 * @return
	 */
	public Account findByUnique(String key);
}
