package edu.buaa.scse.niu.wechat.engine.service;

import java.util.List;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupCastResult;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleCastResult;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.protocol.Protocol;

public interface MessageService {

	/**
	 * 单人聊天时，消息引擎转发聊天消息
	 * @param destId
	 * @param p
	 * @param msg
	 */
	public SingleCastResult singlecast(Protocol p,SingleMessage msg);
	
	/**
	 * 群聊时 消息引擎向组内群发聊天信息
	 * @param p	聊天内容对应的底层协议对象
	 * @param msg	聊天内容
	 * @return	发送结果
	 */
	public GroupCastResult groupcast(Protocol p,GroupMessage msg);
	
	/**
	 * 储存单聊消息 根据转发结果决定是储存于 临时消息表or消息表
	 * @param singleCastResult
	 */
	public void save(SingleCastResult singleCastResult);
	
	/**
	 * 储存群发消息 根据转发结果决定是储存于 临时消息表or消息表
	 * @param groupCastResult
	 */
	public void save(GroupCastResult groupCastResult);
	
	/**
	 * 查找给定用户的离线单聊消息
	 * @param user
	 * @return
	 */
	public List<SingleMessage> getOfflineSingleMsg(Account user);
	
	/**
	 * 查找给定用户的离线群聊消息
	 * @param user
	 * @return
	 */
	public List<GroupMessage> getOfflineGroupMsg(Account user);
}
