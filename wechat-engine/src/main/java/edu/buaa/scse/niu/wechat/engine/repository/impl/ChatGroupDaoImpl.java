package edu.buaa.scse.niu.wechat.engine.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.repository.ChatGroupDao;

@Repository
public class ChatGroupDaoImpl extends BaseDaoImpl<ChatGroup> implements
		ChatGroupDao {

	@Override
	public List<ChatGroup> findByAccount(Account user) {
//		Query query = getSession()
//				.createQuery(
//						"from Customer c innerjoin c.orders o where c.name like ‘zhao%’ ");
//		List list = query.list();
//		List<ChatGroup> res=getSession()
//				.createCriteria(ChatGroup.class)
//				.createCriteria(ChatGroup.USERS_COLUMN_NAME,
//						JoinType.INNER_JOIN)
//				.add(Restrictions.eq(ChatGroup.USERS_COLUMN_NAME, user)).list();
//
//		return res;
		//TODO
		return null;
	}

}
