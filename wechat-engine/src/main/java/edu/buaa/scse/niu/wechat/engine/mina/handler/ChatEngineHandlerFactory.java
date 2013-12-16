package edu.buaa.scse.niu.wechat.engine.mina.handler;

import java.util.Map;
import java.util.Set;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.handler.chain.ChainedIoHandler;
import org.apache.mina.handler.chain.IoHandlerChain;
import org.apache.mina.handler.chain.IoHandlerCommand;

public class ChatEngineHandlerFactory {
	
	public static IoHandler getIoHandler(Map<String,IoHandlerCommand> commands) {
		IoHandlerChain chain = new IoHandlerChain();
		Set<String> keyset=commands.keySet();
		for(String key:keyset){
			IoHandlerCommand cmd=commands.get(key);
			chain.addLast(key, cmd);
		}
//		chain.addLast("db", new DatabaseIoHandler());
		ChainedIoHandler handler = new ChainedIoHandler(chain);
		return handler;
	}
	
}
