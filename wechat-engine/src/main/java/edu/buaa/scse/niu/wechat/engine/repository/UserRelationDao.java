package edu.buaa.scse.niu.wechat.engine.repository;

import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.UserGroup;
import edu.buaa.scse.niu.wechat.engine.entity.UserRelationship;


public interface UserRelationDao extends BaseDao<UserRelationship>{

	public List<UserRelationship> findByAccount(Account user);
	
	public List<UserRelationship> findByGroup(UserGroup group);
	
	public UserRelationship findByAccount(Account user,Account friend);
}
