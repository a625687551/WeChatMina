package edu.buaa.scse.niu.wechat.engine.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.UserGroup;
import edu.buaa.scse.niu.wechat.engine.repository.UserGroupDao;

@Repository
public class UserGroupDaoImpl extends BaseDaoImpl<UserGroup> implements
		UserGroupDao {

	@Override
	public List<UserGroup> findByAccount(Account account) {
		Assert.notNull(account);
		//TODO 应该传id 还是对象？？
		return findByProperty("account", account.getId());
	}

}
