package com.imss.sivimss.oauth.util;

import lombok.Getter;

@Getter
public enum MensajeEnum {
	OK("OK", "Respuesta Exitosa"),
	CONTRASENIA_PROXIMA_VENCER("CONTRASENIA_PROXIMA_VENCER", "La fecha del último cambio de contraseña está a quince días de caducar"),
	CONTRASENIA_INCORRECTA("CONTRASENIA_INCORRECTA", "La contraseña ingresada es incorrecta"),
	INTENTOS_FALLIDOS("INTENTOS_FALLIDOS", "Se ha alcanzado el maximo de intentos fallidos, favor de esperar "),
	CONTRASENIA_VENCIDA("CONTRASENIA_VENCIDA", "La fecha del último cambio de contraseña es superior a 3 meses"),
	USUARIO_PREACTIVO("USUARIO_PREACTIVO", "El usuario es Pre Activo"),
	ESTATUS_DESACTIVADO("ESTATUS_DESACTIVADO", "El usuario ha sido Desactivado");
	
	String valor;
	String desc;
	
	private MensajeEnum(String valor, String desc) {
		this.valor = valor;
		this.desc = desc;
	}
	
}
