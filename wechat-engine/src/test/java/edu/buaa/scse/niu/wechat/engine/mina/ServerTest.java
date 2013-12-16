package edu.buaa.scse.niu.wechat.engine.mina;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx=new ClassPathXmlApplicationContext("server_test_context.xml",ServerTest.class);
	}

}
