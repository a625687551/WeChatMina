package edu.buaa.scse.niu.wechat.engine.repository;

import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.msg.OfflineSingleMessage;

public interface OfflineSingleMsgDao extends BatchDao<OfflineSingleMessage>{

	public List<OfflineSingleMessage> findByAccount(Account user);
}
