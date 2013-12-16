package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.request.LoginRequest;

public class LoginRequestEncoder extends JsonEncoder<LoginRequest> {

	public LoginRequestEncoder() {
		super();
	}

	public LoginRequestEncoder(Charset charset) {
		super(charset);
	}

	@Override
	public byte[] encode(LoginRequest data) {
		fixNullField(data);
		
		IoBuffer buffer = IoBuffer.allocate(100, false);
		buffer.setAutoExpand(true);

		try {
			buffer.putPrefixedString(data.getUserName(), 1, encoder);
			buffer.putPrefixedString(data.getPassword(), 1, encoder);
			buffer.putPrefixedString(data.getVerification(), 1, encoder);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		buffer.flip();
		byte[] ret = new byte[buffer.limit()];
		buffer.get(ret);
		return ret;
	}

	@Override
	public void fixNullField(LoginRequest data) {
		if(data.getPassword()==null){
			data.setPassword("");
		}
		if(data.getUserName()==null){
			data.setUserName("");
		}
		if(data.getVerification()==null){
			data.setVerification("");
		}
	}


}
