package com.imss.sivimss.oauth.service;

import com.imss.sivimss.oauth.model.Login;

public interface CuentaService {

	public Login obtenerLoginPorIdUsuario(String idUsuario) throws Exception;
	
	public Login obtenerLoginPorCveUsuario(String cveUsuario) throws Exception;
	
	public Boolean actualizarContra(String idLogin, String idUsuario, String contrasenia) throws Exception;
	
}
