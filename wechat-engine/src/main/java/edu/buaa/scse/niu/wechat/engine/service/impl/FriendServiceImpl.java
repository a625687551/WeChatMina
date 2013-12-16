package edu.buaa.scse.niu.wechat.engine.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.UserGroup;
import edu.buaa.scse.niu.wechat.engine.entity.UserRelationship;
import edu.buaa.scse.niu.wechat.engine.repository.AccountDao;
import edu.buaa.scse.niu.wechat.engine.repository.UserGroupDao;
import edu.buaa.scse.niu.wechat.engine.repository.UserRelationDao;
import edu.buaa.scse.niu.wechat.engine.service.FriendService;

@Service
public class FriendServiceImpl implements FriendService {
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private UserGroupDao userGroupDao;
	@Autowired
	private UserRelationDao userRelationDao;

	@Override
	public UserGroup newUserGroup(Account user, String groupName) {
		UserGroup group=new UserGroup(groupName, user);
		return userGroupDao.save(group);
	}

	@Override
	public UserGroup renameUserGroup(String newName, UserGroup group) {
		String id=group.getId();
		group=userGroupDao.get(id);
		group.setGroupName(newName);
		return userGroupDao.update(group);
	}

	@Override
	public boolean deleteUserGroup(UserGroup group) {
		userGroupDao.delete(group);
		return true;
	}

	@Override
	public boolean changeUserGroup(UserGroup newGroup,
			UserRelationship relationship) {
		if(newGroup==null || relationship==null){
			return false;
		}
		String gid=newGroup.getId();
		String rid=relationship.getId();
		if(gid==null || rid==null){
			return false;
		}
		relationship=userRelationDao.get(rid);
		newGroup=userGroupDao.get(gid);
		relationship.setGroup(newGroup);
		userRelationDao.update(relationship);
		return true;
	}

	@Override
	public UserRelationship[] makeFriends(Account user, Account friend) {
		UserRelationship rela0=new UserRelationship(null, friend, user);
		UserRelationship rela1=new UserRelationship(null, user, friend);
		rela0=userRelationDao.save(rela0);
		rela1=userRelationDao.save(rela1);
		return new UserRelationship[]{rela0,rela1};
	}

	@Override
	public List<UserGroup> initUserGroup(Account user) {
		//TODO 配置文件
		String defaultGroups[]={"我的好友","企业好友","陌生人","黑名单"};
		List<UserGroup> groups=new ArrayList<>();
		for(String name:defaultGroups){
			UserGroup group=new UserGroup(name, user);
			userGroupDao.save(group);
			groups.add(group);
		}
		return groups;
	}

	@Override
	public boolean deleteFriend(Account user0, Account user1) {
		UserRelationship rela1=userRelationDao.findByAccount(user0, user1);
		UserRelationship rela2=userRelationDao.findByAccount(user1, user0);
		if(rela1==null || rela2==null){
			return false;
		}
		userRelationDao.delete(rela1);
		userRelationDao.delete(rela2);
		return true;
	}

	@Override
	public List<UserRelationship> findFriends(Account user) {
		return userRelationDao.findByAccount(user);
	}

	@Override
	public List<UserRelationship> findFriends(UserGroup group) {
		return userRelationDao.findByGroup(group);
	}

	@Override
	public UserRelationship findFriends(Account user, Account friend) {
		return userRelationDao.findByAccount(user, friend);
	}

	@Override
	public List<UserGroup> findGroup(Account account) {
		return userGroupDao.findByAccount(account);
	}


	
	
}
