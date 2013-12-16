package edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata;

import java.awt.image.BufferedImage;
import java.util.Date;

import edu.buaa.scse.niu.wechat.engine.entity.ChatMessageType.MessageType;

public class ImageMsgData extends FileMsgData {

	public ImageMsgData(String path, String filename, byte[] dataBytes) {
		super(MessageType.IMAGE, path, filename, dataBytes, 0);
	}

	public ImageMsgData(Date createTime, String path, String filename,
			byte[] dataBytes) {
		super(MessageType.IMAGE, createTime, path, filename, dataBytes, 0);
	}

	public static final String TAG = ImageMsgData.class.getSimpleName();

	private BufferedImage image;

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
