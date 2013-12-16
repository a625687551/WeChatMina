package edu.buaa.scse.niu.wechat.engine.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse.ErrorCode;
import edu.buaa.scse.niu.wechat.engine.repository.AccountDao;
import edu.buaa.scse.niu.wechat.engine.service.validator.RegisterValidator;

@Service
public class RegisterValidatorImpl implements RegisterValidator{

	public enum Field {
		Name, Phone, Email
	};

	@Autowired
	private AccountDao dao;
	
	@Override
	public Set<ErrorCode> checkUnique(RegisterRequest request) {
		Set<ErrorCode> errors=new HashSet<ErrorCode>();
		String name=request.getName();
		String phone=request.getPhone();
		String email=request.getEmail();
		
		if(name==null || name.equals("")){
			errors.add(ErrorCode.Null_name);
		}else if(dao.findByName(name)!=null){
			errors.add(ErrorCode.Duplicate_name);
		}
		if(email!=null && !email.equals("")){
			if(dao.findByEmail(email)!=null){
				errors.add(ErrorCode.Duplicate_email);
			}
		}
		if(phone!=null && !phone.equals("")){
			if(dao.findByPhone(phone)!=null){
				errors.add(ErrorCode.Duplicate_phone);
			}
		}
		return errors;
	}
}
