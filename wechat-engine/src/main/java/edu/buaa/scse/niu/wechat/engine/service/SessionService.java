package edu.buaa.scse.niu.wechat.engine.service;

import org.apache.mina.core.session.IoSession;

import edu.buaa.scse.niu.wechat.engine.entity.Account;

public interface SessionService {
	
	public IoSession getIoSession(int userId);

	public Account getUser(int userId);

	public boolean put(Account user, IoSession session);

	public boolean remove(int userId);
}
