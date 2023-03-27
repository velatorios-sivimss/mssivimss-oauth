package com.imss.sivimss.oauth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.exception.BadRequestException;
import com.imss.sivimss.oauth.model.Login;
import com.imss.sivimss.oauth.service.ContraseniaService;
import com.imss.sivimss.oauth.service.CuentaService;
import com.imss.sivimss.oauth.util.ConstantsMensajes;
import com.imss.sivimss.oauth.util.Response;

@Service
public class ContraseniaServiceImpl implements ContraseniaService {
	
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
		
		if (!exito) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Error al actualizar en BD");
		}
		
		resp =  new Response<>(false, HttpStatus.OK.value(), ConstantsMensajes.EXITO.getMensaje(),
				exito );
		
		
		return resp;
	}

}
