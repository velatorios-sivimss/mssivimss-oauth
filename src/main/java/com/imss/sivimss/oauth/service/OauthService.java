package com.imss.sivimss.oauth.service;

import java.io.IOException;
import java.util.Map;

import com.imss.sivimss.oauth.util.Response;

public interface OauthService {

	Response<?> acceder(Map<String, Object> datos) throws IOException;
	
}
