package edu.buaa.scse.niu.wechat.engine.mina.filter;

import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.statemachine.StateMachine;
import org.apache.mina.statemachine.StateMachineFactory;
import org.apache.mina.statemachine.StateMachineProxyBuilder;
import org.apache.mina.statemachine.annotation.IoFilterTransition;
import org.apache.mina.statemachine.context.IoSessionStateContextLookup;
import org.apache.mina.statemachine.context.StateContext;
import org.apache.mina.statemachine.context.StateContextFactory;


/**
 * 聊天引擎使用的IoFilter工厂 生成引擎所需的过滤器
 * 其中包括编解码过滤器(ChatProtocolCodecFactory)、状态机过滤器(ClientStateHandler)
 * 
 * @author Niu
 * 
 */
public class ChatEngineFilterFactory {

	public enum FilterType {
		Codec, Authen
	}

	/**
	 * 根据请求的种类返回不同的IoFilter
	 * @param type
	 * @return
	 */
	public static IoFilter getFilter(FilterType type) {
		switch (type) {
		case Authen:
			return getAuthenFilter();
		case Codec:
			return getCodecFilter();
		}
		return null;
	}

	public static IoFilter getCodecFilter() {
		return new ProtocolCodecFilter(new ChatProtocolCodecFactory());
	}

	public static IoFilter getAuthenFilter() {
		return getAuthenFilter(new AuthenticationStateHandler());
	}
	
	public static IoFilter getAuthenFilter(AuthenticationStateHandler handler){
		StateMachine sm = StateMachineFactory.getInstance(
				IoFilterTransition.class).create(AuthenticationStateHandler.START,
						handler);

		return new StateMachineProxyBuilder()
				.setStateContextLookup(
						new IoSessionStateContextLookup(
								new StateContextFactory() {
									public StateContext create() {
										return new AuthenticationStateHandler.AuthenticationContext();
									}
								}, "authContext"))
				.setIgnoreUnhandledEvents(true)
				.setIgnoreStateContextLookupFailure(true)
				.create(IoFilter.class, sm);
	}
}
