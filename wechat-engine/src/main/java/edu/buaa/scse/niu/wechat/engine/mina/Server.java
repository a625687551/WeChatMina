package edu.buaa.scse.niu.wechat.engine.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.handler.chain.ChainedIoHandler;
import org.apache.mina.handler.chain.IoHandlerChain;
import org.apache.mina.statemachine.StateMachine;
import org.apache.mina.statemachine.StateMachineFactory;
import org.apache.mina.statemachine.StateMachineProxyBuilder;
import org.apache.mina.statemachine.annotation.IoFilterTransition;
import org.apache.mina.statemachine.context.IoSessionStateContextLookup;
import org.apache.mina.statemachine.context.StateContext;
import org.apache.mina.statemachine.context.StateContextFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import edu.buaa.scse.niu.wechat.engine.mina.filter.AuthenticationStateHandler;
import edu.buaa.scse.niu.wechat.engine.mina.filter.ChatProtocolCodecFactory;
import edu.buaa.scse.niu.wechat.engine.mina.handler.DatabaseIoHandlerCommand;



public class Server {
	private IoAcceptor acceptor;
	private int port;

	public static void main(String[] args) throws IOException {
		Server server=new Server(3456);
		server.start();
	}
	
	public Server(int port){
		this.port=port;
		acceptor = new NioSocketAcceptor();
		DefaultIoFilterChainBuilder filterChain=acceptor.getFilterChain();
//		filterChain.addLast("logger", new LoggingFilter());
		filterChain.addLast("protocol", new ProtocolCodecFilter(new ChatProtocolCodecFactory()));
		filterChain.addLast("stateMachine", createAuthenticationIoFilter());
		
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		
		acceptor.setHandler(createIoHandler());
	}
	
	public void start(){
		try {
			acceptor.bind(new InetSocketAddress(port));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private IoFilter createAuthenticationIoFilter() {
        StateMachine sm = StateMachineFactory.getInstance(
                IoFilterTransition.class).create(AuthenticationStateHandler.START,
                new AuthenticationStateHandler());

        return new StateMachineProxyBuilder().setStateContextLookup(
                new IoSessionStateContextLookup(new StateContextFactory() {
                    public StateContext create() {
                        return new AuthenticationStateHandler.AuthenticationContext();
                    }
                }, "authContext")).setIgnoreUnhandledEvents(true).setIgnoreStateContextLookupFailure(true).create(
                IoFilter.class, sm);
    }
	
	private ChainedIoHandler createIoHandler(){
		IoHandlerChain chain=new IoHandlerChain();
		chain.addLast("db", new DatabaseIoHandlerCommand());
		ChainedIoHandler handler=new ChainedIoHandler(chain);
		return handler;
	}
}
