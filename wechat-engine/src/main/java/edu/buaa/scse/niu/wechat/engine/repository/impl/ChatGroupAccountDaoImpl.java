package edu.buaa.scse.niu.wechat.engine.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.ChatGroupAccount;
import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.repository.ChatGroupAccountDao;

@Repository
public class ChatGroupAccountDaoImpl extends BaseBatchDaoImpl<ChatGroupAccount> implements ChatGroupAccountDao{

	@Override
	public List<ChatGroupAccount> findByChatGroup(ChatGroup chatGroup) {
		Assert.notNull(chatGroup);
		return findByProperty("chatGroupId",
				chatGroup.getId());
	}

	@Override
	public List<ChatGroupAccount> findByAccount(Account account) {
		Assert.notNull(account);
		return findByProperty("accountId",
				account.getId());
	}

}
