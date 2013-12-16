package edu.buaa.scse.niu.wechat.engine.entity;

public class ChatMessageType {

	/**
	 * 纯文本消息
	 */
	public static final byte TYPE_TEXT = 0x01;
	/**
	 * 图片消息
	 */
	public static final byte TYPE_IMAGE = 0x03;
	/**
	 * 文本表情消息
	 */
	public static final byte TYPE_TEXT_ICON = 0x02;
	/**
	 * 语音消息
	 */
	public static final byte TYPE_VOICE = 0x04;
	/**
	 * 视频消息
	 */
	public static final byte TYPE_VIDEO = 0x05;
	/**
	 * 位置消息
	 */
	public static final byte TYPE_LOCATION = 0x06;
	/**
	 * 超链接消息
	 */
	public static final byte TYPE_HYPERLINK = 0x07;

	public enum MessageType {
		TEXT, VOICE, IMAGE, TEXTICON, VIDEO, LOCATION, HYPERLINK;
	}

	public static MessageType getMessageType(byte type) {
		switch (type) {
		case TYPE_TEXT:
			return MessageType.TEXT;
		case TYPE_TEXT_ICON:
			return MessageType.TEXTICON;
		case TYPE_IMAGE:
			return MessageType.IMAGE;
		case TYPE_VOICE:
			return MessageType.VOICE;
		case TYPE_VIDEO:
			return MessageType.VIDEO;
		case TYPE_LOCATION:
			return MessageType.LOCATION;
		case TYPE_HYPERLINK:
			return MessageType.HYPERLINK;
		default:
			return MessageType.TEXT;
		}
	}

	public static byte getTypeByte(MessageType type) {
		switch (type) {
		case TEXT:
			return TYPE_TEXT;
		case TEXTICON:
			return TYPE_TEXT_ICON;
		case IMAGE:
			return TYPE_IMAGE;
		case VOICE:
			return TYPE_VOICE;
		case VIDEO:
			return TYPE_VIDEO;
		case LOCATION:
			return TYPE_LOCATION;
		case HYPERLINK:
			return TYPE_HYPERLINK;
		default:
			return TYPE_TEXT;
		}
	}
}
