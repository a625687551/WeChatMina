package edu.buaa.scse.niu.wechat.engine.mina.handler;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.chain.IoHandlerCommand;
import org.springframework.beans.factory.annotation.Autowired;

import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupCastResult;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleCastResult;
import edu.buaa.scse.niu.wechat.engine.service.MessageService;
import edu.buaa.scse.niu.wechat.engine.service.Services;

/**
 * IOHandler 处理数据持久化相关操作
 * @author Niu
 *
 */
public class DatabaseIoHandlerCommand implements IoHandlerCommand{

	@Autowired
	private Services serviceFactory;
//	@Autowired
//	private MessageService messageService;
	
//	public ServiceFactory getServiceFactory() {
//		return serviceFactory;
//	}
//
//	public void setServiceFactory(ServiceFactory serviceFactory) {
//		this.serviceFactory = serviceFactory;
//	}

	public DatabaseIoHandlerCommand(){
		
	}
	
	@Override
	public void execute(NextCommand next, IoSession session, Object message)
			throws Exception {
//		MessageService service=messageService;
		MessageService service=serviceFactory.getMessageService();
		if(message instanceof SingleCastResult){
			SingleCastResult result=(SingleCastResult) message;
			service.save(result);
		}else if(message instanceof GroupCastResult){
			GroupCastResult result=(GroupCastResult)message;
			service.save(result);
		}else {
			
		}
		next.execute(session, message);
	}

}
