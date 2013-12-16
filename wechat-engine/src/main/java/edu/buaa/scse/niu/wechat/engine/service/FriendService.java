package edu.buaa.scse.niu.wechat.engine.service;

import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.UserGroup;
import edu.buaa.scse.niu.wechat.engine.entity.UserRelationship;

public interface FriendService {

	/**
	 * 初始化用户好友分组 创建默认分组
	 * 
	 * @param user
	 */
	public List<UserGroup> initUserGroup(Account user);

	/**
	 * 新建好友分组
	 * 
	 * @param user
	 * @param groupName
	 * @return
	 */
	public UserGroup newUserGroup(Account user, String groupName);

	/**
	 * 重命名好友分组
	 * 
	 * @param newName
	 * @param group
	 * @return
	 */
	public UserGroup renameUserGroup(String newName, UserGroup group);

	/**
	 * 寻找给定用户的好友分组
	 * @param account
	 * @return
	 */
	public List<UserGroup> findGroup(Account account);

	/**
	 * 删除好友分组
	 * 
	 * @param group
	 * @return
	 */
	public boolean deleteUserGroup(UserGroup group);

	/**
	 * 将好友移动的给定分组
	 * 
	 * @param group
	 * @param relationship
	 * @return
	 */
	public boolean changeUserGroup(UserGroup newGroup,
			UserRelationship relationship);

	/**
	 * 建立好友关系
	 * 
	 * @param user
	 * @param friend
	 * @return
	 */
	public UserRelationship[] makeFriends(Account user, Account friend);

	/**
	 * 删除好友关系
	 * 
	 * @param user0
	 * @param user1
	 * @return
	 */
	public boolean deleteFriend(Account user0, Account user1);

	public List<UserRelationship> findFriends(Account user);

	public List<UserRelationship> findFriends(UserGroup group);

	public UserRelationship findFriends(Account user, Account friend);
}
