package edu.buaa.scse.niu.wechat.engine.service;

import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.UserGroup;
import edu.buaa.scse.niu.wechat.engine.entity.UserRelationship;
import edu.buaa.scse.niu.wechat.engine.entity.request.LoginRequest;
import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;

public interface UserService {

	/**
	 * 通过用户Id查找已登录用户
	 * @param id 用户Id
	 * @return
	 */
	public Account getAccountById(int id);

	
	/**
	 * 用户登录
	 * @param request 请求对象 包含用户名、密码、验证码
	 * @return 成功则返回用户对象，失败返回错误信息
	 */
	public LoginResponse login(LoginRequest request);

	
	public boolean logoff(int id);

	/**
	 * 用户注册 注册成功后生成该用户的默认usergroup
	 * @param request
	 * @return 
	 */
	public RegisterResponse register(RegisterRequest request);
	
	/**
	 * 管理员功能 删除用户 需要级联删除 用户-聊天组表中的内容
	 * @param user
	 */
	public void deleteUser(Account user);
	
}
