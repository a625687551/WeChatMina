package edu.buaa.scse.niu.wechat.engine.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.buaa.scse.niu.wechat.engine.entity.ChatGroupAccount;
import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupCastResult;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.OfflineGroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.OfflineSingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleCastResult;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.protocol.Protocol;
import edu.buaa.scse.niu.wechat.engine.repository.GroupMessageDao;
import edu.buaa.scse.niu.wechat.engine.repository.OfflineGroupMsgDao;
import edu.buaa.scse.niu.wechat.engine.repository.OfflineSingleMsgDao;
import edu.buaa.scse.niu.wechat.engine.repository.SingleMessageDao;
import edu.buaa.scse.niu.wechat.engine.service.ChatGroupService;
import edu.buaa.scse.niu.wechat.engine.service.MessageService;
import edu.buaa.scse.niu.wechat.engine.service.Services;
import edu.buaa.scse.niu.wechat.engine.service.SessionService;

@Service
public class MessageServiceImpl implements MessageService{
	private final static Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class.getSimpleName());
	
	@Autowired
	private Services services;
	
	@Autowired
	private OfflineSingleMsgDao offlineSingleMsgDao;
	@Autowired
	private OfflineGroupMsgDao offlineGroupMsgDao;
	@Autowired
	private SingleMessageDao singleMessageDao;
	@Autowired
	private GroupMessageDao groupMessageDao;
	
	@Override
	public SingleCastResult singlecast(Protocol p,SingleMessage msg) {
		SessionService service=services.getSessionService();
		IoSession session=service.getIoSession(msg.getDestUser().getId());
		SingleCastResult result;
		boolean online=false;
		if(session==null){
			result=new SingleCastResult(msg, false);
		}else{
			session.write(p);
			online=true;
			result=new SingleCastResult(msg, true);
		}
		LOGGER.debug("singlecast online="+online);
		return result;
	}

	@Override
	public GroupCastResult groupcast(Protocol p,GroupMessage msg) {
		SessionService service=services.getSessionService();
		List<ChatGroupAccount> users=getUsersByGroup(msg.getGroup().getId());
		List<Account> offlineList=new ArrayList<Account>();
		int online=0;
		int offline=0;
		for(ChatGroupAccount cu:users){
			int id=cu.getId().getAccountId();
			IoSession session=service.getIoSession(id);
			if(session==null){
				offlineList.add(cu.getAccount());
				offline++;
			}else{
				session.write(p);
				online++;
			}
		}
		GroupCastResult result=new GroupCastResult(msg, offlineList);
		LOGGER.info("groupcast online:"+online+" offline:"+offline);
		return result;
	}
	
	private List<ChatGroupAccount> getUsersByGroup(int groupId){
		ChatGroupService gservice=services.getChatGroupService();
		List<ChatGroupAccount> users=gservice.getChatGroupUsers(groupId);
		return users;
	}

	@Override
	public void save(SingleCastResult singleCastResult) {
		if(singleCastResult.isOnline()){
			//发送成功储存到消息表
			singleMessageDao.save(singleCastResult.getMsg());
		}else{
			//对方离线 储存到临时表
			OfflineSingleMessage offlineMsg=new OfflineSingleMessage(singleCastResult.getMsg());
			offlineSingleMsgDao.save(offlineMsg);
		}
	}

	@Override
	public void save(GroupCastResult groupCastResult) {
		List<Account> users=groupCastResult.getOfflineUsers();
		GroupMessage msg=groupCastResult.getMsg();
		
		//储存离线消息
		if(users.size()>0){
			List<OfflineGroupMessage> offmsgs = new ArrayList<OfflineGroupMessage>();
			for(Account user:users){
				OfflineGroupMessage offmsg=new OfflineGroupMessage(user, msg);
				offmsgs.add(offmsg);
			}
			offlineGroupMsgDao.save(offmsgs);
		}
		
		//储存到群聊消息表
		groupMessageDao.save(msg);
		
	}

	@Transactional
	@Override
	public List<SingleMessage> getOfflineSingleMsg(Account user) {
		List<OfflineSingleMessage> offmsgs=offlineSingleMsgDao.findByAccount(user);
		
		List<SingleMessage> msgs=new ArrayList<>();
		for(int i=0,size=offmsgs.size();i<size;i++){
			OfflineSingleMessage msg=offmsgs.get(i);
			msgs.add(msg.getMessage());
		}
		
		offlineSingleMsgDao.delete(offmsgs);
		return msgs;
	}

	@Transactional
	@Override
	public List<GroupMessage> getOfflineGroupMsg(Account user) {
		List<OfflineGroupMessage> offmsgs=offlineGroupMsgDao.findByAccount(user);
		
		List<GroupMessage> msgs=new ArrayList<>();
		for(int i=0,size=offmsgs.size();i<size;i++){
			OfflineGroupMessage msg=offmsgs.get(i);
			msgs.add(msg.getMessage());
		}
		
		offlineGroupMsgDao.delete(offmsgs);
		return msgs;
	}
	
}
