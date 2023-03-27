package com.imss.sivimss.oauth.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.beans.Usuario;
import com.imss.sivimss.oauth.exception.BadRequestException;
import com.imss.sivimss.oauth.service.UsuarioService;

@Service
public class UsuarioServiceImpl extends UtileriaService implements UsuarioService {
	
	@SuppressWarnings("unchecked")
	@Override
	public Usuario obtener(String user) throws Exception {
		
		Usuario usuario= new Usuario();
		List<Map<String, Object>> mapping;
		List<Map<String, Object>> datos = consultaGenericaPorQuery( usuario.buscarUsuario(user) );
		
		mapping = Arrays.asList(modelMapper.map(datos, HashMap[].class));
		
		if( (mapping == null) || (mapping.isEmpty()) ) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Usuario no existe");
		}else {
			usuario= new Usuario( mapping.get(0) );
		}
		return usuario;
	}

}
