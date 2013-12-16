package edu.buaa.scse.niu.wechat.engine.service;

import edu.buaa.scse.niu.wechat.engine.service.validator.RegisterValidator;

public interface Services {

	public MessageService getMessageService();

	public SessionService getSessionService();

	public UserService getUserService();

	public ChatGroupService getChatGroupService();
	
	public PasswordService getPasswordService();
	
	public RegisterValidator getRegisterValidator();
	
	public FriendService getFriendService();
}
