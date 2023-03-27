package com.imss.sivimss.oauth.util;

/**
 * Clase para la paginacion
 *
 * @author    
 * @puesto dev
 * @date 24 nov. 2022
 */
public class BdConstantes {
	
	public static final String SELECT_USUARIOS = "SELECT * FROM SVT_USUARIOS US ";
	public static final String WHERE = "WHERE ";
	public static final String AND = "AND ";
	public static final String CVE_MATRICULA= "CVE_MATRICULA";
	public static final String ACTIVO= "CVE_ESTATUS = '1' ";
	public static final String LIMIT= "LIMIT 1 ";
	public static final String SELECT= "SELECT ";
	public static final String UPDATE= "UPDATE ";
	
	public static final String ESTATUS_ACTIVO= "ACTIVO";
	public static final String ESTATUS_PRE_ACTIVO= "PRE ACTIVO";
	public static final String ESTATUS_DESACTIVADO= "DESACTIVADA";
	
	private BdConstantes() {
	    throw new IllegalStateException("BdConstantes class");
	  }

}
