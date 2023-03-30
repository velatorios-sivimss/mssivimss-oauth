package com.imss.sivimss.oauth.service;

import com.imss.sivimss.oauth.util.Response;

public interface ContraseniaService {

	public Response<?> cambiar(String user, String contraAnterior, String contraNueva) throws Exception;
	
	public Integer validarFecha(String fecha) throws Exception;
	
	public Response<?> generarCodigo(String user) throws Exception;
	
}
