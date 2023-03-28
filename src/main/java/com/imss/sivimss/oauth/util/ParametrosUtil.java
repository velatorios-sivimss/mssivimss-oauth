package com.imss.sivimss.oauth.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParametrosUtil {

	private Logger log = LoggerFactory.getLogger(ParametrosUtil.class);
	
	public String tiempoToken() {
		
		StringBuilder query = new StringBuilder("SELECT TIP_PARAMETRO ");
		query.append( "FROM SVC_PARAMETRO_SISTEMA " );
		query.append( "WHERE DES_PARAMETRO = 'TIEMPO TOKEN' ");
		query.append( "AND CVE_ESTATUS = '1' ");
		query.append( "LIMIT 1 ");
		
		log.info( query.toString() );
		
		return query.toString();
	}
	
	public String consultarSiap() {
		
		StringBuilder query = new StringBuilder("SELECT TIP_PARAMETRO ");
		query.append( "FROM SVC_PARAMETRO_SISTEMA " );
		query.append( "WHERE DES_PARAMETRO = 'CONSULTAR SIAP' ");
		query.append( "AND CVE_ESTATUS = '1' ");
		query.append( "LIMIT 1 ");
		
		log.info( query.toString() );
		
		return query.toString();
	}
	
	public String numDias() {
		
		StringBuilder query = new StringBuilder("SELECT TIP_PARAMETRO ");
		query.append( "FROM SVC_PARAMETRO_SISTEMA " );
		query.append( "WHERE DES_PARAMETRO = 'NUM DIAS A CADUCAR' ");
		query.append( "AND CVE_ESTATUS = '1' ");
		query.append( "LIMIT 1 ");
		
		log.info( query.toString() );
		
		return query.toString();
	}
	
	public String numMeses() {
		
		StringBuilder query = new StringBuilder("SELECT TIP_PARAMETRO ");
		query.append( "FROM SVC_PARAMETRO_SISTEMA " );
		query.append( "WHERE DES_PARAMETRO = 'NUM MESES VIGENCIA' ");
		query.append( "AND CVE_ESTATUS = '1' ");
		query.append( "LIMIT 1 ");
		
		log.info( query.toString() );
		
		return query.toString();
	}
	
	public String numIntentos() {
		
		StringBuilder query = new StringBuilder("SELECT TIP_PARAMETRO ");
		query.append( "FROM SVC_PARAMETRO_SISTEMA " );
		query.append( "WHERE DES_PARAMETRO = 'NUM MAXIMO DE INTENTOS' ");
		query.append( "AND CVE_ESTATUS = '1' ");
		query.append( "LIMIT 1 ");
		
		log.info( query.toString() );
		
		return query.toString();
	}
	
	public String tiempoBloqueo() {
		
		StringBuilder query = new StringBuilder("SELECT TIP_PARAMETRO ");
		query.append( "FROM SVC_PARAMETRO_SISTEMA " );
		query.append( "WHERE DES_PARAMETRO = 'MINUTOS DE BLOQUEO' ");
		query.append( "AND CVE_ESTATUS = '1' ");
		query.append( "LIMIT 1 ");
		
		log.info( query.toString() );
		
		return query.toString();
	}
}
