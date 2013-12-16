package edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import edu.buaa.scse.niu.wechat.engine.entity.ChatMessageType.MessageType;



/**
 * 持久化消息内容时使用的实体类
 * @author niu
 *
 */
@Embeddable
public class MsgData {
	public static final String TAG = MsgData.class.getSimpleName();

	public static final int HEADER_LENGTH=5;
	public static final String MSG_TYPE = "MSG_TYPE";
	public static final String CREATE_TIME = "CREATE_TIME";
	public static final String MSG_STATE = "MSG_STATE";
	public static final String RECEIVE_TIME = "RECEIVE_TIME";
	public static final String DATA="DATA";
	public static final String FILESIZE="FILESIZE";
	public static final String FILENAME="FILENAME";
	public static final String LENGTH="LENGTH";
	
//	public static final String ID = "ID";

	/**
	 * msgid: 消息ID (当前时间毫秒数) msgType: 消息类型 (文本、文件、图片) sendTime: 发送时间
	 * msgContent：消息内容(非文本形式填写文件所在的路径) crateDate： 消息创建时间 msgExpA ： 扩展A msgExpB ：
	 * 扩展B msgExpC ： 扩展C
	 */

	public enum MessageState {
		IDLE, SENDING, SENT, SEND_FAIL, RECEIVE
	}

	/**
	 * 消息类型 1byte
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = MSG_TYPE, nullable = false, updatable = false)
	protected MessageType msgType;

	/**
	 * 对于自己发送的消息表示消息生成时间，对于接收的消息表示接收时间 7byte
	 */
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = CREATE_TIME)
	protected Date createTime;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = RECEIVE_TIME)
	protected Date receiveTime;
	/**
	 * 消息状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = MSG_STATE, nullable = false)
	protected MessageState state;
	
	/**
	 * 文本消息-记录消息内容
	 * 文件消息（语音、图片、视频）-记录消息对应文件的路径
	 */
	@Column(name=DATA,length=200,nullable=false)
	protected String data;
	
	/**
	 * 文本消息-文本字符串长度
	 * 文件消息（语音、图片、视频）-文件大小
	 */
	@Column(name=FILESIZE)
	protected int filesize;
	
	/**
	 * 文本消息-null
	 * 文件消息-文件原始名称
	 */
	@Column(name=FILENAME,length=100)
	protected String filename;
	
	/**
	 * 语音、视频消息的时长
	 */
	@Column(name=LENGTH)
	protected int length;
	
	public MsgData() {
	}

	/**
	 * 客户端新建消息
	 * @param msgType 	消息类型
	 * @param data		消息数据（文本内容、路径）
	 * @param filesize	文件大小
	 * @param filename	文件原始名
	 * @param length	音视频时长
	 */
	public MsgData(MessageType msgType, String data, int filesize,
			String filename, int length) {
		this.data = data;
		this.filesize = filesize;
		this.filename = filename;
		this.length = length;
		this.msgType = msgType;
		state = MessageState.IDLE;
		this.createTime=new Date();
	}
	
	/**
	 * 客户端接收消息
	 * @param msgType
	 * @param createTime
	 * @param data
	 * @param filesize
	 * @param filename
	 * @param length
	 */
	public MsgData(MessageType msgType, Date createTime, String data,
			int filesize, String filename, int length) {
		this(msgType, data, filesize, filename, length);
		this.createTime=createTime;
		this.receiveTime=new Date();
	}

	
//	/**
//	 * 通过复制构造，用于生成子类对象
//	 * 
//	 * @param msg
//	 */
//	public MsgData(MsgData msg) {
//		msgType = msg.getMsgType();
//		state = msg.getState();
//	}

	@Override
	public String toString() {
		return "MsgData [msgType=" + msgType + ", createTime=" + createTime
				+ ", receiveTime=" + receiveTime + ", state=" + state
				+ ", data=" + data + ", filesize=" + filesize + ", filename="
				+ filename + ", length=" + length + "]";
	}
	
	public MessageState getState() {
		return state;
	}

	public void setState(MessageState state) {
		this.state = state;
	}

//	public String getMsgId() {
//		return msgId;
//	}
//
//	public void setMsgId(String msgId) {
//		this.msgId = msgId;
//	}

	public MessageType getMsgType() {
		return msgType;
	}

	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	public int getFilesize() {
		return filesize;
	}

	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
}
