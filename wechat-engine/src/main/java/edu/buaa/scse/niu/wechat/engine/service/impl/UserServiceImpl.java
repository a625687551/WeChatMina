package edu.buaa.scse.niu.wechat.engine.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.buaa.scse.niu.wechat.engine.entity.ChatGroupAccount;
import edu.buaa.scse.niu.wechat.engine.entity.Profile;
import edu.buaa.scse.niu.wechat.engine.entity.Account;
import edu.buaa.scse.niu.wechat.engine.entity.request.LoginRequest;
import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse;
import edu.buaa.scse.niu.wechat.engine.entity.response.LoginResponse.ErrorCode;
import edu.buaa.scse.niu.wechat.engine.repository.ChatGroupDao;
import edu.buaa.scse.niu.wechat.engine.repository.ChatGroupAccountDao;
import edu.buaa.scse.niu.wechat.engine.repository.AccountDao;
import edu.buaa.scse.niu.wechat.engine.service.PasswordService;
import edu.buaa.scse.niu.wechat.engine.service.Services;
import edu.buaa.scse.niu.wechat.engine.service.UserService;
import edu.buaa.scse.niu.wechat.engine.service.validator.RegisterValidator;

@Service
public class UserServiceImpl implements UserService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(UserServiceImpl.class.getSimpleName());

	private final Map<Integer, Account> accountMap = new ConcurrentHashMap<>();
	
	private int userCount=0;

	@Autowired
	private Services services;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private ChatGroupDao chatGroupDao;
	@Autowired
	private ChatGroupAccountDao chatGroupAccountDao;

	public UserServiceImpl(){
		
	}
	
	public UserServiceImpl(AccountDao accountDao, ChatGroupDao chatGroupDao,
			ChatGroupAccountDao chatGroupAccountDao) {
		this.accountDao = accountDao;
		this.chatGroupDao = chatGroupDao;
		this.chatGroupAccountDao = chatGroupAccountDao;
	}



	@Transactional(readOnly=true)
	@Override
	public LoginResponse login(LoginRequest request) {
		LoginResponse res = null;
		String userName = request.getUserName();
		String pwd = request.getPassword();
		
//		Account user = accountDao.findByName(userName);
		
		Account user = accountDao.findByUnique(userName);
		
		ErrorCode error = null;
		if (user == null) {
			error = ErrorCode.No_such_user;
		} else {
			if (user.getPassword().equals(pwd)) {
				accountMap.put(user.getId(), user);
				userCount++;
				LOGGER.info("Login Successful:" + request.toString());
				LOGGER.info("Current login user : "+userCount);
				return new LoginResponse(user);
			} else {
				error = ErrorCode.Wrong_pwd;
			}
		}
		res = new LoginResponse(error);
		LOGGER.info("Login Failed:" + res.getError());
		return res;

	}

	@Override
	public boolean logoff(int id) {
		// TODO
		userCount--;
		LOGGER.info("Current login user : "+userCount);
		return false;
	}

	@Override
	public Account getAccountById(int id) {
		Account u = accountMap.get(id);
		return u;
	}

	@Override
	public RegisterResponse register(RegisterRequest request) {
		
		Set<RegisterResponse.ErrorCode> errors=checkRequest(request);
		if(errors!=null && errors.size()>0){
			return new RegisterResponse(errors);
		}else{
			PasswordService ps=services.getPasswordService();
			String salt=ps.getRandomSalt();
			String encrypted=ps.encrypt(request.getPassword(), salt);
			
			Account account=new Account(request.getName(),encrypted,salt);
			Profile profile=new Profile(request.getEmail(),request.getPhone());
			account.setProfile(profile);
			account=accountDao.save(account);
			
			RegisterResponse res=new RegisterResponse(account);
			return res;
		}
	}
	
	private Set<RegisterResponse.ErrorCode> checkRequest(RegisterRequest request){
		RegisterValidator validator=services.getRegisterValidator();
		return validator.checkUnique(request);
	}
	
	@Transactional
	@Override
	public void deleteUser(Account user) {
		accountDao.refresh(user);
		List<ChatGroupAccount> groups=user.getChatGroupAccounts();
		for(ChatGroupAccount g:groups){
			chatGroupAccountDao.delete(g);
		}
		
//		List<ChatGroup> groups=user.getChatGroups();
//		if(groups!=null){
//			for(ChatGroup g:groups){
//				g.getUsers().remove(user);
//				chatGroupDao.saveOrUpdate(g);
//			}
//		}
//		user.setChatGroups(null);
		accountDao.delete(user);
	}
	
}
