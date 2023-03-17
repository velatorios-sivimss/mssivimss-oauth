package com.imss.sivimss.oauth.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.util.AppConstantes;
import com.imss.sivimss.oauth.util.PermisosUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imss.sivimss.oauth.beans.Usuario;
import com.imss.sivimss.oauth.exception.BadRequestException;
import com.imss.sivimss.oauth.model.Funcionalidad;
import com.imss.sivimss.oauth.model.Permisos;
import com.imss.sivimss.oauth.service.OauthService;
import com.imss.sivimss.oauth.util.ProviderServiceRestTemplate;
import com.imss.sivimss.oauth.util.Response;
import com.imss.sivimss.oauth.security.JwtProvider;

@Service
public class OauthServiceImpl implements OauthService {

	@Value("${endpoints.dominio-consulta}")
	private String urlDominioConsulta;
	
	@Autowired
	private ProviderServiceRestTemplate providerRestTemplate;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	//private static final Logger log = LoggerFactory.getLogger(OauthServiceImpl.class);
	
	private static final String CONSULTA = "/generico/consulta";  
	
	@SuppressWarnings("unchecked")
	@Override
	public Response<?> acceder(Map<String, Object> datos) throws IOException {
		
		String user = datos.get(AppConstantes.USUARIO).toString() ;
		String contrasenia = datos.get(AppConstantes.CONTRASENIA).toString() ;
		Usuario usuario= new Usuario();
		List<Map<String, Object>> mapping;
		
		Response<Object> resp = providerRestTemplate.consumirServicio(usuario.buscarUsuario(user), urlDominioConsulta + CONSULTA);
		
		if (resp.getCodigo() == 200) {
			mapping = Arrays.asList(modelMapper.map(resp.getDatos(), HashMap[].class));
		}else {
			return resp;
		}
		
		if( (mapping == null) || (mapping.isEmpty()) ) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Usuario no existe");
		}else {
			usuario= new Usuario( mapping.get(0) );
		}
		
		
		PermisosUtil permisosUtil = new PermisosUtil();
		resp = providerRestTemplate.consumirServicio(permisosUtil.buscarFuncionalidad(usuario.getIdRol()), urlDominioConsulta + CONSULTA);
		
		if (resp.getCodigo() == 200) {
			mapping = Arrays.asList(modelMapper.map(resp.getDatos(), HashMap[].class));
		}else {
			return resp;
		}
		
		List<Funcionalidad> funcionalidad = new ArrayList<>();
		
		for( Map<String, Object> objeto : mapping ) {
			Funcionalidad fun = new Funcionalidad();
			
			fun.setIdFuncionalidad( objeto.get("ID_FUNCIONALIDAD").toString() );
			resp = providerRestTemplate.consumirServicio(permisosUtil.buscarPermisos(usuario.getIdRol(), fun.getIdFuncionalidad()), urlDominioConsulta + CONSULTA);
			List<Map<String, Object>> permisosObjeto = Arrays.asList(modelMapper.map(resp.getDatos(), HashMap[].class));
			
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
		
		Map<String, Object> mapa = new HashMap<>();
		mapa.put("nombre", usuario.getNombre() + " " + usuario.getPaterno() + " " + usuario.getMaterno());
		mapa.put("curp", usuario.getCurp());
		mapa.put("idRol", usuario.getIdRol());
		mapa.put("desRol", usuario.getDesRol());
		mapa.put("idOficina", usuario.getIdOficina());
		mapa.put("idDelegacion", usuario.getIdDelegacion());
		mapa.put("idVelatorio", usuario.getIdVelatorio());
		mapa.put("permisosUsuario", funcionalidad);
		mapa.put("cveMatricula", usuario.getClaveMatricula());
		mapa.put("cveUsuario", usuario.getClaveUsuario());
		mapa.put("idUsuario", usuario.getIdUsuario());
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(mapa);
		
		resp = providerRestTemplate.consumirServicio(permisosUtil.tiempoToken(), urlDominioConsulta + CONSULTA);
		
		if (resp.getCodigo() == 200) {
			mapping = Arrays.asList(modelMapper.map(resp.getDatos(), HashMap[].class));
		}else {
			return resp;
		}
		
		String tiempoString = mapping.get(0).get("TIP_PARAMETRO").toString();
		
		Long tiempo = (long) Integer.parseInt(tiempoString);
		
		if ( contrasenia.equals(usuario.getPassword()) ) {
			resp.setDatos( jwtProvider.createToken(json, tiempo) );
		}
		
		return resp;
		
	}

}
