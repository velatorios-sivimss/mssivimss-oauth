package com.imss.sivimss.oauth.util;

public class PermisosUtil {

	public String buscarFuncionalidad(String idRol) {
		
		StringBuilder query = new StringBuilder("SELECT DISTINCT(ID_FUNCIONALIDAD) ");
		query.append( "FROM SVC_ROL_FUNCIONALIDAD_PERMISO " );
		query.append( "WHERE ID_ROL = ");
		query.append( idRol + " ");
		query.append( BdConstantes.AND );
		query.append( BdConstantes.ACTIVO );
		query.append( "ORDER BY ID_FUNCIONALIDAD ASC" );
		
		return query.toString();
	}
	
	public String buscarPermisos(String idRol, String idFuncionalidad) {
		
		StringBuilder query = new StringBuilder("SELECT PER.* ");
		query.append( "FROM SVC_PERMISO PER " );
		query.append( "INNER JOIN SVC_ROL_FUNCIONALIDAD_PERMISO RFP ON RFP.ID_PERMISO = PER.ID_PERMISO ");
		query.append( "WHERE RFP.ID_ROL = " + idRol + " ");
		query.append( "AND RFP.ID_FUNCIONALIDAD = " + idFuncionalidad + " ");
		query.append( "ORDER BY PER.ID_PERMISO" );
		
		return query.toString();
	}
	
}
