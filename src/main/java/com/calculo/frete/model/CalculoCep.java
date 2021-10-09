package com.calculo.frete.model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author Léo
 */
@Data
@Entity
@Table(name = "CALCULO_CEP")
public class CalculoCep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
     
    @Column(name = "NOME_DESTINATARIO")
    private String nomeDestinatario;
    
    @Column(name = "PESO")
    private Double peso;
    
    @Column(name = "CEP_ORIGEM")
    private String cepOrigem;
    
    @Column(name = "CEP_DESTINO")
    private String cepDestino;
    
    @Column(name = "VALOR_TOTAL")
    private Double valorTotal;
    
    @Column(name = "DATA_PREV_ENTREGA")
    private Date dataPrevisaoEntrega;
    
    @Column(name = "DATA_CONSULTA")
    private Date dataConsulta;


    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CalculoCep other = (CalculoCep) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

   

	
}
