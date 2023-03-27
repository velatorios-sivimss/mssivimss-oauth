package com.imss.sivimss.oauth.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.util.BdConstantes;
import com.imss.sivimss.oauth.util.ConstantsMensajes;
import com.imss.sivimss.oauth.util.PermisosUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imss.sivimss.oauth.beans.Usuario;
import com.imss.sivimss.oauth.exception.BadRequestException;
import com.imss.sivimss.oauth.model.Funcionalidad;
import com.imss.sivimss.oauth.model.Login;
import com.imss.sivimss.oauth.model.Permisos;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public Response<?> acceder(String user, String contrasenia) throws Exception {
		
		List<Map<String, Object>> mapping;
		Usuario usuario= usuarioService.obtener(user);
		List<Map<String, Object>> datos;
		Response<Object> resp;
		PermisosUtil permisosUtil = new PermisosUtil();
		
		if ( !contrasenia.equals(usuario.getPassword()) ) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Usuario o Contraseña incorrecta");
		}
		
		Login login = cuentaService.obtenerLoginPorIdUsuario( usuario.getIdUsuario() );
		
		if( login.getEstatusCuenta().equalsIgnoreCase( BdConstantes.ESTATUS_PRE_ACTIVO ) ) {
			
			Map<String, Object> preActivo = new HashMap<>();
			preActivo.put("preActivo", true);
			preActivo.put("contrasenia", contrasenia);
			
			resp =  new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
					preActivo );
			
			return resp;
			
		}else if ( login.getEstatusCuenta().equalsIgnoreCase( BdConstantes.ESTATUS_DESACTIVADO ) ) {
			
			Map<String, Object> desActivado = new HashMap<>();
			desActivado.put("desActivado", true);
			
			resp =  new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
					desActivado );
			
			return resp;
			
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
		
		datos = consultaGenericaPorQuery( permisosUtil.tiempoToken() );
		mapping = Arrays.asList(modelMapper.map(datos, HashMap[].class));
		
		String tiempoString = mapping.get(0).get("TIP_PARAMETRO").toString();
		
		Long tiempo = (long) Integer.parseInt(tiempoString);
		
		resp =  new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
				jwtProvider.createToken(json, tiempo) );
		
		return resp;
		
	}

}
