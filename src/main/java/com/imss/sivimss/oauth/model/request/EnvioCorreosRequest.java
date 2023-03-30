package com.imss.sivimss.oauth.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class EnvioCorreosRequest {
    @JsonProperty
private ArrayList<CorreoRequest> usuarios;
}

