package edu.buaa.scse.niu.wechat.engine.repository;

import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.UserGroup;


public interface UserGroupDao extends BaseDao<UserGroup>{
	
	public List<UserGroup> findByAccount(Account account);

}
