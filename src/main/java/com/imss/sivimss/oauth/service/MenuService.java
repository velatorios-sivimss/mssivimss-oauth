package com.imss.sivimss.oauth.service;

import java.io.IOException;
import java.util.Map;

import com.imss.sivimss.oauth.util.Response;

public interface MenuService {

	Response<?> obtener(String user, String contrasenia) throws IOException;
	
}
