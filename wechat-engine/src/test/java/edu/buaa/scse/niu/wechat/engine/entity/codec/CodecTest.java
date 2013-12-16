package edu.buaa.scse.niu.wechat.engine.entity.codec;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.request.LoginRequest;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse;
import edu.buaa.scse.niu.wechat.engine.mina.codec.LoginRequestDecoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.LoginRequestEncoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.LoginResponseDecoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.LoginResponseEncoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.SingleMessageDecoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.SingleMessageEncoder;

public class CodecTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CodecTest test = new CodecTest();
		test.runLoginTest();
		test.runSingleMsgTest();
	}

	private ApplicationContext context;

	public CodecTest() {
		context = new ClassPathXmlApplicationContext("codec-beans.xml",
				CodecTest.class);
	}

	public void runLoginTest(){
		
		LoginRequestEncoder req_encoder=context.getBean("loginRequestEncoder", LoginRequestEncoder.class);
		LoginRequestDecoder req_decoder=context.getBean("loginRequestDecoder", LoginRequestDecoder.class);
		
		LoginResponseEncoder res_encoder=context.getBean("loginResponseEncoder", LoginResponseEncoder.class);
		LoginResponseDecoder res_decoder=context.getBean("loginResponseDecoder", LoginResponseDecoder.class);
		
		LoginRequest request=context.getBean("loginReq", LoginRequest.class);
		byte[] bt_req=req_encoder.encode(request);
		LoginRequest ret_request=req_decoder.decode(bt_req);
		System.out.println(ret_request.toString());
		
		LoginResponse response=context.getBean("loginRes",LoginResponse.class);
		byte[] bt_res=res_encoder.encode(response);
		LoginResponse ret_response=res_decoder.decode(bt_res);
		System.out.println(ret_response.toString());
	}
	
	public void runSingleMsgTest(){
		SingleMessageEncoder encoder=context.getBean("singleMsgEncoder", SingleMessageEncoder.class);
		SingleMessageDecoder decoder=context.getBean("singleMsgDecoder", SingleMessageDecoder.class);
		
		SingleMessage msg=context.getBean("singleMessage",SingleMessage.class);
		
		byte[] bts=encoder.encode(msg);
		SingleMessage ret=decoder.decode(bts);
		
		System.out.println(msg.toString());
		System.out.println(ret.toString());
	}
}
