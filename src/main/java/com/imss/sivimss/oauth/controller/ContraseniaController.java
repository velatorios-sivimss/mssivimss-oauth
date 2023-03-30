package com.imss.sivimss.oauth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imss.sivimss.oauth.service.ContraseniaService;
import com.imss.sivimss.oauth.util.AppConstantes;
import com.imss.sivimss.oauth.util.Response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/contrasenia")
public class ContraseniaController {
	
	@Autowired
	private ContraseniaService contraseniaService;
	
	@PostMapping("/cambiar")
	public Response<?> acceder(@RequestBody Map<String, Object> datos) throws Exception {
		
		String user = datos.get(AppConstantes.USUARIO).toString() ;
		String contraseniaAnterior = datos.get(AppConstantes.CONTRASENIA_ANTERIOR).toString() ;
		String contraseniaNueva = datos.get(AppConstantes.CONTRASENIA_NUEVA).toString() ;
		
		return contraseniaService.cambiar(user, contraseniaAnterior, contraseniaNueva);
      
	}
	
	@PostMapping("/generar-codigo")
	public Response<?> generarCodigo(@RequestBody Map<String, Object> datos) throws Exception {
		
		String user = datos.get(AppConstantes.USUARIO).toString() ;
		
		return contraseniaService.generarCodigo( user );
      
	}
	
	@PostMapping("/validar-codigo")
	public Response<?> validarCodigo(@RequestBody Map<String, Object> datos) throws Exception {
		
		String user = datos.get(AppConstantes.USUARIO).toString() ;
		String codigo = datos.get(AppConstantes.CODIGO).toString() ;
		
		return contraseniaService.generarCodigo( user );
      
	}
	
}
