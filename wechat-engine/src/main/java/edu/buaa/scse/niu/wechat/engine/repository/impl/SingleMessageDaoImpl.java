package edu.buaa.scse.niu.wechat.engine.repository.impl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.repository.SingleMessageDao;

@Repository
public class SingleMessageDaoImpl extends BaseDaoImpl<SingleMessage> implements SingleMessageDao{

	@Override
	public List<SingleMessage> findByRelationship(Account src, Account dest) {
		Assert.notNull(src);
		Assert.notNull(dest);
		// src to dest
		Criterion crit1=Restrictions.eq("srcUser", src.getId());
		Criterion crit2=Restrictions.eq("destUser", dest.getId());
		Criterion and1=Restrictions.and(crit1,crit2);
		
		// dest to src
		Criterion crit3=Restrictions.eq("destUser", src.getId());
		Criterion crit4=Restrictions.eq("srcUser", dest.getId());
		Criterion and2=Restrictions.and(crit3,crit4);
		
		Criterion or=Restrictions.or(and1,and2);
		return findByCriteria(or);
	}

}
