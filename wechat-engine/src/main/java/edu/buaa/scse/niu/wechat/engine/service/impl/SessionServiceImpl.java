package edu.buaa.scse.niu.wechat.engine.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Service;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.service.SessionService;

@Service
public class SessionServiceImpl implements SessionService {
	/**
	 * 储存当前所有已登录用户对应的session key:userId value:IoSession
	 */
	private final Map<Integer, IoSession> sessionMap = new ConcurrentHashMap<>();

	private final Map<Integer, Account> userMap = new ConcurrentHashMap<>();

	@Override
	public IoSession getIoSession(int userId) {
		IoSession res = sessionMap.get(userId);
		return res;
	}

	@Override
	public Account getUser(int userId) {
		Account u = userMap.get(userId);
		return u;
	}

	@Override
	public boolean put(Account user, IoSession session) {
		int userId = user.getId();
		if (userId < 0) {
			return false;
		}
		sessionMap.put(userId, session);
		userMap.put(userId, user);
		return true;
	}

	@Override
	public boolean remove(int userId) {
		if (sessionMap.containsKey(userId)) {
			sessionMap.remove(userId);
		}
		if (sessionMap.containsKey(userId)) {
			userMap.remove(userId);
		}
		return true;
	}

}
