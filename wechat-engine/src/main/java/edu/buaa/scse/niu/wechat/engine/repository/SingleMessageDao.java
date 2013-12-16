package edu.buaa.scse.niu.wechat.engine.repository;

import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;

public interface SingleMessageDao extends BaseDao<SingleMessage>{

	public List<SingleMessage> findByRelationship(Account src,Account dest);
}
