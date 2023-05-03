package com.imss.sivimss.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imss.sivimss.oauth.service.CatalogosService;
import com.imss.sivimss.oauth.util.ConstantsMensajes;
import com.imss.sivimss.oauth.util.LogUtil;
import com.imss.sivimss.oauth.util.Response;
import java.util.logging.Level;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/catalogos")
public class CatalogosController {

	@Autowired
	private LogUtil logUtil;
	
	@Autowired
	private CatalogosService catalogosService;
	
	@PostMapping("/consulta")
	public Response<Object> consultaListaGenerica() throws Exception {
		
		return new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
				catalogosService.consulta() );

	}

}
