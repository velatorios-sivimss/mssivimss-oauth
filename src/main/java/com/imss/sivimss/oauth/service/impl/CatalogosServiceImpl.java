package com.imss.sivimss.oauth.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.imss.sivimss.oauth.model.response.Catalogos;
import com.imss.sivimss.oauth.model.response.CatalogosResponse;
import com.imss.sivimss.oauth.service.CatalogosService;
import com.imss.sivimss.oauth.util.CatalogosUtil;

@Service
public class CatalogosServiceImpl extends UtileriaService implements CatalogosService {
	
	@Override
	public CatalogosResponse consulta() throws Exception {
		CatalogosUtil catalogosUtil = new CatalogosUtil();
		CatalogosResponse catalogosResponse = new CatalogosResponse();
		Catalogos catalogos = new Catalogos();
		List<Map<String, Object>> delegaciones = consultaGenericaPorQuery( catalogosUtil.delegacion() );
		List<Map<String, Object>> nivelOficina = consultaGenericaPorQuery( catalogosUtil.nivelOficina() );
		List<Map<String, Object>> parentesco = consultaGenericaPorQuery( catalogosUtil.parentesco() );
		List<Map<String, Object>> pais = consultaGenericaPorQuery( catalogosUtil.pais() );
		List<Map<String, Object>> estados = consultaGenericaPorQuery( catalogosUtil.estados() );
		List<Map<String, Object>> tipoOrden = consultaGenericaPorQuery( catalogosUtil.tipoOrden() );
		List<Map<String, Object>> tipoPension = consultaGenericaPorQuery( catalogosUtil.tipoPension() );
		List<Map<String, Object>> unidadesMedicas = consultaGenericaPorQuery( catalogosUtil.unidadesMedicas() );
		
		catalogos.setDelegaciones(delegaciones);
		catalogos.setNivelOficina(nivelOficina);
		catalogos.setParentesco(parentesco);
		catalogos.setPais(pais);
		catalogos.setEstados(estados);
		catalogos.setTipoOrden(tipoOrden);
		catalogos.setTipoPension(tipoPension);
		catalogos.setUnidadesMedicas(unidadesMedicas);
		
		catalogosResponse.setCatalogos(catalogos);
		
		return catalogosResponse;
	}

}
