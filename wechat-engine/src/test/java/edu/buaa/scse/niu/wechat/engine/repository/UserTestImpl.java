package edu.buaa.scse.niu.wechat.engine.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.Profile;

@Component
public class UserTestImpl implements UserTest{

	private final static Logger LOGGER = LoggerFactory.getLogger(UserTestImpl.class.getSimpleName());
	@Autowired
	private AccountDao userDao;

	@Override
	public List<Account> findAll() {
		List<Account> us=userDao.findAll();
		int size=us.size();
		LOGGER.info("find "+size+" users");
		return us;
	}


	@Override
	public Account save(Account user) {
		user=userDao.save(user);
		return user;
	}
	
	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "test");
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath:/spring/root-context.xml");
		UserTest test=ctx.getBean(UserTest.class);
		Account u=new Account();
		Profile p=new Profile("niutianfang@126.com", "15810766570");
		p.setNickName("kevin");
		u=new Account("niu", "123", "abc");
		u.setProfile(p);
//		test.save(u);
		List<Account> users=test.findAll();
		System.out.println("##### Find All Account #####");
		System.out.println("Find "+users.size()+" accounts");
		Account aname=test.findByUnique("niu");
		Account aemail=test.findByUnique("niutianfang@126.com");
		Account aphone=test.findByUnique("15810766570");
		System.out.println("##### Find Account By Unique Key #####");
		System.out.println("Name "+aname);
		System.out.println("Email "+aemail);
		System.out.println("Phone "+aphone);
	}


	@Override
	public Account findByUnique(String key) {
		Account account=userDao.findByUnique(key);
		return account;
	}
	
}
