package edu.buaa.scse.niu.wechat.engine.protocol;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Protocol {

	public static final String TAG = "Protocol";

	public static final byte PACKSIGN = 0x16;

	/**
	 * 用户标识，用于区别android和ios用户 发送Login命令报文时
	 * 将CLIENT_ANDROID置于content字段的第一个byte，之后添加用户id
	 */
	public static final byte CLIENT_IOS = 0x01;
	public static final byte CLIENT_ANDROID = 0x02;

	public static final byte BYTE_NULL = 0x00;

	/**
	 * 最小包长 数据字段为空
	 */
	public static final int MIN_PACKAGE_LEAGTH = 15;

	/**
	 * 包起始字符 1byte
	 */
	private byte startSign;
	/**
	 * 命令字段 1byte
	 */
	private byte cmdType;
	/**
	 * 时间戳，标识该数据包的发起时间BCD码。如(2013-03-28 09:26:58)：20130328092658 7byte
	 */
	private Date time;
	/**
	 * 随机数，以后扩展用 1byte
	 */
	private byte random;
	/**
	 * 数据包内容部分总长度 4byte
	 */
	private int length;

	/**
	 * 数据包内容字段 长度为length值
	 */
	private byte[] data;
	/**
	 * 包中止字符 1byte
	 */
	private byte endSign; // 签名

	public Protocol() {
		super();
		startSign = PACKSIGN;
		endSign = PACKSIGN;
		time = new Date();
	}
	
	public Protocol(byte cmdType, byte[] content){
		this(cmdType,BYTE_NULL,content);
	}

	public Protocol(byte cmdType, byte random, byte[] content) {
		super();
		startSign = PACKSIGN;
		endSign = PACKSIGN;
		time = new Date();
		this.cmdType = cmdType;
		if(content==null){
			length=0;
		}else{
			length = content.length;
		}
		this.random = random;
		this.data=content;
//		this.terminal = StringUtils.getFixedLengthString(terminal, ID_LENGTH,
//				' ');
	}

	public String getProtocolString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Start:");
		sb.append(startSign);
		sb.append("; Cmd:");
//		sb.append(getCmdName(cmdType));
		sb.append("; Length:");
		sb.append(length);
		sb.append("; Time:");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
		String t = format.format(time);
		sb.append(t);
		sb.append("; Random:");
		sb.append(random);
		sb.append("; Content:");
//		if (cmdType == CMD_SENDMSG) {
//			ChatMessageProtocol cmp = ChatMessageProtocol
//					.getProtocolInstance(content);
//			sb.append(cmp.getProtocolString());
//		} else {
//			sb.append(content);
//		}
		sb.append("; End:");
		sb.append(endSign);
		String res = sb.toString();
		return res;
	}
	
	/**
	 * 检测协议是否合法，分别检测协议包开始标志、结束标志、数据字段长度是否正确
	 * @param p
	 * @return 
	 */
	public boolean isValid(){
		if(startSign!=PACKSIGN || endSign!=PACKSIGN){
			return false;
		}else if(length!=0 && length!=data.length){
			return false;
		}else {
			return true;
		}
	}

	public static String getTimeString(Date date) {
		String s = null;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		s = format.format(date);
		return s;
	}


	public byte getStartSign() {
		return startSign;
	}

	public void setStartSign(byte startSign) {
		this.startSign = startSign;
	}

	public byte getCmdType() {
		return cmdType;
	}

	public void setCmdType(byte cmdType) {
		this.cmdType = cmdType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public byte getRandom() {
		return random;
	}

	public void setRandom(byte random) {
		this.random = random;
	}

	public byte getEndSign() {
		return endSign;
	}

	public void setEndSign(byte endSign) {
		this.endSign = endSign;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
}
