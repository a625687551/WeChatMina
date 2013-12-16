package edu.buaa.scse.niu.wechat.common.utils;

import org.codehaus.jackson.map.ObjectMapper;

public class JacksonMapper {
	private static ObjectMapper mapper;

	public static ObjectMapper getMapperInstance() {
		if (mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}
}
