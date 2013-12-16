package edu.buaa.scse.niu.wechat.engine.service.validator;

import java.util.Set;

import edu.buaa.scse.niu.wechat.engine.entity.request.RegisterRequest;
import edu.buaa.scse.niu.wechat.engine.entity.response.RegisterResponse.ErrorCode;

public interface RegisterValidator {

	public Set<ErrorCode> checkUnique(RegisterRequest request);
}
