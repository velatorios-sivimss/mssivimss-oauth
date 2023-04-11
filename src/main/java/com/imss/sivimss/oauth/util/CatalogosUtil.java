package com.imss.sivimss.oauth.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatalogosUtil {

	private Logger log = LoggerFactory.getLogger(CatalogosUtil.class);
	
	public String delegacion() {
		
		StringBuilder query = new StringBuilder("SELECT ID_DELEGACION as id, ");
		query.append( "DES_DELEGACION as 'desc' " );
		query.append( "FROM SVC_DELEGACION " );
		query.append( "ORDER BY ID_DELEGACION ASC " );
		log.info(query.toString());
		
		return query.toString();
		
	}
	
	public String nivelOficina() {
		
		StringBuilder query = new StringBuilder("SELECT ID_OFICINA AS id, ");
		query.append( "DES_NIVELOFICINA AS 'desc' " );
		query.append( "FROM SVC_NIVEL_OFICINA " );
		query.append( "ORDER BY ID_OFICINA ASC " );
		log.info(query.toString());
		
		return query.toString();
	}
	
	public String velatorios(String idVelatorio) {
		
		StringBuilder query = new StringBuilder("SELECT ID_VELATORIO AS id, ");
		query.append( "NOM_VELATORIO AS 'desc' " );
		query.append( "FROM SVC_VELATORIO " );
		query.append( "WHERE ID_DELEGACION = " +  idVelatorio);
		query.append( " ORDER BY ID_VELATORIO ASC " );
		log.info(query.toString());
		
		return query.toString();
	}
	
	public String parentesco() {
		
		StringBuilder query = new StringBuilder("SELECT ID_PARENTESCO AS id, ");
		query.append( "DES_PARENTESCO AS 'desc' " );
		query.append( "FROM SVC_PARENTESCO " );
		query.append( "ORDER BY ID_PARENTESCO ASC " );
		log.info(query.toString());
		
		return query.toString();
	}
	
	public String pais() {
		
		StringBuilder query = new StringBuilder("SELECT ID_PAIS AS id, ");
		query.append( "DES_PAIS AS 'desc' " );
		query.append( "FROM SVC_PAIS " );
		query.append( "ORDER BY ID_PAIS ASC " );
		log.info(query.toString());
		
		return query.toString();
	}
	
	public String estados() {
		
		StringBuilder query = new StringBuilder("SELECT ID_ESTADO AS id, ");
		query.append( "DES_ESTADO AS 'desc' " );
		query.append( "FROM SVC_ESTADO " );
		query.append( "ORDER BY ID_ESTADO ASC " );
		log.info(query.toString());
		
		return query.toString();
	}
	
	public String tipoOrden() {
		
		StringBuilder query = new StringBuilder("SELECT ID_TIPO_ORDEN_SERVICIO AS id, ");
		query.append( "DES_TIPO_ORDEN_SERVICIO AS 'desc' " );
		query.append( "FROM SVC_TIPO_ORDEN_SERVICIO " );
		query.append( "ORDER BY ID_TIPO_ORDEN_SERVICIO ASC " );
		log.info(query.toString());
		
		return query.toString();
	}
	
	public String tipoPension() {
		
		StringBuilder query = new StringBuilder("SELECT ID_TIPO_PENSION AS id, ");
		query.append( "DES_PENSION AS 'desc' " );
		query.append( "FROM SVC_TIPO_PENSION " );
		query.append( "ORDER BY ID_TIPO_PENSION ASC " );
		log.info(query.toString());
		
		return query.toString();
	}
	
	public String unidadesMedicas() {
		
		StringBuilder query = new StringBuilder("SELECT ID_UNIDAD_MEDICA AS id, ");
		query.append( "DES_UNIDAD_MEDICA AS 'desc' " );
		query.append( "FROM SVC_UNIDAD_MEDICA " );
		query.append( "ORDER BY ID_UNIDAD_MEDICA ASC " );
		log.info(query.toString());
		
		return query.toString();
	}
	
}
