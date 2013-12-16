package edu.buaa.scse.niu.wechat.engine.client;

import java.net.InetSocketAddress;
import java.util.List;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.UserRelationship;
import edu.buaa.scse.niu.wechat.engine.entity.factory.MsgDataFactory;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.MsgData;
import edu.buaa.scse.niu.wechat.engine.entity.request.LoginRequest;
import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;
import edu.buaa.scse.niu.wechat.engine.mina.filter.ChatProtocolCodecFactory;
import edu.buaa.scse.niu.wechat.engine.protocol.Protocol;
import edu.buaa.scse.niu.wechat.engine.protocol.ProtocolCommand;
import edu.buaa.scse.niu.wechat.engine.protocol.ProtocolFactory;

public class Client implements ClientInterface {

	private final static Logger LOGGER = LoggerFactory.getLogger(Client.class
			.getSimpleName());

	private int port;
	private IoConnector connector;

	/** The session */
	private static IoSession session;

	private ProtocolFactory protocolFactory;

	private Account user;

	private List<UserRelationship> friends;

	private List<ChatGroup> groups;

	private LoginRequest loginRequest;
	private RegisterRequest registerRequest;

	public Client(int port) {
		protocolFactory = new ClientProtocolFactory();
		this.port = port;
		connector = new NioSocketConnector();
		DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();
//		LoggingFilter log = new LoggingFilter("log");
//		log.setSessionIdleLogLevel(LogLevel.NONE);
//		log.setMessageReceivedLogLevel(LogLevel.DEBUG);

		// filterChain.addLast("logger1", log);
		filterChain.addLast("protocol", new ProtocolCodecFilter(
				new ChatProtocolCodecFactory()));
		// filterChain.addLast("logger2", log);
		connector.setHandler(new ChatClientHandler(this));
//		SocketSessionConfig dcfg = (SocketSessionConfig) connector
//				.getSessionConfig();

		user = new Account();
	}

	public void start() {
		ConnectFuture connFuture = connector.connect(new InetSocketAddress(
				"localhost", port));
		connFuture.awaitUninterruptibly();
		session = connFuture.getSession();
	}

	public void connect() {
		Protocol p = protocolFactory.createProtocol(ProtocolCommand.CMD_CONNECT);
		session.write(p);
	}

	public void register(RegisterRequest req) {
		LOGGER.info("Client register:" + req.toString());
		Protocol p = protocolFactory.createRegisterProtocol(req);
		session.write(p);
	}

	public void login(LoginRequest req) {
		Protocol p = protocolFactory.createLoginProtocol(req);
		session.write(p);
	}

	public void testSingleCast(int times, int gap_second) {
		try {
			for (int i = 0; i < times; i++) {
				MsgData data = MsgDataFactory
						.newText("Test singlecast message:" + i);
				Account friend = friends.get(0).getFriendAccount();
				SingleMessage msg = new SingleMessage(data, friend, user);
				Protocol p = protocolFactory.createSingleMsgProtocol(msg);
				session.write(p);
				System.out.println("client send:" + msg.toString());
				Thread.sleep(gap_second * 1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void testGroupCast(int times, int gap_second) {
		if(groups!=null && groups.size()>0){
			ChatGroup group=groups.get(0);
			try {
				for (int i = 0; i < times; i++) {
					MsgData data = MsgDataFactory
							.newText("Test singlecast message:" + i);
					GroupMessage msg = new GroupMessage(group, user, data);
					Protocol p = protocolFactory.createGroupMsgProtocol(msg);
					session.write(p);
					Thread.sleep(gap_second * 1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onRegisterResult(RegisterResponse result) {
		if (result.isSucc()) {
			user = result.getAccount();
			LOGGER.info("Client " + user.getName() + " register success");
		} else {
			LOGGER.info("Client " + " register fail:" + result.getErrors());
		}
		testSingleCast(2, 2);
	}

	@Override
	public void onLoginResult(LoginResponse result) {
		if (result.isSucc()) {
			user = result.getUser();
			LOGGER.info("Client " + user.getName() + " login success");
		} else {
			LOGGER.info("Client " + " login fail:" + result.getError());
		}

		// if (result.isSucc()) {
		// System.out.println("登录成功");
		// user = result.getUser();
		// // testSingleCast(10, 2);
		// testGroupCast(10,2);
		// }
	}

	@Override
	public void onReceiveMessage(SingleMessage msg) {
		LOGGER.info("Client " + user.getName() + " receive a single message");
		LOGGER.info(msg.toString());
		// System.out.println(msg.toString());
	}

	@Override
	public void onReceiveMessage(GroupMessage msg) {
		// System.out.println(msg.toString());
		LOGGER.info("Client " + user.getName() + " receive a group message");
		LOGGER.info(msg.toString());
	}

	@Override
	public void onConnected() {
		LOGGER.info("Client connected");
		if(loginRequest!=null){
			login(loginRequest);
		}else if(registerRequest!=null){
			register(registerRequest);
		}
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	public RegisterRequest getRegisterRequest() {
		return registerRequest;
	}

	public void setRegisterRequest(RegisterRequest registerRequest) {
		this.registerRequest = registerRequest;
	}

	public List<UserRelationship> getFriends() {
		return friends;
	}

	public void setFriends(List<UserRelationship> friends) {
		this.friends = friends;
	}

	public List<ChatGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<ChatGroup> groups) {
		this.groups = groups;
	}

	public LoginRequest getLoginRequest() {
		return loginRequest;
	}

	public void setLoginRequest(LoginRequest loginRequest) {
		this.loginRequest = loginRequest;
	}

}
