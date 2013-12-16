package edu.buaa.scse.niu.wechat.engine.entity;

import java.nio.charset.Charset;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.mina.codec.SingleMessageDecoder;
import edu.buaa.scse.niu.wechat.engine.mina.codec.SingleMessageEncoder;


public class MessageCodecTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		ApplicationContext ctx=new ClassPathXmlApplicationContext("message-codec-beans.xml",MessageCodecTest.class);
		SingleMessage msg=ctx.getBean("singleMessage", SingleMessage.class);
		
		Charset charset=Charset.forName("UTF-8");
		SingleMessageEncoder encoder=new SingleMessageEncoder(charset);
		SingleMessageDecoder decoder=new SingleMessageDecoder(charset);
		
		byte[] bts=encoder.encode(msg);
		SingleMessage ret=decoder.decode(bts);
		
		System.out.println(msg.toString());
		System.out.println(ret.toString());
	}

}
