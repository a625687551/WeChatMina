package edu.buaa.scse.niu.wechat.engine.repository;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.ChatGroupAccount;
import edu.buaa.scse.niu.wechat.engine.entity.Profile;
import edu.buaa.scse.niu.wechat.engine.entity.UserGroup;
import edu.buaa.scse.niu.wechat.engine.entity.UserRelationship;
import edu.buaa.scse.niu.wechat.engine.entity.factory.MsgDataFactory;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.service.ChatGroupService;
import edu.buaa.scse.niu.wechat.engine.service.UserService;

@Repository
@Transactional
public class RepositoryTestImpl implements RepositoryTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "test");
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath:/spring/root-context.xml");
		RepositoryTest test = ctx.getBean(RepositoryTest.class);
		test.deleteAll();
//		test.testChatGroup(TestType.Insert);
//		test.testChatGroup(TestType.Delete);
		// test.testDelete();
		test.testInsert();
	}

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private AccountDao userDao;
	@Autowired
	private UserGroupDao userGroupDao;
	@Autowired
	private UserRelationDao userRelationDao;
	@Autowired
	private ChatGroupDao chatGroupDao;
	@Autowired
	private ChatGroupAccountDao chatGroupUserDao;
	@Autowired
	private SingleMessageDao singleMessageDao;
	@Autowired
	private GroupMessageDao groupMessageDao;
	@Autowired
	private OfflineGroupMsgDao offlineGroupMsgDao;
	@Autowired
	private OfflineSingleMsgDao offlineSingleMsgDao;

	@Autowired
	private UserService userService;
	@Autowired
	private ChatGroupService chatGroupService;
	
	private void testUserService() {
		List<Account> users = userDao.findAll();
		userService.deleteUser(users.get(0));
	}

	@Override
	public void testInsert() {
		List<Account> users = insertUser(25);
		ChatGroup group = insertChatGroup(users);
		insertUserGroup(users);
		List<UserRelationship> relas = insertRelationship(users);
		insertSingleMessage(relas);
		insertGroupMessage(group);
	}

	private void insertGroupMessage(ChatGroup group) {
		for (int k = 0; k < 10; k++) {
			GroupMessage msg = new GroupMessage();
			msg.setGroup(group);
			msg.setSrcUser(group.getChatGroupAccounts().get(0).getAccount());
			msg.setMessage(MsgDataFactory.newText("abcdefg"));
			groupMessageDao.save(msg);
		}
	}

	private void insertSingleMessage(List<UserRelationship> relas) {
		for (int i = 0; i < relas.size(); i++) {
			UserRelationship rela = relas.get(i);
			for (int k = 0; k < 10; k++) {
				SingleMessage msg = new SingleMessage();
				if (k % 2 == 0) {
					msg.setDestUser(rela.getAccount());
					msg.setSrcUser(rela.getFriendAccount());
				} else {
					msg.setSrcUser(rela.getAccount());
					msg.setDestUser(rela.getFriendAccount());
				}
				msg.setMessage(MsgDataFactory.newText("abcdefg"));
				singleMessageDao.save(msg);
			}
		}

	}

	private List<Account> insertUser(int size) {
		DecimalFormat df = new DecimalFormat("000");
		List<Account> users = new ArrayList<Account>();
		for (int i = 0; i < size; i++) {
			String index = df.format(i);
			Account u = new Account("name"+index, "123456", "abc");
			Profile p=new Profile(index + "email@126.com", "15810766" + index);
			p.setNickName("nickname"+index);
			p.setSignature("hahaha");
			u.setProfile(p);
			users.add(u);
		}

		System.out.println("insert User start");
		long start = System.currentTimeMillis();
		for (Account user : users) {
			userDao.save(user);
		}
		long time = System.currentTimeMillis() - start;
		System.out.println("insert User end. time=" + time);
		return users;
	}

	private void insertUserGroup(List<Account> users) {
		List<UserGroup> groups = new ArrayList<>();
		for (Account user : users) {
			UserGroup g = new UserGroup();
			for (int i = 0; i < 4; i++) {
				g.setGroupName(user.getName() + ":g" + i);
				g.setAccount(user);
				groups.add(g);
			}
		}
		for (UserGroup group : groups) {
			userGroupDao.save(group);
		}
	}

	private ChatGroup insertChatGroup(List<Account> users) {
		System.out.println("insert ChatGroup start");
		long start = System.currentTimeMillis();
		
		String name="testgroup";
		ChatGroup g=chatGroupService.newChatGroup(users, name);
		
		long time = System.currentTimeMillis() - start;
		System.out.println("insert ChatGroup end. time=" + time);
		return g;
	}

	private List<UserRelationship> insertRelationship(List<Account> users) {
		int size = users.size();
		Random rand = new Random();
		int tmp;
		List<UserRelationship> relas = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Account src = users.get(i);
			for (int j = 0; j < 5; j++) {
				tmp = rand.nextInt(size);
				if (tmp != i) {
					Account dest = users.get(j);
					UserRelationship rela = new UserRelationship();
					rela.setAccount(src);
					rela.setFriendAccount(dest);
					relas.add(rela);
					userRelationDao.save(rela);
				}
			}
		}
		return relas;
	}
	
	@Override
	public void deleteAll() {
		long time = System.currentTimeMillis();
		System.out.println("Test Delete begin");
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

	@Override
	public void testUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void testFind() {
		// TODO Auto-generated method stub

	}

	@Override
	public void testUser() {

	}

	@Override
	public void testUserGroup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void testUserRelationship(TestType type) {
		if(type==TestType.Insert){
			List<Account> users = insertUser(25);
			List<UserRelationship> relas = insertRelationship(users);
		}
	}

	@Override
	public void testChatGroup(TestType type) {
		if(type==TestType.Insert){
			List<Account> users=insertUser(25);
			List<Account> tmpUsers;
			List<ChatGroup> groups=new ArrayList<>();
			for(int i=0;i<5;i++){
				tmpUsers=new ArrayList<>();
				for(int k=0;k<5;k++){
					tmpUsers.add(users.get(i*5+k));
				}
				ChatGroup group=insertChatGroup(tmpUsers);
				groups.add(group);
			}
		}else if(type==TestType.Delete){
			List<ChatGroup> groups=chatGroupDao.findAll();
			for(ChatGroup g:groups){
				chatGroupService.delete(g);
			}
		}
	}

	@Override
	public void testSingleMessage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void testGroupMessage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void testOfflineMessage() {
		// TODO Auto-generated method stub

	}

}
