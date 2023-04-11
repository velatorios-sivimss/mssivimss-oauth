package com.imss.sivimss.oauth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imss.sivimss.oauth.service.VelatorioService;
import com.imss.sivimss.oauth.util.AppConstantes;
import com.imss.sivimss.oauth.util.ConstantsMensajes;
import com.imss.sivimss.oauth.util.Response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/velatorio")
public class VelatorioController {

	@Autowired
	private VelatorioService velatorioService;
	
	@PostMapping("/consulta")
	public Response<Object> consultaListaGenerica( @RequestBody Map<String, Object> datos ) throws Exception {
		
		String idDelegacion = datos.get(AppConstantes.ID_DELEGACION).toString();
		
		return new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
				velatorioService.consulta( idDelegacion ) );

	}

}
