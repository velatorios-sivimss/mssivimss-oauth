package com.imss.sivimss.oauth.beans;

import java.util.Map;

import com.imss.sivimss.oauth.util.BdConstantes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class Usuario {
	private String idUsuario;
	private String materno;
	private String nombre;
	private String correo;
	private String claveMatricula;
	private String password;
	private String paterno;
	private String statusCuenta;
	private String idOficina;
	private String idVelatorio;
	private String idRol;
	private String desRol;
	private String idDelegacion;
	private String curp;
	private String claveUsuario;
	private String activo;
	
	public Usuario(Map<String, Object> datos) {
		this.idUsuario = datos.get("ID_USUARIO").toString();
		this.nombre = datos.get("NOM_PERSONA").toString();
		this.paterno = datos.get("NOM_PRIMER_APELLIDO").toString();
		this.materno = datos.get("NOM_SEGUNDO_APELLIDO").toString();
		this.correo = datos.get("REF_CORREO").toString();
		this.password = datos.get("CVE_CONTRASENIA").toString();
		
		if( datos.get("CVE_MATRICULA") != null ) {
			this.claveMatricula = datos.get("CVE_MATRICULA").toString();
		}
		
		this.idOficina = datos.get("ID_OFICINA").toString();
		
		if( datos.get("ID_DELEGACION") != null ) {
			this.idDelegacion = datos.get("ID_DELEGACION").toString();
		}
		
		if( datos.get("ID_VELATORIO") != null ) {
			this.idVelatorio = datos.get("ID_VELATORIO").toString();
		}
		
		this.idRol = datos.get("ID_ROL").toString();
		this.desRol = datos.get("DES_ROL").toString();
		this.curp = datos.get("CVE_CURP").toString();
		this.claveUsuario = datos.get("CVE_USUARIO").toString();
		this.activo = datos.get("IND_ACTIVO").toString();
	}
	
	
	public String buscarUsuario(String user) {
		
		StringBuilder query = new StringBuilder("SELECT\r\n"
				+ "US.ID_USUARIO,\r\n"
				+ "PER.NOM_PERSONA,\r\n"
				+ "PER.NOM_PRIMER_APELLIDO,\r\n"
				+ "PER.NOM_SEGUNDO_APELLIDO,\r\n"
				+ "PER.REF_CORREO,\r\n"
				+ "US.CVE_CONTRASENIA,\r\n"
				+ "US.CVE_MATRICULA,\r\n"
				+ "US.ID_OFICINA,\r\n"
				+ "US.ID_DELEGACION,\r\n"
				+ "US.ID_VELATORIO,\r\n"
				+ "ROL.ID_ROL,\r\n"
				+ "ROL.DES_ROL,\r\n"
				+ "PER.CVE_CURP,\r\n"
				+ "US.CVE_USUARIO,\r\n"
				+ "US.IND_ACTIVO\r\n"
				+ "FROM SVT_USUARIOS US \r\n"
				+ "INNER JOIN SVC_ROL ROL ON ROL.ID_ROL = US.ID_ROL \r\n"
				+ "INNER JOIN SVC_PERSONA PER ON PER.ID_PERSONA = US.ID_PERSONA\r\n"
				+ "WHERE CVE_USUARIO = ");
		query.append( "'" + user + "' \r\n" );
		query.append( "AND ROL.ID_ROL != 150  \r\n" );
		query.append( BdConstantes.LIMIT );
		
		return query.toString();
	}
	
}
