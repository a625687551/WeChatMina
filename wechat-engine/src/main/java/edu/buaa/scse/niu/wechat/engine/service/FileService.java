package edu.buaa.scse.niu.wechat.engine.service;

public interface FileService {

	public String getVoiceName(boolean single,int srcId,int destId);
	
	public String getImageName(boolean single,int srcId,int destId);
	
	public String getVoiceDir(boolean single,int srcId,int destId);
	
	public String getImageDir(boolean single,int srcId,int destId);
	
	public String getVoicePath(boolean single,int srcId,int destId);
	
	public String getImagePath(boolean single,int srcId,int destId);
	
	public String getBaseFileDir();
	
}
