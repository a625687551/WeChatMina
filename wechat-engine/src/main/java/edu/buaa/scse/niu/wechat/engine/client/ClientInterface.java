package edu.buaa.scse.niu.wechat.engine.client;

import edu.buaa.scse.niu.wechat.engine.entity.msg.GroupMessage;
import edu.buaa.scse.niu.wechat.engine.entity.msg.SingleMessage;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;


public interface ClientInterface {

	public void onConnected();
	public void onRegisterResult(RegisterResponse result);
	public void onLoginResult(LoginResponse result);
	public void onReceiveMessage(SingleMessage msg);
	public void onReceiveMessage(GroupMessage msg);
}
