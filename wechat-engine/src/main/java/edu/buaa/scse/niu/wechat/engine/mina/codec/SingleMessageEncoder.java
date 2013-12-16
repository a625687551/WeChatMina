package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.msgdata.MsgData;

public class SingleMessageEncoder extends JsonEncoder<SingleMessage> {

	private MsgDataEncoder dataEncoder;
	public SingleMessageEncoder() {
		super();
		dataEncoder=new MsgDataEncoder();
	}

	public SingleMessageEncoder(Charset charset) {
		super(charset);
		dataEncoder=new MsgDataEncoder(charset);
	}

	@Override
	public byte[] encode(SingleMessage msg) {
		
		fixNullField(msg);
		
		byte[] data=dataEncoder.encode(msg.getMessage());
		int datalength=data.length;
		
		IoBuffer buffer = IoBuffer.allocate(SingleMessage.HEADER_LENGTH+datalength,
				false);
		buffer.setAutoExpand(true);

		// 消息Id--发送者Id--接收者Id--消息内容
		try {
			buffer.putString(msg.getId(), 36, encoder);
			buffer.putInt(msg.getSrcUser().getId());
			buffer.putInt(msg.getDestUser().getId());
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
	public void fixNullField(SingleMessage data) {
		//TODO 
		if(data.getMessage()==null){
			data.setMessage(new MsgData());
		}
		if(data.getSrcUser()==null){
			data.setSrcUser(new Account());
		}
		if(data.getDestUser()==null){
			data.setDestUser(new Account());
		}
	}

}
