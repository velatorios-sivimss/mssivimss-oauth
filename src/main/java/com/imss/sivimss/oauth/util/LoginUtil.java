package com.imss.sivimss.oauth.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginUtil {

	private Logger log = LoggerFactory.getLogger(LoginUtil.class);
	
	public String buscarPorIdUsuario(String idUsuario) {
		
		StringBuilder query = new StringBuilder("SELECT ");
		query.append( "* " );
		query.append( "FROM SVT_LOGIN " );
		query.append( "WHERE ID_USUARIO = ");
		query.append( idUsuario + " ");
		query.append( BdConstantes.LIMIT );
		
		log.info( query.toString() );
		
		return query.toString();
	}
	
	
	public String insertar(String idUsuario) {
		
		StringBuilder query = new StringBuilder("INSERT INTO ");
		query.append( "SVT_LOGIN (`ID_USUARIO`) VALUES " );
		query.append( "('" +idUsuario + "')");
		
		log.info( query.toString() );
		
		return query.toString();
	}
	
	public String buscarPorCveUsuario(String cveUsuario) {
		
		StringBuilder query = new StringBuilder(BdConstantes.SELECT);
		query.append( "LOGIN.* " );
		query.append( "FROM SVT_LOGIN LOGIN " );
		query.append( "INNER JOIN SVT_USUARIOS US ON US.ID_USUARIO = LOGIN.ID_USUARIO " );
		query.append( "WHERE US.CVE_USUARIO = ");
		query.append( cveUsuario + " ");
		query.append( BdConstantes.LIMIT );
		
		log.info( query.toString() );
		
		return query.toString();
	}
	
	public List<String> actContrasenia( String idLogin, String idUsuario, String contrasenia ) {
		
		List<String> lista = new ArrayList<>();
		StringBuilder query = new StringBuilder(BdConstantes.UPDATE);
		query.append( "SVT_USUARIOS " );
		query.append( "SET `CVE_CONTRASENIA` = '"+ contrasenia +"', " );
		query.append( "`FEC_ACTUALIZACION` = NOW(), " );
		query.append( "`ID_USUARIO_MODIFICA` = '"+ idUsuario +"' " );
		query.append( "WHERE (`ID_USUARIO` = '"+ idUsuario +"') " );
		
		log.info( query.toString() );
		lista.add( query.toString() );
		
		query = new StringBuilder(BdConstantes.UPDATE);
		query.append( "SVT_LOGIN " );
		query.append( "SET `FEC_CAMBIO_CONTRASENIA` = NOW(), " );
		query.append( "`CVE_ESTATUS_CUENTA` = '"+ BdConstantes.ESTATUS_ACTIVO +"' " );
		query.append( "WHERE (`ID_LOGIN` = '"+ idLogin +"') " );
		
		log.info( query.toString() );
		lista.add( query.toString() );
		
		return lista;
	}
	
	public String actNumIntentos( String idLogin, Integer numIntentos, Integer maxNumIntentos ) {
		
		StringBuilder query = new StringBuilder(BdConstantes.UPDATE);
		query.append( "SVT_LOGIN " );
		query.append( "SET `NUM_INTENTOS` = " + numIntentos );
		
		if( numIntentos >= maxNumIntentos ) {
			query.append( ", `FEC_BLOQUEO` = NOW() " );
		}else {
			query.append( ", `FEC_BLOQUEO` = null " );
		}
		
		query.append( " WHERE (`ID_LOGIN` = '"+ idLogin +"') " );
		
		log.info( query.toString() );
		
		return query.toString();
	}
	
	public String generarCodigo( Integer longitud ) {
		
		StringBuilder query = new StringBuilder();
		Random random = new Random();
		Integer numAleatorio;
		
		for(int i=0; i<longitud; i++) {
			
			numAleatorio = random.nextInt(10);
			log.info(numAleatorio.toString());
			query.append( numAleatorio );
		}
		
		log.info( query.toString() );
		return query.toString();
	}
	
	public String actCodSeg(String idLogin, String codigo) {
		
		StringBuilder query = new StringBuilder(BdConstantes.UPDATE);
		query.append( "SVT_LOGIN " );
		query.append( "SET `CVE_CODIGO_SEGURIDAD` = '" + codigo + "'" );
		query.append( ", `FEC_CODIGO_SEGURIDAD` = NOW() " );
		query.append( " WHERE (`ID_LOGIN` = '"+ idLogin +"') " );
		log.info( query.toString() );
		return query.toString();
		
	}
	
}
