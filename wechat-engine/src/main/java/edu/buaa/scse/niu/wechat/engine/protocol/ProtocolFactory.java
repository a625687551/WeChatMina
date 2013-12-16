package edu.buaa.scse.niu.wechat.engine.protocol;

import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.request.LoginRequest;
import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;

public interface ProtocolFactory {

	public Protocol createProtocol(byte type);

	public Protocol createResponseProtocol(Protocol p);

	public Protocol createLoginProtocol(LoginRequest request);

	public Protocol createLoginResProtocol(LoginResponse response);
	
	public Protocol createRegisterProtocol(RegisterRequest request);
	
	public Protocol createRegisterResProtocol(RegisterResponse response);

	public Protocol createSingleMsgProtocol(SingleMessage msg);

	public Protocol createSingleMsgResProtocol(SingleMessage msg);

	public Protocol createGroupMsgProtocol(GroupMessage msg);

	public Protocol createGroupMsgResProtocol(GroupMessage msg);

}
