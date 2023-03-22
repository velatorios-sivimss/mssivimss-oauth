package com.imss.sivimss.oauth.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imss.sivimss.oauth.model.response.MenuResponse;

public class MenuUtil {

	private Logger log = LoggerFactory.getLogger(MenuUtil.class);
	
	public Map<String, Object> buscar(String idRol, Integer nivel) {
		
		Map<String, Object> datos = new HashMap<>();
		
		StringBuilder query = new StringBuilder("SELECT ");
		query.append( "id_tabla_menu AS idTablaMenu, " );
		query.append( "id_tabla_padre AS idTablaPadre, " );
		query.append( "id_modulo AS idModulo, " );
		query.append( "desc_icono AS descIcono, " );
		query.append( "titulo " );
		query.append( "FROM menu " );
		query.append( "WHERE ID_ROL = ");
		query.append( idRol + " ");
		query.append( "AND " );
		query.append( "nivel = "  + nivel);
		query.append( " ORDER BY id_tabla_menu ASC" );
		
		log.info( query.toString() );
		String encoded = DatatypeConverter.printBase64Binary(query.toString().getBytes());
		
		datos.put(AppConstantes.QUERY, encoded);
		return datos;
	}
	
	public void organizar(List<MenuResponse> padre, List<MenuResponse> hijo) {
		
		for(MenuResponse menu : hijo) {
			insertar(menu, padre);
		}
		
	}
	
	private void insertar(MenuResponse menu, List<MenuResponse> padre) {
		
		String idPadre= menu.getIdTablaPadre();
		log.info( "idPadre = " + idPadre);
		
		for( int i=0; i<padre.size(); i++ ) {
			
			if( idPadre.equals( padre.get(i).getIdTablaMenu() )  ) {
				
				if(padre.get(i).getModulos() == null) {
					List<MenuResponse> modulos = new ArrayList<>();
					padre.get(i).setModulos(modulos);
				}
				
				padre.get(i).getModulos().add(menu);
				break;
				
			}
			
		}
		
	}
	
	public Map<String, Object> obtenerMensajes() {
		
		Map<String, Object> datos = new HashMap<>();
		
		StringBuilder query = new StringBuilder("SELECT ");
		query.append( "ID_MENSAJE AS idMensaje, " );
		query.append( "DES_MENSAJE AS desMensaje " );
		query.append( "FROM SVC_MENSAJE " );
		query.append( " ORDER BY ID_MENSAJE ASC" );
		
		log.info( query.toString() );
		String encoded = DatatypeConverter.printBase64Binary(query.toString().getBytes());
		
		datos.put(AppConstantes.QUERY, encoded);
		return datos;
	}
	
}
