package com.imss.sivimss.oauth.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.service.DelegacionService;

@Service
public class DelegacionServiceImpl extends UtileriaService implements DelegacionService {

	private static final Logger log = LoggerFactory.getLogger(DelegacionServiceImpl.class);
	
	@Override
	@Cacheable("delegacion-consulta")
	public List<Map<String, Object>> consulta() throws Exception {
		
		StringBuilder query = new StringBuilder("SELECT ID_DELEGACION as idDelegacion, ");
		query.append( "DES_DELEGACION as desDelegacion " );
		query.append( "FROM SVC_DELEGACION " );
		query.append( "ORDER BY ID_DELEGACION ASC " );
		log.info(query.toString());
		List<Map<String, Object>> resp;
		
		resp = consultaGenericaPorQuery( query.toString() );
		
		return resp;
	}

}
