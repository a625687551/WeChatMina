package edu.buaa.scse.niu.wechat.engine.mina.filter;

import static org.apache.mina.statemachine.event.IoFilterEvents.*;

import org.apache.mina.core.filterchain.IoFilter.NextFilter;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.statemachine.StateControl;
import org.apache.mina.statemachine.annotation.IoFilterTransition;
import org.apache.mina.statemachine.annotation.State;
import org.apache.mina.statemachine.context.AbstractStateContext;
import org.springframework.beans.factory.annotation.Autowired;
import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupCastResult;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleCastResult;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.request.LoginRequest;
import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse.ErrorCode;
import edu.buaa.scse.niu.wechat.engine.mina.ServerProtocolFactory;
import edu.buaa.scse.niu.wechat.engine.mina.codec.CodecFactory;
import edu.buaa.scse.niu.wechat.engine.mina.codec.GroupMessageDecoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.LoginRequestDecoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.RegisterRequestDecoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.SingleMessageDecoder;
import edu.buaa.scse.niu.wechat.engine.protocol.Protocol;
import edu.buaa.scse.niu.wechat.engine.protocol.ProtocolCommand;
import edu.buaa.scse.niu.wechat.engine.protocol.ProtocolFactory;
import edu.buaa.scse.niu.wechat.engine.service.Services;
import edu.buaa.scse.niu.wechat.engine.service.SessionService;
import edu.buaa.scse.niu.wechat.engine.service.UserService;
import edu.buaa.scse.niu.wechat.engine.service.MessageService;

public class AuthenticationStateHandler {
	@State
	public static final String ROOT = "Root";
	@State(ROOT)
	public static final String START = "Start";
	@State(ROOT)
	public static final String WAIT_CONNECT = "WaitConnect";
	@State(ROOT)
	public static final String WAIT_LOGIN = "WaitLogin";
	/**
	 * 登录失败太多次，断开连接
	 */
	@State(ROOT)
	public static final String LOGIN_FAILED = "LoginFailed";
	/**
	 * 关闭连接
	 */
	@State(ROOT)
	public static final String DISCONNECT = "Disconnect";
	@State(ROOT)
	public static final String LOGGED = "Logged";

	private ProtocolFactory protocolFactory;

	@Autowired
	private Services services;

	public AuthenticationStateHandler() {
		super();
		protocolFactory = new ServerProtocolFactory();
	}

	public static class AuthenticationContext extends AbstractStateContext {
		public Account user;
		public int tries = 0;
	}

	@IoFilterTransition(on = SESSION_OPENED, in = START, next = WAIT_CONNECT)
	public void sendAuthRequest(NextFilter nextFilter, IoSession session) {
		// session.write("+ Greetings from ChatEngine!. Please connect ChatEngine.");

	}

	@IoFilterTransition(on = MESSAGE_RECEIVED, in = WAIT_CONNECT)
	public void connect(NextFilter nextFilter, IoSession session, Protocol p) {
		if (p.getCmdType() == ProtocolCommand.CMD_CONNECT) {
			Protocol res = protocolFactory.createResponseProtocol(p);
			session.write(res);
			StateControl.breakAndGotoNext(WAIT_LOGIN);
		}
	}

	@IoFilterTransition(on = MESSAGE_RECEIVED, in = WAIT_LOGIN)
	public void loginOrRegister(AuthenticationContext context, NextFilter nextFilter,
			IoSession session, Protocol p) {
		if (p.getCmdType() == ProtocolCommand.CMD_LOGIN) {
			login(context, nextFilter, session, p);
		}else if(p.getCmdType()==ProtocolCommand.CMD_REGISTER){
			register(context, nextFilter, session, p);
		}
	}
	
	private void login(AuthenticationContext context, NextFilter nextFilter,
			IoSession session, Protocol p){
		LoginRequestDecoder decoder = CodecFactory.getLoginRequestDecoder();
		LoginRequest request = decoder.decode(p.getData());

		UserService service = services.getUserService();
		LoginResponse response = service.login(request);

		// 失败次数过多，则将错误类型改变
		if (!response.isSucc()) {
			context.tries++;
			if (context.tries >= 5) {
				response.setError(ErrorCode.Too_Much_Try);
			}
		}

		// 向对方写入登录结果
		Protocol res = protocolFactory.createLoginResProtocol(response);
		session.write(res);

		if (response.isSucc()) {
			// 登录成功，状态机状态改变
			context.user = response.getUser();
			context.tries = 0;
			SessionService manager = services.getSessionService();
			manager.put(context.user, session);

			// For test
			// ChatGroupService
			// groupservice=serviceFactory.getChatGroupService();
			// ChatGroup group=groupservice.getChatGroup(1);
			// group.getUsers().add(context.user);
			// groupservice.updateChatGroup(group);

			StateControl.breakAndGotoNext(LOGGED);
		} else if (response.getError().equals(ErrorCode.Too_Much_Try)) {
			// 失败次数太多，断开连接
			StateControl.breakAndGotoNow(LOGIN_FAILED);
		}
	}
	
