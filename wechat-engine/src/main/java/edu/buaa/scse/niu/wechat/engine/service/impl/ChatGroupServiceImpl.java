package edu.buaa.scse.niu.wechat.engine.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.ChatGroupAccount;
import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.repository.ChatGroupDao;
import edu.buaa.scse.niu.wechat.engine.repository.ChatGroupAccountDao;
import edu.buaa.scse.niu.wechat.engine.repository.AccountDao;
import edu.buaa.scse.niu.wechat.engine.service.ChatGroupService;

@Service
public class ChatGroupServiceImpl implements ChatGroupService{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class.getSimpleName());
	
	private ChatGroupDao chatGroupDao;
	private ChatGroupAccountDao chatGroupUserDao;
	
	
	@Autowired
	public ChatGroupServiceImpl(ChatGroupDao chatGroupDao,
			ChatGroupAccountDao chatGroupUserDao, AccountDao userDao) {
		super();
		this.chatGroupDao = chatGroupDao;
		this.chatGroupUserDao = chatGroupUserDao;
	}


	@Override
	public ChatGroup newChatGroup(List<Account> users, String name) {
		ChatGroup chatGroup=saveChatGroup(name);
		saveChatGroupUser(users, chatGroup);
		return chatGroup;
	}
	
	@Transactional(propagation=Propagation.NESTED)
	private ChatGroup saveChatGroup(String name){
		ChatGroup chatGroup=new ChatGroup();
		chatGroup.setName(name);
		chatGroup=chatGroupDao.save(chatGroup);
		LOGGER.info("save chatGroup: id="+chatGroup.getId());
		return chatGroup;
	}

	private void saveChatGroupUser(List<Account> users,ChatGroup chatGroup){
		List<ChatGroupAccount> chatGroupUsers=new ArrayList<>();
		chatGroupDao.refresh(chatGroup);
		for(Account u:users){
			ChatGroupAccount cu=new ChatGroupAccount(chatGroup, u);
//			userDao.refresh(u);
			chatGroupUsers.add(cu);
//			chatGroupUserDao.save(cu);
		}
		chatGroupUserDao.save(chatGroupUsers);
	}

	@Override
	public List<ChatGroupAccount> getChatGroupUsers(int chatGroupId) {
		ChatGroup group=chatGroupDao.get(chatGroupId);
		if(group==null){
			return null;
		}
		List<ChatGroupAccount> users=group.getChatGroupAccounts();
		return users;
	}


	@Override
	public ChatGroup delete(ChatGroup chatGroup) {
		chatGroupDao.refresh(chatGroup);
		List<ChatGroupAccount> cusers=chatGroup.getChatGroupAccounts();
		chatGroupUserDao.delete(cusers);
		chatGroupDao.delete(chatGroup);
		return chatGroup;
	}


	@Override
	public List<ChatGroupAccount> findChatGroups(Account account) {
		return chatGroupUserDao.findByAccount(account);
	}


}
