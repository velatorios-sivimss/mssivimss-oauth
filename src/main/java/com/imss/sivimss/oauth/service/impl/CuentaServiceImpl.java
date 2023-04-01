package com.imss.sivimss.oauth.service.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.imss.sivimss.oauth.util.ParametrosUtil;

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
			login.setFecCamContra( datos.get(0).get("FEC_CAMBIO_CONTRASENIA").toString() );
			
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
			
			if( datos.get(0).get("FEC_CAMBIO_CONTRASENIA") != null ) {
				login.setFecCamContra( datos.get(0).get("FEC_CAMBIO_CONTRASENIA").toString() );
			}
			
			if( datos.get(0).get("CVE_CODIGO_SEGURIDAD") != null ) {
				login.setCodSeguridad( datos.get(0).get("CVE_CODIGO_SEGURIDAD").toString() );
			}
			
			if( datos.get(0).get("FEC_CODIGO_SEGURIDAD") != null ) {
				login.setFecCodSeguridad( datos.get(0).get("FEC_CODIGO_SEGURIDAD").toString() );
			}
			
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

	@SuppressWarnings("unchecked")
	@Override
	public void validarSiap(String cveUsuario) throws Exception {
		List<Map<String, Object>> datos;
		ParametrosUtil parametrosUtil = new ParametrosUtil();
		List<Map<String, Object>> mapping;
		String estatusSiap = "";
		
		datos = consultaGenericaPorQuery( parametrosUtil.tiempoToken() );
		mapping = Arrays.asList(modelMapper.map(datos, HashMap[].class));
		
		String siap = mapping.get(0).get("TIP_PARAMETRO").toString();
		
		if( siap.equalsIgnoreCase("true") ) {
			log.info( "Se debe consultar el SIAP" );
			estatusSiap = "Activo";
		}
		
		if( siap.equalsIgnoreCase("true") && !estatusSiap.equalsIgnoreCase("Activo") ) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Usuario " + cveUsuario +" no Existe en SIAP");
		}
		
	}

	@Override
	public void actNumIntentos(String idLogin, Integer numIntentos) throws Exception {
		LoginUtil loginUtil = new LoginUtil();
		Integer maxNumIntentos = obtenerMaxNumIntentos();
		
		actualizaGenericoPorQuery( loginUtil.actNumIntentos(idLogin, numIntentos, maxNumIntentos) );
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer validaNumIntentos(String idLogin, String fechaBloqueo, String numIntentos) throws Exception {
		List<Map<String, Object>> datos;
		ParametrosUtil parametrosUtil = new ParametrosUtil();
		List<Map<String, Object>> mapping;
		Integer tiempoBloqueo;
		LoginUtil loginUtil = new LoginUtil();
		Integer intentos = Integer.parseInt(numIntentos);
		
		datos = consultaGenericaPorQuery( parametrosUtil.tiempoBloqueo() );
		mapping = Arrays.asList(modelMapper.map(datos, HashMap[].class));
		tiempoBloqueo =  Integer.parseInt(mapping.get(0).get("TIP_PARAMETRO").toString());
		
		if( fechaBloqueo!=null && !fechaBloqueo.isEmpty() ) {
			
			SimpleDateFormat formatter;
			Calendar calendar = Calendar.getInstance();
			formatter = new SimpleDateFormat(PATTERN);
			
			Date bloqueo = formatter.parse(fechaBloqueo);
			calendar.setTime(bloqueo);
			calendar.add(Calendar.MINUTE , tiempoBloqueo);
			bloqueo = calendar.getTime();
			
			datos = consultaGenericaPorQuery( parametrosUtil.obtenerFecha(formatoSQL) );
			mapping = Arrays.asList(modelMapper.map(datos, HashMap[].class));
			String tiempoSQL = mapping.get(0).get("tiempo").toString();
			formatter = new SimpleDateFormat(patronSQL);
			
			Date actual =  formatter.parse(tiempoSQL);
			
			if( actual.after(bloqueo) ) {
				//resetear numBloqueo
				intentos = 0;
				actualizaGenericoPorQuery( loginUtil.actNumIntentos(idLogin, 0, 1) );
			}else {
				throw new BadRequestException(HttpStatus.BAD_REQUEST, "Favor de Esperar " + tiempoBloqueo +" minutos");
			}
			
		}
		
		return intentos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer obtenerMaxNumIntentos()  throws Exception{
		Integer maxNumIntentos;
		List<Map<String, Object>> datos;
		ParametrosUtil parametrosUtil = new ParametrosUtil();
		List<Map<String, Object>> mapping;
		
		datos = consultaGenericaPorQuery( parametrosUtil.numIntentos() );
		mapping = Arrays.asList(modelMapper.map(datos, HashMap[].class));
		
		maxNumIntentos = Integer.parseInt(mapping.get(0).get("TIP_PARAMETRO").toString());
		
		return maxNumIntentos;
	}

}
