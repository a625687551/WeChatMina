package edu.buaa.scse.niu.wechat.engine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.buaa.scse.niu.wechat.engine.client.Client;
import edu.buaa.scse.niu.wechat.engine.client.ClientThread;
import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.ChatGroupAccount;
import edu.buaa.scse.niu.wechat.engine.entity.Profile;
import edu.buaa.scse.niu.wechat.engine.entity.UserGroup;
import edu.buaa.scse.niu.wechat.engine.entity.UserRelationship;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.OfflineGroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.OfflineSingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.request.LoginRequest;
import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;
import edu.buaa.scse.niu.wechat.engine.service.ChatGroupService;
import edu.buaa.scse.niu.wechat.engine.service.FriendService;
import edu.buaa.scse.niu.wechat.engine.service.Services;
import edu.buaa.scse.niu.wechat.engine.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/spring/test-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class BaseTest {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private Services services;

	@Before
	public void setUp() throws Exception {
		deleteAll();
	}

	private void deleteAll() {
		long time = System.currentTimeMillis();
		System.out.println("Test Delete begin");
		deleteFromTable(OfflineGroupMessage.class.getSimpleName());
		deleteFromTable(OfflineSingleMessage.class.getSimpleName());
		deleteFromTable(GroupMessage.class.getSimpleName());
		deleteFromTable(SingleMessage.class.getSimpleName());
		deleteFromTable(ChatGroupAccount.class.getSimpleName());
		deleteFromTable(ChatGroup.class.getSimpleName());
		deleteFromTable(UserRelationship.class.getSimpleName());
		deleteFromTable(UserGroup.class.getSimpleName());
		deleteFromTable(Account.class.getSimpleName());
		deleteFromTable(Profile.class.getSimpleName());
		System.out.println("Test Delete end");
		long t = System.currentTimeMillis() - time;
		System.out.println("Time=" + t);
	}

	public void deleteFromTable(String tableName) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "delete " + tableName;
		session.createQuery(hql).executeUpdate();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void initData() {
		UserService userService = services.getUserService();
		FriendService friendService = services.getFriendService();
		ChatGroupService groupService = services.getChatGroupService();

		List<Account> accounts = new ArrayList<>();
		List<UserGroup> groups = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			RegisterRequest req = new RegisterRequest("user" + i, "password", i
					+ "user@163.com", "0000000" + i);
			RegisterResponse res = userService.register(req);

			assertTrue(res.isSucc());
			Account user = res.getAccount();
			List<UserGroup> gs = friendService.initUserGroup(user);
			groups.add(gs.get(0));
			accounts.add(user);
		}

		for (int i = 0; i < 4; i++) {
			Account src = accounts.get(i);
			for (int j = i + 1; j < 4; j++) {
				Account dest = accounts.get(j);
				friendService.makeFriends(src, dest);
			}
		}

		accounts.remove(3);
		groupService.newChatGroup(accounts, "除了user3都在这个组");
		
		for(int i=0;i<4;i++){
			Client client=new Client(2345);
			Account user=accounts.get(i);
			List<UserRelationship> friends=friendService.findFriends(user);
			List<ChatGroupAccount> cgs=groupService.findChatGroups(user);
			List<ChatGroup> gs=new ArrayList<>();
			for(ChatGroupAccount cga:cgs){
				gs.add(cga.getChatGroup());
			}
			
			LoginRequest req=new LoginRequest(user.getName(), user.getPassword(), "");
			client.setLoginRequest(req);
			client.setFriends(friends);
			client.setGroups(gs);
			ClientThread t=new ClientThread(client);
			t.start();
		}
		
	}

}
