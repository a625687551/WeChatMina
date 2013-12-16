package edu.buaa.scse.niu.wechat.engine.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;
import edu.buaa.scse.niu.wechat.engine.repository.GroupMessageDao;

@Repository
public class GroupMessageDaoImpl extends BaseDaoImpl<GroupMessage> implements GroupMessageDao{

	@Override
	public List<GroupMessage> findByChatGroup(ChatGroup group) {
		Assert.notNull(group);
		return findByProperty("group", group.getId());
	}

}