	public void register(AuthenticationContext context, NextFilter nextFilter,
			IoSession session, Protocol p) {
		if(p.getCmdType() == ProtocolCommand.CMD_REGISTER) {
			RegisterRequestDecoder decoder=CodecFactory.getRegisterRequestDecoder();
			RegisterRequest request=decoder.decode(p.getData());

			UserService service = services.getUserService();
			RegisterResponse response = service.register(request);

			// 向对方写入登录结果
			Protocol res = protocolFactory.createRegisterResProtocol(response);
			session.write(res);

			if (response.isSucc()) {
				// 登录成功，状态机状态改变
				context.user = response.getAccount();
				context.tries = 0;
				SessionService manager = services.getSessionService();
				manager.put(context.user, session);

				StateControl.breakAndGotoNext(LOGGED);
			}
		}
	}

	@IoFilterTransition(on = MESSAGE_RECEIVED, in = LOGGED, weight = 10)
	public void logoff(AuthenticationContext context, IoSession session,
			Protocol p) {
		if (p.getCmdType() == ProtocolCommand.CMD_LOGOFF) {
			// TODO 注销
			session.write("+ Bye! Please come back!").addListener(
					IoFutureListener.CLOSE);
		}

	}

	@IoFilterTransition(on = EXCEPTION_CAUGHT, in = ROOT, weight = 10)
	public void exceptionCaught(IoSession session, Exception e) {
		e.printStackTrace();
		session.close(true);
	}

	@IoFilterTransition(on = SESSION_CREATED, in = ROOT)
	public void sessionCreated(NextFilter nextFilter, IoSession session) {
		nextFilter.sessionCreated(session);
	}

	@IoFilterTransition(on = SESSION_OPENED, in = ROOT)
	public void sessionOpened(NextFilter nextFilter, IoSession session) {
		nextFilter.sessionOpened(session);
	}

	@IoFilterTransition(on = SESSION_CLOSED, in = LOGGED)
	public void sessionClosed(NextFilter nextFilter, IoSession session) {
		nextFilter.sessionClosed(session);
	}

	@IoFilterTransition(on = EXCEPTION_CAUGHT, in = LOGGED)
	public void exceptionCaught(NextFilter nextFilter, IoSession session,
			Throwable cause) {
		nextFilter.exceptionCaught(session, cause);
	}

	@IoFilterTransition(on = MESSAGE_RECEIVED, in = LOGGED)
	public void receiveMessage(NextFilter nextFilter, IoSession session,
			Protocol p) {
		MessageService service = services.getMessageService();
		if (p.getCmdType() == ProtocolCommand.CMD_SENDMSG) {
			SingleMessageDecoder decoder = CodecFactory
					.getSingleMessageDecoder();
			SingleMessage msg = decoder.decode(p.getData());

			// 转发到相应的session
			SingleCastResult result = service.singlecast(p, msg);

			nextFilter.messageReceived(session, result);
		} else if (p.getCmdType() == ProtocolCommand.CMD_SENDMSG_GROUP) {
			GroupMessageDecoder decoder = CodecFactory.getGroupMessageDecoder();
			GroupMessage msg = decoder.decode(p.getData());

			// 转发到改组各用户相应的session
			GroupCastResult result = service.groupcast(p, msg);

			nextFilter.messageReceived(session, result);
		}
	}

	@IoFilterTransition(on = MESSAGE_SENT, in = LOGGED)
	public void messageSent(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) {
		nextFilter.messageSent(session, writeRequest);
	}

	@IoFilterTransition(on = CLOSE, in = ROOT)
	public void filterClose(NextFilter nextFilter, IoSession session) {
		nextFilter.filterClose(session);
	}

	@IoFilterTransition(on = WRITE, in = ROOT)
	public void filterWrite(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) {
		nextFilter.filterWrite(session, writeRequest);
	}

}