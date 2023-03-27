package com.imss.sivimss.oauth.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.config.mymapper.Consultas;
import com.imss.sivimss.oauth.config.mymapper.IdDto;
import com.imss.sivimss.oauth.util.Database;
import com.imss.sivimss.oauth.config.MyBatisConnect;
import com.imss.sivimss.oauth.security.JwtProvider;

@Service
public class UtileriaService {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	private MyBatisConnect con;
	
	@Autowired
	private Database database;

	protected static Consultas consultas = null;
	protected static AnnotationConfigApplicationContext context = null;

	private static final Logger log = LoggerFactory.getLogger(UtileriaService.class);

	private static final String FALLO_QUERY = "Fallo al ejecutar el Query  ";
	
	public List<Map<String, Object>> consultaGenericaPorQuery(String query) throws Exception {
		List<Map<String, Object>> resp = new ArrayList<>();

		try {

			context = con.conectar();
			consultas = con.crearBeanDeConsultas();
			resp = consultas.selectHashMap(query);

		} catch (Exception e) {
			log.error(FALLO_QUERY + ", {}", e.getMessage());
			throw new Exception(FALLO_QUERY + e.getMessage());
		}

		return resp;
	}
	
	public Map<String, Object> insertarDetalle(String query, String tablaNom, String tablaId) throws Exception {
		IdDto idDto= new IdDto();
		log.info(query);
		Map<String, Object> detalle = null;
		
		StringBuilder consulta = new StringBuilder();
		
		try {

			context = con.conectar();
			consultas = con.crearBeanDeConsultas();
			consultas.insert(query, idDto);
			
			consulta.append("SELECT * FROM ");
			consulta.append(tablaNom);
			consulta.append(" WHERE ");
			consulta.append(tablaId);
			consulta.append(" = ");
			consulta.append(idDto.getId());
			
			List<Map<String, Object>> lista = consultaGenericaPorQuery(consulta.toString());
			detalle = lista.get(0);
			
		} catch (Exception e) {
			log.error(FALLO_QUERY + ", {}", e.getMessage());
			throw new Exception(FALLO_QUERY + e.getMessage());
		}
		
		return detalle;

	}

	
	public Boolean actualizaGenericoPorQuery(String query) throws Exception {

		boolean resp = false;
	
		log.info(query);

		try {

			context = con.conectar();
			consultas = con.crearBeanDeConsultas();
			resp = consultas.actualizar(query);

		} catch (Exception e) {
			log.error(FALLO_QUERY + ", {}", e.getMessage());
			throw new Exception(FALLO_QUERY + e.getMessage());
		}
		return resp;

	}
	
	public Boolean actualizarMultiple(List<String> querys) throws Exception {

		Boolean exito = false;
		Connection connection = database.getConnection();
		PreparedStatement stmt1 = null;
		
		try {
			
			connection.setAutoCommit(false);
			
			for( String actualizacion : querys ) {
				
				stmt1 = connection.prepareStatement(actualizacion);
				stmt1.executeUpdate();
				
			}
			
			connection.commit();
			
		} catch (Exception e) {
			log.error(FALLO_QUERY + ", {}", e.getMessage());
			throw new Exception(FALLO_QUERY + e.getMessage());
		}finally{
			log.info( "cierra conexion a la base de datos" );    
			try {
				if(stmt1!=null) stmt1.close();                                
				if(connection!=null) connection.close();
			} catch (SQLException ex) {
				log.error( ex.getMessage() );    
			}
		}
		
		exito = true;
		
		return exito;
	}
}
