package com.imss.sivimss.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imss.sivimss.oauth.service.NivelOficinaService;
import com.imss.sivimss.oauth.util.ConstantsMensajes;
import com.imss.sivimss.oauth.util.Response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/nivelOficina")
public class NivelOficinaController {

	@Autowired
	private NivelOficinaService nivelOficinaService;
	
	@PostMapping("/consulta")
	public Response<Object> consultaListaGenerica() throws Exception {
		
		return new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
				nivelOficinaService.consulta() );

	}

}
