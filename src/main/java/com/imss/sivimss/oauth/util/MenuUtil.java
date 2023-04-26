package com.imss.sivimss.oauth.util;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imss.sivimss.oauth.model.response.MenuResponse;

public class MenuUtil {

	private Logger log = LoggerFactory.getLogger(MenuUtil.class);
	
	public String buscar(String idRol, Integer nivel) {
		
		StringBuilder query = new StringBuilder("SELECT ");
		query.append( "M.ID_MODULO AS idModulo, " );
		query.append( "M.ID_MODULO_PADRE AS idModuloPadre, " );
		query.append( "M.ID_FUNCIONALIDAD AS idFuncionalidad, " );
		query.append( "M.DES_TITULO AS titulo " );
		query.append( "FROM SVT_MENU M " );
		query.append( "LEFT JOIN SVC_ROL_FUNCIONALIDAD_PERMISO RP ON RP.ID_FUNCIONALIDAD = M.ID_FUNCIONALIDAD " );
		query.append( "AND RP.ID_ROL = ");
		query.append( idRol + " ");
		query.append( " AND RP.CVE_ESTATUS = 1 ");
		query.append( "WHERE M.NUM_NIVEL = "  + nivel);
		query.append( " GROUP BY M.ID_FUNCIONALIDAD " );
		
		log.info( query.toString() );
		
		return query.toString();
	}
	
	public void organizar(List<MenuResponse> padre, List<MenuResponse> hijo) {
		
		for(MenuResponse menu : hijo) {
			insertar(menu, padre);
		}
		
	}
	
	private void insertar(MenuResponse menu, List<MenuResponse> padre) {
		
		String idPadre= menu.getIdModuloPadre();
		log.info( "idPadre = " + idPadre);
		
		for( int i=0; i<padre.size(); i++ ) {
			
			if( idPadre.equals( padre.get(i).getIdModulo() )  ) {
				
				if(padre.get(i).getModulos() == null) {
					List<MenuResponse> modulos = new ArrayList<>();
					padre.get(i).setModulos(modulos);
				}
				
				padre.get(i).getModulos().add(menu);
				break;
				
			}
			
		}
		
	}
	
	public String obtenerMensajes() {
		
		StringBuilder query = new StringBuilder("SELECT ");
		query.append( "ID_MENSAJE AS idMensaje, " );
		query.append( "DES_MENSAJE AS desMensaje " );
		query.append( "FROM SVC_MENSAJE " );
		query.append( " ORDER BY ID_MENSAJE ASC" );
		
		log.info( query.toString() );
		
		return query.toString();
	}
	
}
