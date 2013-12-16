package edu.buaa.scse.niu.wechat.engine.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;
import edu.buaa.scse.niu.wechat.engine.mina.codec.CodecFactory;
import edu.buaa.scse.niu.wechat.engine.mina.codec.JsonDecoder;
import edu.buaa.scse.niu.wechat.engine.protocol.Protocol;
import edu.buaa.scse.niu.wechat.engine.protocol.ProtocolCommand;

public class ChatClientHandler extends IoHandlerAdapter{

	private Client client;
	
	public ChatClientHandler(Client client) {
		super();
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionCreated(session);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionOpened(session);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if(message instanceof Protocol){
			Protocol p=(Protocol) message;
			JsonDecoder decoder;
			switch(p.getCmdType()){
			case ProtocolCommand.CMD_CONNECT_RES:
				client.onConnected();
				break;
			case ProtocolCommand.CMD_REGISTER_RES:
				decoder=CodecFactory.getRegisterResponseDecoder();
				RegisterResponse reg=(RegisterResponse) decoder.decode(p.getData());
				client.onRegisterResult(reg);
			case ProtocolCommand.CMD_LOGIN_RES:
				decoder=CodecFactory.getLoginResponseDecoder();
				LoginResponse result=(LoginResponse) decoder.decode(p.getData());
				client.onLoginResult(result);
				break;
			case ProtocolCommand.CMD_SENDMSG:
				decoder=CodecFactory.getSingleMessageDecoder();
				SingleMessage msg=(SingleMessage) decoder.decode(p.getData());
				client.onReceiveMessage(msg);
				break;
			}
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		super.messageSent(session, message);
	}

}
