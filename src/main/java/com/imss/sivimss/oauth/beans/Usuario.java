package com.imss.sivimss.oauth.beans;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import com.imss.sivimss.oauth.util.AppConstantes;
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

	
	public Usuario(Map<String, Object> datos) {
		this.idUsuario = datos.get("ID_USUARIO").toString();
		this.nombre = datos.get("NOM_USUARIO").toString();
		this.paterno = datos.get("NOM_APELLIDO_PATERNO").toString();
		this.materno = datos.get("NOM_APELLIDO_MATERNO").toString();
		this.correo = datos.get("DES_CORREOE").toString();
		this.password = datos.get("CVE_CONTRASENIA").toString();
		this.claveMatricula = datos.get("CVE_MATRICULA").toString();
		this.idOficina = datos.get("ID_OFICINA").toString();
		this.idDelegacion = datos.get("ID_DELEGACION").toString();
		this.idVelatorio = datos.get("ID_VELATORIO").toString();
		this.idRol = datos.get("ID_ROL").toString();
		this.desRol = datos.get("DES_ROL").toString();
		this.curp = datos.get("DES_CURP").toString();
		//this.statusCuenta = datos.get("ESTATUS_CUENTA").toString();
	}
	
	
	public Map<String, Object> buscarUsuario(String user) {
		
		Map<String, Object> datos = new HashMap<>();
		
		StringBuilder query = new StringBuilder(BdConstantes.SELECT_USUARIOS);
		query.append( "INNER JOIN SVC_ROL ROL ON ROL.ID_ROL = US.ID_ROL " );
		query.append( BdConstantes.WHERE );
		query.append( BdConstantes.CVE_MATRICULA + " = ");
		query.append( "'" + user + "' " );
		query.append( BdConstantes.AND );
		query.append( BdConstantes.ACTIVO );
		query.append( BdConstantes.LIMIT );
		String encoded = DatatypeConverter.printBase64Binary(query.toString().getBytes());
		
		datos.put(AppConstantes.QUERY, encoded);
		return datos;
	}
}
