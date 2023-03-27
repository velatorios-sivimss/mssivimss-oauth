package com.imss.sivimss.oauth.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.util.ConstantsMensajes;
import com.imss.sivimss.oauth.model.Funcionalidad;
import com.imss.sivimss.oauth.model.Permisos;
import com.imss.sivimss.oauth.model.response.MenuResponse;
import com.imss.sivimss.oauth.service.MenuService;
import com.imss.sivimss.oauth.util.MenuUtil;
import com.imss.sivimss.oauth.util.PermisosUtil;
import com.imss.sivimss.oauth.util.Response;

@Service
public class MenuServiceImpl extends UtileriaService implements MenuService {
	
	private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Override
	@Cacheable("menu-obtener")
	public Response<?> obtener(String idRol) throws Exception {
		
		List<List<MenuResponse>> menuBD = new ArrayList<>();
		MenuUtil menuUtil = new MenuUtil();
		List<Map<String, Object>> datos;
		Response<Object> resp;
		
		for(int i=0; i<4; i++) {
			datos = consultaGenericaPorQuery( menuUtil.buscar(idRol, i) );
			menuBD.add( Arrays.asList(modelMapper.map(datos, MenuResponse[].class)) );
		}
		
		log.info("Niveles = " + menuBD.size());
		
		for( int i=0; i< ( menuBD.size() - 1 ); i++ ) {
			menuUtil.organizar(menuBD.get(i), menuBD.get(i+1));
		}
		
		resp = new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
				menuBD.get(0) );
		
		return resp;
	}

	@Override
	@Cacheable("menu-mensajes")
	public Response<?> mensajes() throws Exception {
		
		MenuUtil menuUtil = new MenuUtil();
		List<Map<String, Object>> datos;
		Response<Object> resp;
		
		datos = consultaGenericaPorQuery( menuUtil.obtenerMensajes() );
		
		resp = new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
				datos );
		
		return resp;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable("menu-permisos")
	public Response<?> permisos(String idRol) throws Exception {
		
		List<Map<String, Object>> datos;
		List<Map<String, Object>> mapping;
		PermisosUtil permisosUtil = new PermisosUtil();
		Map<String, Object> mapa = new HashMap<>();
		Response<Object> resp;
		datos = consultaGenericaPorQuery( permisosUtil.buscarFuncionalidad(idRol) );
		
		mapping = Arrays.asList(modelMapper.map(datos, HashMap[].class));
		
		List<Funcionalidad> funcionalidad = new ArrayList<>();
		
		for( Map<String, Object> objeto : mapping ) {
			Funcionalidad fun = new Funcionalidad();
			
			fun.setIdFuncionalidad( objeto.get("ID_FUNCIONALIDAD").toString() );
			datos = consultaGenericaPorQuery( permisosUtil.buscarPermisos(idRol, fun.getIdFuncionalidad()) );
			List<Map<String, Object>> permisosObjeto = Arrays.asList(modelMapper.map(datos, HashMap[].class));
			
			List<Permisos> permisos = new ArrayList<>();
			
			for( Map<String, Object> permisoObjeto : permisosObjeto ) {
				Permisos permiso = new Permisos();
				permiso.setIdPermiso( permisoObjeto.get("ID_PERMISO").toString() );
				permiso.setDescPermiso( permisoObjeto.get("DES_PERMISO").toString() );
				permisos.add(permiso);
			}
			
			fun.setPermisos(permisos);
			funcionalidad.add(fun);
		}
		
		mapa.put("permisosUsuario", funcionalidad);
		
		resp =  new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
				mapa );
		
		return resp;
	}

}
