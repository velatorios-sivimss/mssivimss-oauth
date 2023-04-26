package com.imss.sivimss.oauth.service;

import com.imss.sivimss.oauth.model.Login;

public interface CuentaService {

	public Login obtenerLoginPorIdUsuario(String idUsuario) throws Exception;
	
	public Login obtenerLoginPorCveUsuario(String cveUsuario) throws Exception;
	
	public Boolean actualizarContra(String idLogin, String idUsuario, String contrasenia) throws Exception;
	
	public void validarSiap(String cveUsuario) throws Exception;
	
	public void actNumIntentos(String idLogin, Integer numIntentos) throws Exception;
	
	public Integer validaNumIntentos(String idLogin, String fechaBloqueo, String numIntentos) throws Exception;
	
	public Integer obtenerMaxNumIntentos() throws Exception;
}
