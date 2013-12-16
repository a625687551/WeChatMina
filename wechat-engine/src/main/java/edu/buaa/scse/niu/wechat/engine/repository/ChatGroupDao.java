package edu.buaa.scse.niu.wechat.engine.repository;

import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.Account;

public interface ChatGroupDao extends BaseDao<ChatGroup>{

	public List<ChatGroup> findByAccount(Account user);
}
