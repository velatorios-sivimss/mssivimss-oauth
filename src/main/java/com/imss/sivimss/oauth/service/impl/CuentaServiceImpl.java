package com.imss.sivimss.oauth.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.exception.BadRequestException;
import com.imss.sivimss.oauth.model.Login;
import com.imss.sivimss.oauth.service.CuentaService;
import com.imss.sivimss.oauth.util.LoginUtil;

@Service
public class CuentaServiceImpl extends UtileriaService implements CuentaService {

	private static final Logger log = LoggerFactory.getLogger(CuentaServiceImpl.class);
	
	@Override
	public Login obtenerLoginPorIdUsuario(String idUsuario) throws Exception {
		
		List<Map<String, Object>> datos;
		LoginUtil loginUtil = new LoginUtil();
		List<Login> lista;
		datos = consultaGenericaPorQuery( loginUtil.buscarPorIdUsuario(idUsuario) );
		Map<String, Object> dato;
		Login login;
		
		if( datos == null || datos.isEmpty() ) {
			log.info("No existen datos en BD, entonces se debe crear el  registro");
			dato = insertarDetalle( loginUtil.insertar(idUsuario) , "SVT_LOGIN", "ID_LOGIN");
			login = modelMapper.map(dato, Login.class);
		}else {
			lista = Arrays.asList(modelMapper.map(datos, Login[].class));
			login = lista.get(0);
		}
		
		return login;
	
	}

	@Override
	public Login obtenerLoginPorCveUsuario(String cveUsuario) throws Exception {
		List<Map<String, Object>> datos;
		LoginUtil loginUtil = new LoginUtil();
		List<Login> lista;
		datos = consultaGenericaPorQuery( loginUtil.buscarPorCveUsuario(cveUsuario) );
		Login login;
		
		if( datos == null || datos.isEmpty() ) {
			log.info("No existen datos en BD");
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Usuario no Existe");
		}else {
			lista = Arrays.asList(modelMapper.map(datos, Login[].class));
			login = lista.get(0);
		}
		
		
		return login;
	}

	@Override
	public Boolean actualizarContra(String idLogin, String idUsuario, String contrasenia) throws Exception {
		
		Boolean exito = false;
		LoginUtil loginUtil = new LoginUtil();
		
		exito = actualizarMultiple( loginUtil.actContrasenia(idLogin, idUsuario, contrasenia) );
		
		return exito;
	}

}
