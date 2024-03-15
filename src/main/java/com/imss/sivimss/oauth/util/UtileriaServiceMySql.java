package com.imss.sivimss.oauth.util;

public class UtileriaServiceMySql {
	public String inserBitacora (String id) {
		String query = "INSERT\r\n"
				+ "INTO\r\n"
				+ "SVH_BITACORA_DOCUMENTOS (ID_TIPO_TRANSACCION, ID_USUARIO)\r\n"
				+ "VALUES\r\n"
				+ "(4,"+id+")\r\n";
		return query;
	} 


}
