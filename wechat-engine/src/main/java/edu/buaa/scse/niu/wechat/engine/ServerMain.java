package edu.buaa.scse.niu.wechat.engine;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerMain {
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "test");
		ApplicationContext ctx=new ClassPathXmlApplicationContext("classpath:/spring/root-context.xml");
	}
}
