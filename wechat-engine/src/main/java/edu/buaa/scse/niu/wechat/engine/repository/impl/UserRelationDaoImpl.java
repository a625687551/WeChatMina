package edu.buaa.scse.niu.wechat.engine.repository.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.UserGroup;
import edu.buaa.scse.niu.wechat.engine.entity.UserRelationship;
import edu.buaa.scse.niu.wechat.engine.repository.UserRelationDao;
@Repository
public class UserRelationDaoImpl extends BaseDaoImpl<UserRelationship>
		implements UserRelationDao {

	@Override
	public List<UserRelationship> findByAccount(Account user) {
		Assert.notNull(user);
		//TODO 应该传id 还是对象？？
		return findByProperty("account",
				user.getId());
	}

	@Override
	public List<UserRelationship> findByGroup(UserGroup group) {
		Assert.notNull(group);
		//TODO 应该传id 还是对象？？
		return findByProperty("group", group.getId());
	}

	@Override
	public UserRelationship findByAccount(Account user, Account friend) {
		Criterion crit1=Restrictions.eq("srcUser", user.getId());
		Criterion crit2=Restrictions.eq("destUser", friend.getId());
		Criterion and=Restrictions.and(crit1,crit2);
		return (UserRelationship) createCriteria(and).uniqueResult();
	}

}
