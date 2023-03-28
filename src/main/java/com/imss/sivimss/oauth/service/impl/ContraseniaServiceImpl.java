package com.imss.sivimss.oauth.service.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.exception.BadRequestException;
import com.imss.sivimss.oauth.model.Login;
import com.imss.sivimss.oauth.service.ContraseniaService;
import com.imss.sivimss.oauth.service.CuentaService;
import com.imss.sivimss.oauth.util.ConstantsMensajes;
import com.imss.sivimss.oauth.util.EstatusVigenciaEnum;
import com.imss.sivimss.oauth.util.ParametrosUtil;
import com.imss.sivimss.oauth.util.Response;

@Service
public class ContraseniaServiceImpl extends UtileriaService implements ContraseniaService {
	
	@Autowired
	private CuentaService cuentaService;
	
	@Override
	public Response<?> cambiar(String user, String contraAnterior, String contraNueva) throws Exception {
		
		Response<Object> resp;
		Boolean exito = false;
		
		if ( contraAnterior.equals(contraNueva) ) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Contraseñas iguales");
		}
		
		Login login = cuentaService.obtenerLoginPorCveUsuario( user );
		
		exito = cuentaService.actualizarContra(login.getIdLogin(), login.getIdUsuario(), contraNueva);
		
		if (Boolean.FALSE.equals(exito)) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Error al actualizar en BD");
		}
		
		resp =  new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
				exito );
		
		
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer validarFecha(String fecha) throws Exception {
		List<Map<String, Object>> datos;
		ParametrosUtil parametrosUtil = new ParametrosUtil();
		List<Map<String, Object>> mapping;
		String sNumDias;
		Integer numDias;
		String sNumMeses;
		Integer numMeses;
		Date fechaActual;
		Date fechaProxVencer;
		Date fechaVencida;
		SimpleDateFormat formatter;
		Calendar calendar = Calendar.getInstance();
		Integer estatus = EstatusVigenciaEnum.VALIDA.getId();
		
		datos = consultaGenericaPorQuery( parametrosUtil.numDias() );
		mapping = Arrays.asList(modelMapper.map(datos, HashMap[].class));
		
		sNumDias = mapping.get(0).get("TIP_PARAMETRO").toString();
		numDias = Integer.parseInt(sNumDias);
		numDias = numDias * (-1);
		
		datos = consultaGenericaPorQuery( parametrosUtil.numMeses() );
		mapping = Arrays.asList(modelMapper.map(datos, HashMap[].class));
		
		sNumMeses = mapping.get(0).get("TIP_PARAMETRO").toString();
		numMeses = Integer.parseInt(sNumMeses);
		
		formatter = new SimpleDateFormat(PATTERN);
		fechaActual = formatter.parse(fecha);
		
		calendar.setTime(fechaActual);
		calendar.add(Calendar.MONTH , numMeses);
		fechaVencida = calendar.getTime();
		
		calendar.add(Calendar.DAY_OF_YEAR, numDias);
		fechaProxVencer = calendar.getTime();
		
		if( fechaActual.after(fechaProxVencer) && fechaActual.before(fechaVencida) ) {
			estatus = EstatusVigenciaEnum.PROXIMA_VENCER.getId();
		}else if( fechaActual.after(fechaVencida) ) {
			estatus = EstatusVigenciaEnum.VENCIDA.getId();
		}
		
		return estatus;
	}
	
}
