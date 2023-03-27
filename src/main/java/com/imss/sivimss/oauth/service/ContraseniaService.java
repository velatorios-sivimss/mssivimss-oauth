package com.imss.sivimss.oauth.service;

import com.imss.sivimss.oauth.util.Response;

public interface ContraseniaService {

	Response<?> cambiar(String user, String contraAnterior, String contraNueva) throws Exception;
	
}
