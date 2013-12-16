package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.Charset;

import edu.buaa.scse.niu.wechat.engine.entity.msg.RespondMessage;

public class RespondMsgDecoder extends JsonDecoder<RespondMessage>{

	public RespondMsgDecoder(){
		super();
	}
	
	public RespondMsgDecoder(Charset charset){
		super(charset);
	}
	
	@Override
	public RespondMessage decode(byte[] data) {
		return null;
	}

}
