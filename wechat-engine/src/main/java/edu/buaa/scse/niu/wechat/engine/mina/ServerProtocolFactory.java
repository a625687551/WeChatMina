package edu.buaa.scse.niu.wechat.engine.mina;

import org.springframework.stereotype.Component;

import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.RespondMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.MsgData;
import edu.buaa.scse.niu.wechat.engine.entity.request.LoginRequest;
import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;
import edu.buaa.scse.niu.wechat.engine.mina.codec.CodecFactory;
import edu.buaa.scse.niu.wechat.engine.mina.codec.GroupMessageEncoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.LoginRequestEncoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.LoginResponseEncoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.RegisterRequestEncoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.RegisterResponseEncoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.RespondMegEncoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.SingleMessageEncoder;
import edu.buaa.scse.niu.wechat.engine.protocol.Protocol;
import edu.buaa.scse.niu.wechat.engine.protocol.ProtocolCommand;
import edu.buaa.scse.niu.wechat.engine.protocol.ProtocolFactory;

@Component
public class ServerProtocolFactory implements ProtocolFactory,ProtocolCommand {

	@Override
	public Protocol createProtocol(byte type) {
		Protocol ret = new Protocol(type, null);
		return ret;
	}

	@Override
	public Protocol createResponseProtocol(Protocol p) {
		byte cmd = p.getCmdType();
		Protocol ret = null;
		switch (cmd) {
		case CMD_CONNECT:
			ret = createProtocol(CMD_CONNECT_RES);
			break;
		case CMD_CLIENT_EXIT:
			ret = createProtocol(CMD_SERVER_EXIT);
			break;
		case CMD_GETMSG_OFFLINE:
			ret = createProtocol(CMD_GETMSG_OFFLINE_RES);
			break;
		case CMD_HEARTBEAT:
			ret = createProtocol(CMD_HEARTBEAT_RES);
			break;
		case CMD_LOGIN:
			break;
		case CMD_LOGOFF:
			ret = createProtocol(CMD_LOGOFF_RES);
			break;
		case CMD_SENDMSG:
			break;
		case CMD_SENDMSG_GROUP:
			break;
		case CMD_SENDMSG_GROUP_RES:
			break;
		case CMD_SENDMSG_RES:
			break;
		case CMD_SERVER_EXIT:
			break;
		default:
			break;
		}
		return ret;
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
		LoginResponseEncoder encoder=CodecFactory.getLoginResponseEncoder();
		byte[] data=encoder.encode(response);
		Protocol p = new Protocol(ProtocolCommand.CMD_LOGIN_RES, data);
		return p;
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
		MsgData data=msg.getMessage();
		RespondMessage res = new RespondMessage(msg.getId(),
				data.getMsgType());
		
		RespondMegEncoder encoder=CodecFactory.getRespondMegEncoder();
		byte[] bts=encoder.encode(res);
		Protocol p = new Protocol(ProtocolCommand.CMD_SENDMSG_RES, bts);
		return p;
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
		MsgData data=msg.getMessage();
		RespondMessage res=new RespondMessage(msg.getId(), data.getMsgType());
		RespondMegEncoder encoder=CodecFactory.getRespondMegEncoder();
		byte[] bts=encoder.encode(res);
		Protocol p = new Protocol(ProtocolCommand.CMD_SENDMSG_GROUP_RES, bts);
		return p;
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
