package com.imss.sivimss.oauth.model.response;

import java.util.List;

import lombok.Data;

@Data
public class MenuResponse {

	private String idTablaMenu;
	private String idTablaPadre;
	private String idModulo;
	private String descIcono;
	private String titulo;
	private List<MenuResponse> modulos;
	
}
