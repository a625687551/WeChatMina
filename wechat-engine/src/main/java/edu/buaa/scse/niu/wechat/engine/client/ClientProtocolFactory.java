package edu.buaa.scse.niu.wechat.engine.client;

import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.request.LoginRequest;
import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;
import edu.buaa.scse.niu.wechat.engine.mina.codec.CodecFactory;
import edu.buaa.scse.niu.wechat.engine.mina.codec.GroupMessageEncoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.LoginRequestEncoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.RegisterRequestEncoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.RegisterResponseEncoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.SingleMessageEncoder;
import edu.buaa.scse.niu.wechat.engine.protocol.Protocol;
import edu.buaa.scse.niu.wechat.engine.protocol.ProtocolCommand;
import edu.buaa.scse.niu.wechat.engine.protocol.ProtocolFactory;

public class ClientProtocolFactory implements ProtocolFactory{

	@Override
	public Protocol createProtocol(byte type) {
		Protocol ret = new Protocol(type, null);
		return ret;
	}

	@Override
	public Protocol createResponseProtocol(Protocol p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Protocol createLoginProtocol(LoginRequest request) {
		LoginRequestEncoder encoder=CodecFactory.getLoginRequestEncoder();
		byte[] data=encoder.encode(request);
		Protocol p = new Protocol(ProtocolCommand.CMD_LOGIN, data);
		return p;
	}

	@Override
	public Protocol createLoginResProtocol(LoginResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Protocol createSingleMsgProtocol(SingleMessage msg) {
		SingleMessageEncoder encoder=CodecFactory.getSingleMessageEncoder();
		byte[] data=encoder.encode(msg);
		Protocol p = new Protocol(ProtocolCommand.CMD_SENDMSG, data);
		return p;
	}

	@Override
	public Protocol createSingleMsgResProtocol(SingleMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Protocol createGroupMsgProtocol(GroupMessage msg) {
		GroupMessageEncoder encoder=CodecFactory.getGroupMessageEncoder();
		byte[] data=encoder.encode(msg);
		Protocol p=new Protocol(ProtocolCommand.CMD_SENDMSG_GROUP,data);
		return p;
	}

	@Override
	public Protocol createGroupMsgResProtocol(GroupMessage msg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Protocol createRegisterProtocol(RegisterRequest request) {
		RegisterRequestEncoder encoder=CodecFactory.getRegisterRequestEncoder();
		byte[] data=encoder.encode(request);
		Protocol p=new Protocol(ProtocolCommand.CMD_REGISTER, data);
		return p;
	}

	@Override
	public Protocol createRegisterResProtocol(RegisterResponse response) {
		RegisterResponseEncoder encoder=CodecFactory.getRegisterResponseEncoder();
		byte[] data=encoder.encode(response);
		Protocol p=new Protocol(ProtocolCommand.CMD_REGISTER_RES, data);
		return p;
	}

}
