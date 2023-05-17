package com.imss.sivimss.oauth.config;

import java.io.IOException;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.imss.sivimss.oauth.config.mymapper.Consultas;
import com.imss.sivimss.oauth.util.LogUtil;

@Component
public class MyBatisConnect {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogUtil.class);
	
	private AnnotationConfigApplicationContext context = null;
	
	public AnnotationConfigApplicationContext conectar () throws IOException {
		try {
			context = new AnnotationConfigApplicationContext(MyBatisConfig.class);
			return context;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public Consultas crearBeanDeConsultas () throws IOException {
		Consultas consultas = null;
		
		try {
			consultas = context.getBean(Consultas.class);
		} catch (Exception e) {
			log.error(e.getMessage());
			return consultas;
		}
		
		return consultas;
	}
}
