package com.imss.sivimss.oauth.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.service.VelatorioService;

@Service
public class VelatorioServiceImpl extends UtileriaService implements VelatorioService {

	private static final Logger log = LoggerFactory.getLogger(VelatorioServiceImpl.class);
	
	@Override
	@Cacheable("velatorio-consulta")
	public List<Map<String, Object>> consulta() throws Exception {
		StringBuilder query = new StringBuilder("SELECT ID_VELATORIO AS idVelatorio, ");
		query.append( "NOM_VELATORIO AS nomVelatorio " );
		query.append( "FROM SVC_VELATORIO " );
		query.append( "ORDER BY ID_VELATORIO ASC " );
		log.info(query.toString());
		List<Map<String, Object>> resp;
		
		resp = consultaGenericaPorQuery( query.toString() );
		
		return resp;
	}

}
