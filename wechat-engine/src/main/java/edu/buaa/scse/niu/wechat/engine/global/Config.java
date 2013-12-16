package edu.buaa.scse.niu.wechat.engine.global;

import java.nio.charset.Charset;

public class Config {

	private static boolean loaded=false;

	public static String ENGINE_IP;
	public static int ENGINE_PORT;
	public static int THREAD_POOL_SIZE;
	public static int THREAD_POOL_INIT_SIZE;
	
	public static int MAX_PROTOCOL_DATA_SIZE;
	
	public static Charset CHARSET;
	
	public static String FILE_BASE_PATH;

	public static boolean isLoaded() {
		return loaded;
	}

	public static void setLoaded(boolean loaded) {
		Config.loaded = loaded;
	}

}
