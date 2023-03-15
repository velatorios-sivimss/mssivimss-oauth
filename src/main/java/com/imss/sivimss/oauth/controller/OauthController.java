package com.imss.sivimss.oauth.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imss.sivimss.oauth.service.OauthService;
import com.imss.sivimss.oauth.util.Response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/")
public class OauthController {

	private OauthService oauthService;
	
	@PostMapping("acceder")
	public Response<?> acceder(@RequestBody Map<String, Object> datos) throws IOException {
	
		return oauthService.acceder(datos);
      
	}
}
