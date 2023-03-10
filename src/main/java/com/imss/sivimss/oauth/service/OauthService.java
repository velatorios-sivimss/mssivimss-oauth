package com.imss.sivimss.oauth.service;

import java.io.IOException;

import com.imss.sivimss.oauth.util.DatosRequest;
import com.imss.sivimss.oauth.util.Response;

public interface OauthService {

	Response<?> acceder(DatosRequest request) throws IOException;
	
}
