package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.ChatGroup;
import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.MsgData;

public class GroupMessageEncoder extends JsonEncoder<GroupMessage> {

	private MsgDataEncoder dataEncoder;

	public GroupMessageEncoder() {
		super();
		dataEncoder = new MsgDataEncoder();
	}

	public GroupMessageEncoder(Charset charset) {
		super(charset);
		dataEncoder = new MsgDataEncoder(charset);
	}

	@Override
	public byte[] encode(GroupMessage msg) {
		fixNullField(msg);

		byte[] data=dataEncoder.encode(msg.getMessage());
		int datalength=data.length;
		
		IoBuffer buffer = IoBuffer.allocate(SingleMessage.HEADER_LENGTH+datalength,
				false);
		buffer.setAutoExpand(true);

		// 消息Id--发送者Id--接收组Id--消息内容
		try {
			buffer.putString(msg.getId(), 36, encoder);
			buffer.putInt(msg.getSrcUser().getId());
			buffer.putInt(msg.getGroup().getId());
			buffer.put(data);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		
		buffer.flip();
		byte[] ret = new byte[buffer.limit()];
		buffer.get(ret);
		return ret;
	}

	@Override
	public void fixNullField(GroupMessage data) {
		//TODO
		if(data.getGroup()==null){
			ChatGroup g=new ChatGroup();
			data.setGroup(g);
		}
		if(data.getSrcUser()==null){
			Account u=new Account(); 
			data.setSrcUser(u);
		}
		if(data.getMessage()==null){
			MsgData msg=new MsgData();
			data.setMessage(msg);
		}
	}

}
