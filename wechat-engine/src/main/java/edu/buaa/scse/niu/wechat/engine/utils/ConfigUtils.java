package edu.buaa.scse.niu.wechat.engine.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Properties;

import edu.buaa.scse.niu.wechat.engine.global.Config;

public class ConfigUtils {

	public static Properties loadConfig(String path) {
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(path));
			Properties p = new Properties();
			p.load(in);
			return p;
		} catch (FileNotFoundException e) {
			System.out.println("config文件不存在:" + path);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("config文件读取失败:" + path);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param p
	 */
	public static void readConfig(Properties p) {
		
		try{
			String ip = p.getProperty("ENGINE_IP");
			int port = Integer.parseInt(p.getProperty("ENGINE_PORT"));
			int size = Integer.parseInt(p.getProperty("ENGINE_POOL_SIZE"));
			int init= Integer.parseInt(p.getProperty("ENGINE_POOL_INIT_SIZE"));
			int maxprotrocol=Integer.parseInt(p.getProperty("MAX_PROTOCOL_DATA_SIZE"));
			InetAddress addr=InetAddress.getByName(ip);
			String path=p.getProperty("FILE_BASE_PATH");
			Config.ENGINE_IP=ip;
			Config.ENGINE_PORT=port;
			Config.THREAD_POOL_SIZE=size;
			Config.FILE_BASE_PATH=path;
			Config.MAX_PROTOCOL_DATA_SIZE=maxprotrocol;
			Config.THREAD_POOL_INIT_SIZE=init;
			String charset=p.getProperty("CHARSET");
			Config.CHARSET=Charset.forName(charset);
			Config.setLoaded(true);
		}catch(NumberFormatException e){
			System.out.println("config文件读取错误");
			e.printStackTrace();
		}catch (UnknownHostException e) {
			System.out.println("config文件读取错误");
			e.printStackTrace();
		}
		
	}
}
