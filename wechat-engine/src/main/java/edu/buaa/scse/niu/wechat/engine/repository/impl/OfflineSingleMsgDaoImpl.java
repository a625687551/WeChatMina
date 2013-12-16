package edu.buaa.scse.niu.wechat.engine.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.msg.OfflineSingleMessage;
import edu.buaa.scse.niu.wechat.engine.repository.OfflineSingleMsgDao;

@Repository
public class OfflineSingleMsgDaoImpl extends BaseBatchDaoImpl<OfflineSingleMessage> implements OfflineSingleMsgDao{

	@Override
	public List<OfflineSingleMessage> findByAccount(Account account) {
		Assert.notNull(account);
		List<OfflineSingleMessage> res=findByProperty("destUser", account.getId());
		return res;
	}

}
