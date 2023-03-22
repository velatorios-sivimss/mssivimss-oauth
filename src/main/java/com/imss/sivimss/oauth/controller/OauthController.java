package com.imss.sivimss.oauth.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imss.sivimss.oauth.service.MenuService;
import com.imss.sivimss.oauth.service.OauthService;
import com.imss.sivimss.oauth.util.AppConstantes;
import com.imss.sivimss.oauth.util.Response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/")
public class OauthController {

	@Autowired
	private OauthService oauthService;
	
	@Autowired
	private MenuService menuService;
	
	@PostMapping("acceder")
	public Response<?> acceder(@RequestBody Map<String, Object> datos) throws IOException {
		
		String user = datos.get(AppConstantes.USUARIO).toString() ;
		String contrasenia = datos.get(AppConstantes.CONTRASENIA).toString() ;
		
		return oauthService.acceder(user, contrasenia);
      
	}
	
	@PostMapping("menu")
	public Response<?> menu(@RequestBody Map<String, Object> datos) throws IOException {
	
		String user = datos.get(AppConstantes.USUARIO).toString() ;
		String contrasenia = datos.get(AppConstantes.CONTRASENIA).toString() ;
		
		return menuService.obtener(user, contrasenia);
      
	}
	
	@PostMapping("mensajes")
	public Response<?> mensajes(@RequestBody Map<String, Object> datos) throws IOException {
	
		String user = datos.get(AppConstantes.USUARIO).toString() ;
		String contrasenia = datos.get(AppConstantes.CONTRASENIA).toString() ;
		
		return menuService.mensajes(user, contrasenia);
      
	}
	
}
