package edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata;

import java.io.File;
import java.util.Date;

import edu.buaa.scse.niu.wechat.engine.entity.ChatMessageType.MessageType;

public class FileMsgData extends MsgData {

	protected byte[] dataBytes;

	protected File file;

	/**
	 * 
	 * @param messageType
	 *            消息类型
	 * @param path
	 *            文件路径
	 * @param filename
	 *            原始文件名称
	 * @param dataBytes
	 *            比特数据
	 * @param length
	 *            音视频时长
	 */
	public FileMsgData(MessageType msgType, String path, String filename,
			byte[] dataBytes, int length) {
		super(msgType, path, dataBytes.length, filename, length);
		this.dataBytes = dataBytes;
	}

	public FileMsgData(MessageType msgType, Date createTime, String path,
			String filename, byte[] dataBytes, int length) {
		super(msgType, createTime, path, dataBytes.length, filename, length);
		this.dataBytes=dataBytes;
	}

	public byte[] getDataBytes() {
		return dataBytes;
	}

	public void setDataBytes(byte[] dataBytes) {
		this.dataBytes = dataBytes;
	}

}
