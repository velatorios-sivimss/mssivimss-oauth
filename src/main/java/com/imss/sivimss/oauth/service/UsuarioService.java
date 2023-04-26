package com.imss.sivimss.oauth.service;

import com.imss.sivimss.oauth.beans.Usuario;

public interface UsuarioService {

	Usuario obtener(String user) throws Exception;
	
}
