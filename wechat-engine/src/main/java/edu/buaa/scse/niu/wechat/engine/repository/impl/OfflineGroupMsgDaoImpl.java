package edu.buaa.scse.niu.wechat.engine.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.msg.OfflineGroupMessage;
import edu.buaa.scse.niu.wechat.engine.repository.OfflineGroupMsgDao;

@Repository
public class OfflineGroupMsgDaoImpl extends BaseBatchDaoImpl<OfflineGroupMessage> implements OfflineGroupMsgDao{

	@Override
	public List<OfflineGroupMessage> findByAccount(Account user) {
		Assert.notNull(user);
		List<OfflineGroupMessage> res=findByProperty("destUser", user.getId());
		return res;
	}

}
