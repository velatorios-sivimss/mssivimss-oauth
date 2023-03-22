package com.imss.sivimss.oauth.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.beans.Usuario;
import com.imss.sivimss.oauth.exception.BadRequestException;
import com.imss.sivimss.oauth.model.response.MenuResponse;
import com.imss.sivimss.oauth.service.MenuService;
import com.imss.sivimss.oauth.service.UsuarioService;
import com.imss.sivimss.oauth.util.MenuUtil;
import com.imss.sivimss.oauth.util.Response;

@Service
public class MenuServiceImpl extends UtileriaService implements MenuService {

	@Autowired
	private UsuarioService usuarioService;
	
	private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Override
	public Response<?> obtener(String user, String contrasenia) throws IOException {
		
		Usuario usuario= usuarioService.obtener(user);
		Response<Object> resp = new Response<>();
		
		if ( !contrasenia.equals(usuario.getPassword()) ) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Usuario o Contraseña incorrecta");
		}
		
		List<List<MenuResponse>> menuBD = new ArrayList<>();
		MenuUtil menuUtil = new MenuUtil();
		
		for(int i=0; i<4; i++) {
			resp = providerRestTemplate.consumirServicio(menuUtil.buscar(usuario.getIdRol(), i), urlDominioConsulta + consulta);
			menuBD.add( Arrays.asList(modelMapper.map(resp.getDatos(), MenuResponse[].class)) );
		}
		
		log.info("Niveles = " + menuBD.size());
		
		for( int i=0; i< ( menuBD.size() - 1 ); i++ ) {
			menuUtil.organizar(menuBD.get(i), menuBD.get(i+1));
		}
		
		resp.setDatos( menuBD.get(0) );
		
		return resp;
	}

}
