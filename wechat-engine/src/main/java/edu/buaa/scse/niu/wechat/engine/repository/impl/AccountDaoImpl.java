package edu.buaa.scse.niu.wechat.engine.repository.impl;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.Profile;
import edu.buaa.scse.niu.wechat.engine.repository.AccountDao;

@Repository
public class AccountDaoImpl extends BaseDaoImpl<Account> implements AccountDao {

	@Transactional(readOnly=true)
	@Override
	public Account findByName(String name) {
		Account acc = findUniqueByProperty("name", name);
		return acc;
	}

	@Transactional(readOnly=true)
	@Override
	public Account findByEmail(String email) {
		Session session = getSession();
		Account ret = (Account) session.createCriteria(Account.class)
				.createCriteria("profile").add(Restrictions.eq("email", email))
				.uniqueResult();
		return ret;
	}

	@Transactional(readOnly=true)
	@Override
	public Account findByPhone(String phone) {
		Session session = getSession();
		Account ret = (Account) session.createCriteria(Account.class)
				.createCriteria("profile").add(Restrictions.eq("phone", phone))
				.uniqueResult();
		return ret;
	}

	@Transactional(readOnly=true)
	@Override
	public Account findByUnique(String key) {
		Account res=findByName(key);
		if(res==null){
			res=findByPhone(key);
		}
		if(res==null){
			res=findByEmail(key);
		}
//		System.out.println(res.getProfile().getEmail());
//		System.out.println(res.getProfile());
		return res;
	}

}
