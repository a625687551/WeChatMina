package edu.buaa.scse.niu.wechat.engine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.buaa.scse.niu.wechat.engine.service.ChatGroupService;
import edu.buaa.scse.niu.wechat.engine.service.FriendService;
import edu.buaa.scse.niu.wechat.engine.service.MessageService;
import edu.buaa.scse.niu.wechat.engine.service.PasswordService;
import edu.buaa.scse.niu.wechat.engine.service.Services;
import edu.buaa.scse.niu.wechat.engine.service.SessionService;
import edu.buaa.scse.niu.wechat.engine.service.UserService;
import edu.buaa.scse.niu.wechat.engine.service.validator.RegisterValidator;

@Service
public class ServiceFactory implements Services {
	@Autowired
	private UserService userService;
	@Autowired
	private ChatGroupService chatGroupService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private PasswordService passwordService;
	@Autowired
	private RegisterValidator registerValidator;
	@Autowired
	private FriendService friendService;

	public ServiceFactory() {

	}

	@Override
	public MessageService getMessageService() {
		return messageService;
	}

	@Override
	public SessionService getSessionService() {
		return sessionService;
	}

	@Override
	public UserService getUserService() {
		return userService;
	}

	@Override
	public ChatGroupService getChatGroupService() {
		return chatGroupService;
	}

	@Override
	public PasswordService getPasswordService() {
		return passwordService;
	}

	@Override
	public RegisterValidator getRegisterValidator() {
		return registerValidator;
	}

	@Override
	public FriendService getFriendService() {
		return friendService;
	}

}
