package edu.buaa.scse.niu.wechat.engine.mina.filter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import edu.buaa.scse.niu.wechat.common.utils.ByteUtils;
import edu.buaa.scse.niu.wechat.engine.protocol.Protocol;

public class ChatProtocolEncoder extends ProtocolEncoderAdapter {

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		Protocol p = (Protocol) message;
		IoBuffer buffer = IoBuffer.allocate(100, false);
		buffer.setAutoExpand(true);
		
		//包起始标记--数据包类型--时间--扩展字节--数据部分长度--数据--结束标记
		buffer.put(Protocol.PACKSIGN);
		buffer.put(p.getCmdType());
		byte[] time = ByteUtils.dateToBytes(p.getTime());
		buffer.put(time);
		buffer.put(p.getRandom());

		//填入协议body部分 
		//首先输入body部分长度，接下来输入数据
		buffer.putInt(p.getLength());
		byte[] data = p.getData();
		if(data!=null){
			buffer.put(data);
		}
		

		buffer.put(Protocol.PACKSIGN);

		buffer.flip();
		session.write(buffer);
	}

}
