package edu.buaa.scse.niu.wechat.engine.mina.filter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import edu.buaa.scse.niu.wechat.common.utils.ByteUtils;
import edu.buaa.scse.niu.wechat.engine.protocol.Protocol;

public class ChatProtocolDecoder extends CumulativeProtocolDecoder  {

	private static final String DECODER_STATE_KEY=ChatProtocolDecoder.class.getSimpleName()+".STATE";
	private static class DecoderState{
		Protocol protocol;
		boolean foundStart=false;
	}
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		DecoderState decoderState = (DecoderState) session.getAttribute(DECODER_STATE_KEY);
		if (decoderState == null) {
            decoderState = new DecoderState();
            session.setAttribute(DECODER_STATE_KEY, decoderState);
        }
		if(decoderState.protocol==null){
			//首先找到协议开始标志
			if(!decoderState.foundStart){
				while(in.remaining()>0){
					if(in.get()==Protocol.PACKSIGN){
						decoderState.foundStart=true;
						break;
					}
				}
			}
			//协议头部传输完成
			if(in.remaining()>=Protocol.MIN_PACKAGE_LEAGTH-1){
				Protocol p=new Protocol();
				//包起始标记--数据包类型--时间--扩展字节--数据部分长度--数据--结束标记
				p.setStartSign(Protocol.PACKSIGN);
				p.setCmdType(in.get());
				byte[] date=new byte[7];
				in.get(date, 0, 7);
				p.setTime(ByteUtils.bytesToDate(date));
				p.setRandom(in.get());
				decoderState.protocol=p;
			}else{
				//头部数据不足
				return false;
			}
		}
		if(decoderState.protocol!=null){
			//等待传输消息内容体
			//TODO 限制长度
			if(in.prefixedDataAvailable(4,1024)){
				//消息体数据全部到达
				int length = in.getInt();
				decoderState.protocol.setLength(length);
				if(length>0){
					byte[] data = new byte[length];
			        in.get(data);
					decoderState.protocol.setData(data);
				}
				//TODO 可能会读不到该字节
				decoderState.protocol.setEndSign(in.get());
				
				if(decoderState.protocol.isValid()){
					out.write(decoderState.protocol);
					decoderState.protocol=null;
					decoderState.foundStart=false;
					return true;
				}else{
					decoderState.protocol=null;
					decoderState.foundStart=false;
					return false;
				}
			}else{
				return false;
			}
		}
		return false;
	}
}
