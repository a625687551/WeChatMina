package edu.buaa.scse.niu.wechat.engine.mina.codec;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;

public class RegisterRequestEncoder extends JsonEncoder<RegisterRequest> {

	public RegisterRequestEncoder() {
		super();
	}

	public RegisterRequestEncoder(Charset charset) {
		super(charset);
	}

	/**
	 * name--password--phone--email
	 */
	@Override
	public byte[] encode(RegisterRequest data) {
		fixNullField(data);
		IoBuffer buffer = IoBuffer.allocate(100, false);
		buffer.setAutoExpand(true);

		try {
			buffer.putPrefixedString(data.getName(), 1, encoder);
			buffer.putPrefixedString(data.getPassword(), 1, encoder);
			buffer.putPrefixedString(data.getPhone(), 1, encoder);
			buffer.putPrefixedString(data.getEmail(), 1, encoder);
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		buffer.flip();
		byte[] ret = new byte[buffer.limit()];
		buffer.get(ret);
		return ret;
	}

	@Override
	public void fixNullField(RegisterRequest data) {
		if(data.getName()==null){
			data.setName("");
		}
		if(data.getPhone()==null){
			data.setPhone("");
		}
		if(data.getPassword()==null){
			data.setPassword("");
		}
		if(data.getEmail()==null){
			data.setEmail("");
		}
	}

//	public static void main(String[] args) {
//		RegisterRequest req = new RegisterRequest("niu", "123", "niu@126.com",
//				"12580");
//		RegisterRequestEncoder encoder = new RegisterRequestEncoder();
//		byte[] bts = encoder.encode(req);
//		
//		RegisterRequestDecoder decoder=new RegisterRequestDecoder();
//		RegisterRequest res=decoder.decode(bts);
//		System.out.println(res);
//	}

}
