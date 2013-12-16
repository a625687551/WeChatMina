package edu.buaa.scse.niu.wechat.engine.repository;

import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.msg.OfflineGroupMessage;

public interface OfflineGroupMsgDao extends BatchDao<OfflineGroupMessage>{

	public List<OfflineGroupMessage> findByAccount(Account user);
}
