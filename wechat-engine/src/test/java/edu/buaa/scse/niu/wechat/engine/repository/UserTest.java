package edu.buaa.scse.niu.wechat.engine.repository;

import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.Account;


public interface UserTest {

	public List<Account> findAll();
	
	public Account findByUnique(String key);
	
	public Account save(Account user);
	
}
