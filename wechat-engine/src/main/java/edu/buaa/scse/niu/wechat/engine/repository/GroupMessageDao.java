package edu.buaa.scse.niu.wechat.engine.repository;

import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;

public interface GroupMessageDao extends BaseDao<GroupMessage>{

	public List<GroupMessage> findByChatGroup(ChatGroup group);
}
