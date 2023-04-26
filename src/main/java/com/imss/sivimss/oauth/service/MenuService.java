package com.imss.sivimss.oauth.service;

import com.imss.sivimss.oauth.util.Response;

public interface MenuService {

	Response<?> obtener(String idRol) throws Exception;
	
	Response<?> mensajes() throws Exception;
	
	Response<?> permisos(String idRol) throws Exception;
	
}
