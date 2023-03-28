package com.imss.sivimss.oauth.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.util.BdConstantes;
import com.imss.sivimss.oauth.util.ConstantsMensajes;
import com.imss.sivimss.oauth.util.EstatusVigenciaEnum;
import com.imss.sivimss.oauth.util.ParametrosUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imss.sivimss.oauth.beans.Usuario;
import com.imss.sivimss.oauth.exception.BadRequestException;
import com.imss.sivimss.oauth.model.Login;
import com.imss.sivimss.oauth.service.ContraseniaService;
import com.imss.sivimss.oauth.service.CuentaService;
import com.imss.sivimss.oauth.service.OauthService;
import com.imss.sivimss.oauth.service.UsuarioService;
import com.imss.sivimss.oauth.util.Response;

@Service
public class OauthServiceImpl extends UtileriaService implements OauthService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CuentaService cuentaService;
	
	@Autowired
	private ContraseniaService contraseniaService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Response<?> acceder(String user, String contrasenia) throws Exception {
		
		List<Map<String, Object>> mapping;
		Usuario usuario= usuarioService.obtener(user);
		List<Map<String, Object>> datos;
		Response<Object> resp;
		ParametrosUtil parametrosUtil = new ParametrosUtil();
		Map<String, Object> respuesta = new HashMap<>();
		
		Login login = cuentaService.obtenerLoginPorIdUsuario( usuario.getIdUsuario() );
		
		Integer intentos = cuentaService.validaNumIntentos(login.getIdLogin(), login.getFecBloqueo(), login.getNumIntentos());
		
		if ( !contrasenia.equals(usuario.getPassword()) ) {
			intentos++;
			cuentaService.actNumIntentos(login.getIdLogin(), intentos);
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Usuario o Contraseña incorrecta");
		}else {
			cuentaService.actNumIntentos(login.getIdLogin(), 0);
		}
		
		
		if( login.getEstatusCuenta().equalsIgnoreCase( BdConstantes.ESTATUS_PRE_ACTIVO ) ) {
			
			respuesta.put("preActivo", true);
			respuesta.put("contrasenia", contrasenia);
			
			resp =  new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
					respuesta );
			
			return resp;
			
		}else if ( login.getEstatusCuenta().equalsIgnoreCase( BdConstantes.ESTATUS_DESACTIVADO ) ) {
			
			respuesta.put("desActivado", true);
			
			resp =  new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
					respuesta );
			
			return resp;
			
		}
		
		//Validacion del SIAP
		cuentaService.validarSiap( user );
		
		//Validar Fecha de la Contrasenia
		Integer estatusContra = contraseniaService.validarFecha( login.getFecCamContra() );
		
		if( estatusContra.equals( EstatusVigenciaEnum.VENCIDA.getId() ) ) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Contraseña Vencida");
		}else if ( estatusContra.equals( EstatusVigenciaEnum.PROXIMA_VENCER.getId() ) ) {
			respuesta.put("Contrasenia_Proxima_Vencer", true);
		}else {
			respuesta.put("Contrasenia_Proxima_Vencer", false);
		}
		
		
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("nombre", usuario.getNombre() + " " + usuario.getPaterno() + " " + usuario.getMaterno());
		mapa.put("curp", usuario.getCurp());
		mapa.put("idRol", usuario.getIdRol());
		mapa.put("desRol", usuario.getDesRol());
		mapa.put("idOficina", usuario.getIdOficina());
		mapa.put("idDelegacion", usuario.getIdDelegacion());
		mapa.put("idVelatorio", usuario.getIdVelatorio());
		mapa.put("cveMatricula", usuario.getClaveMatricula());
		mapa.put("cveUsuario", usuario.getClaveUsuario());
		mapa.put("idUsuario", usuario.getIdUsuario());
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(mapa);
		
		datos = consultaGenericaPorQuery( parametrosUtil.tiempoToken() );
		mapping = Arrays.asList(modelMapper.map(datos, HashMap[].class));
		
		String tiempoString = mapping.get(0).get("TIP_PARAMETRO").toString();
		
		Long tiempo = (long) Integer.parseInt(tiempoString);
		
		String token = jwtProvider.createToken(json, tiempo);
		respuesta.put("token ", token);
		
		resp =  new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
				respuesta );
		
		return resp;
		
	}

}
