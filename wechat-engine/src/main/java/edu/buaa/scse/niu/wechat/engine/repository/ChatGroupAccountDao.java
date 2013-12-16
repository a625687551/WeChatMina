package edu.buaa.scse.niu.wechat.engine.repository;

import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.ChatGroupAccount;
import edu.buaa.scse.niu.wechat.engine.entity.Account;

public interface ChatGroupAccountDao extends BatchDao<ChatGroupAccount>{

	public List<ChatGroupAccount> findByChatGroup(ChatGroup chatGroup);

	public List<ChatGroupAccount> findByAccount(Account user);
	
}
