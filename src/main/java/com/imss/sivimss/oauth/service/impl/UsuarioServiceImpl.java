package com.imss.sivimss.oauth.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.beans.Usuario;
import com.imss.sivimss.oauth.exception.BadRequestException;
import com.imss.sivimss.oauth.service.UsuarioService;
import com.imss.sivimss.oauth.util.Response;

@Service
public class UsuarioServiceImpl extends UtileriaService implements UsuarioService {
	
	@SuppressWarnings("unchecked")
	@Override
	public Usuario obtener(String user) throws IOException {
		
		Usuario usuario= new Usuario();
		List<Map<String, Object>> mapping;
		Response<Object> resp = providerRestTemplate.consumirServicio(usuario.buscarUsuario(user), urlDominioConsulta + consulta);
		
		if (resp.getCodigo() == 200) {
			mapping = Arrays.asList(modelMapper.map(resp.getDatos(), HashMap[].class));
		}else {
			throw new BadRequestException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error al buscar al Usuario");
		}
		
		if( (mapping == null) || (mapping.isEmpty()) ) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Usuario no existe");
		}else {
			usuario= new Usuario( mapping.get(0) );
		}
		return usuario;
	}

}
