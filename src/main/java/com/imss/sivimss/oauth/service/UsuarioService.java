package com.imss.sivimss.oauth.service;

import java.io.IOException;
import java.util.Map;

import com.imss.sivimss.oauth.beans.Usuario;
import com.imss.sivimss.oauth.util.Response;

public interface UsuarioService {

	Usuario obtener(String user) throws IOException;
	
}
