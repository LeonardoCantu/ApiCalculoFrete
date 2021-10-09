/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calculo.frete.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Léo
 */
@Data
public class CepInput {

    @NotNull(message = "Campo Obrigatorio")
    private Double peso;

    @Length(min = 8, max = 8, message = "Deve conter 8 numeros")
    @NotNull(message = "Campo Obrigatorio")
    private String cepOrigem;

    @Length(min = 8, max = 8, message = "Deve conter 8 numeros")
    @NotNull(message = "Campo Obrigatorio")
    private String cepDestino;

    @Length(min = 0, max = 255, message = "Deve conter menos de 255 caracteres")
    @NotEmpty(message = "Campo Obrigatorio")
    private String nomeDestinatario;

    public CepInput(Double peso, String cepOrigem, String cepDestino, String nomeDestinatario) {
        this.peso = peso;
        this.cepOrigem = cepOrigem;
        this.cepDestino = cepDestino;
        this.nomeDestinatario = nomeDestinatario;
    }

}
