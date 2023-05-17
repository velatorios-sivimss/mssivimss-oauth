package com.imss.sivimss.oauth.service;

import com.imss.sivimss.oauth.util.Response;

public interface MenuService {

	Response<Object> obtener(String idRol) throws Exception;
	
	Response<Object> mensajes() throws Exception;
	
	Response<Object> permisos(String idRol) throws Exception;
	
}
