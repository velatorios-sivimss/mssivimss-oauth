package com.imss.sivimss.oauth.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.security.JwtProvider;
import com.imss.sivimss.oauth.util.ProviderServiceRestTemplate;

@Service
public class UtileriaService {
	
	@Value("${endpoints.dominio-consulta}")
	String urlDominioConsulta;
	
	@Autowired
	ProviderServiceRestTemplate providerRestTemplate;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	JwtProvider jwtProvider;
	
	String consulta = "/generico/consulta";  

}
