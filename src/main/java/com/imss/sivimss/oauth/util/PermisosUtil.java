package com.imss.sivimss.oauth.util;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

public class PermisosUtil {

	public Map<String, Object> buscarFuncionalidad(String idRol) {
		
		Map<String, Object> datos = new HashMap<>();
		
		StringBuilder query = new StringBuilder("SELECT DISTINCT(ID_FUNCIONALIDAD) ");
		query.append( "FROM SVC_ROL_FUNCIONALIDAD_PERMISO " );
		query.append( "WHERE ID_ROL = ");
		query.append( idRol + " ");
		query.append( BdConstantes.AND );
		query.append( BdConstantes.ACTIVO );
		query.append( "ORDER BY ID_FUNCIONALIDAD ASC" );
		String encoded = DatatypeConverter.printBase64Binary(query.toString().getBytes());
		
		datos.put(AppConstantes.QUERY, encoded);
		return datos;
	}
	
	public Map<String, Object> buscarPermisos(String idRol, String idFuncionalidad) {
		
		Map<String, Object> datos = new HashMap<>();
		
		StringBuilder query = new StringBuilder("SELECT PER.* ");
		query.append( "FROM SVC_PERMISO PER " );
		query.append( "INNER JOIN SVC_ROL_FUNCIONALIDAD_PERMISO RFP ON RFP.ID_PERMISO = PER.ID_PERMISO ");
		query.append( "WHERE RFP.ID_ROL = " + idRol + " ");
		query.append( "AND RFP.ID_FUNCIONALIDAD = " + idFuncionalidad + " ");
		query.append( "ORDER BY PER.ID_PERMISO" );
		String encoded = DatatypeConverter.printBase64Binary(query.toString().getBytes());
		
		datos.put(AppConstantes.QUERY, encoded);
		return datos;
	}
	
	public Map<String, Object> tiempoToken() {
		
		Map<String, Object> datos = new HashMap<>();
		
		StringBuilder query = new StringBuilder("SELECT TIP_PARAMETRO ");
		query.append( "FROM SVC_PARAMETRO_SISTEMA " );
		query.append( "WHERE DES_PARAMETRO = 'TIEMPO TOKEN' ");
		query.append( "AND CVE_ESTATUS = '1' ");
		query.append( "LIMIT 1 ");
		String encoded = DatatypeConverter.printBase64Binary(query.toString().getBytes());
		
		datos.put(AppConstantes.QUERY, encoded);
		return datos;
	}
	
	
}
