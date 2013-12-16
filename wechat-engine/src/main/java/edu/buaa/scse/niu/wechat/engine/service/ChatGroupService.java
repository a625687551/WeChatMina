package edu.buaa.scse.niu.wechat.engine.service;

import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.ChatGroupAccount;
import edu.buaa.scse.niu.wechat.engine.entity.Account;

public interface ChatGroupService {

	
	/**
	 * 创建聊天组
	 * @param users
	 * @param name
	 */
	public ChatGroup newChatGroup(List<Account> users,String name);
	
	public ChatGroup delete(ChatGroup chatGroup);
	
	public List<ChatGroupAccount> getChatGroupUsers(int chatGroupId); 

	public List<ChatGroupAccount> findChatGroups(Account account);
}
