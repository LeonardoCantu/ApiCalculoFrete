/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calculo.frete.enums;

/**
 *
 * @author Léo
 */
public enum EnumRegraDescontoEnvio{
    
    DDD_IGUAL(50, 1),
    ESTADO_IGUAL(75, 3),
    ESTADO_DIFERENTE(0, 10);
    
    private final Integer porcentagemDesconto;
    
    private final Integer previsaoEntrega;
    
    private EnumRegraDescontoEnvio(Integer porcentagemDesconto, Integer previsaoEntrega) {
        this.porcentagemDesconto = porcentagemDesconto;
        this.previsaoEntrega = previsaoEntrega;
    }

    public Integer getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public Integer getPrevisaoEntrega() {
        return previsaoEntrega;
    }
           
}
