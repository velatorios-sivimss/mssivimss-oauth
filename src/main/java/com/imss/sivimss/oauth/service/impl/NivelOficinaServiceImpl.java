package com.imss.sivimss.oauth.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.service.NivelOficinaService;

@Service
public class NivelOficinaServiceImpl extends UtileriaService implements NivelOficinaService {

	private static final Logger log = LoggerFactory.getLogger(NivelOficinaServiceImpl.class);
	
	@Override
	@Cacheable("nivelOficina-consulta")
	public List<Map<String, Object>> consulta() throws Exception {
		StringBuilder query = new StringBuilder("SELECT ID_OFICINA AS idOficina, ");
		query.append( "DES_NIVELOFICINA AS desNivelOficina " );
		query.append( "FROM SVC_NIVEL_OFICINA " );
		query.append( "ORDER BY ID_OFICINA ASC " );
		log.info(query.toString());
		List<Map<String, Object>> resp;
		
		resp = consultaGenericaPorQuery( query.toString() );
		
		return resp;
	}

}
