package com.calculo.frete.service;

import com.calculo.frete.enums.EnumRegraDescontoEnvio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculo.frete.model.CalculoCep;
import com.calculo.frete.model.CepInput;
import com.calculo.frete.model.CepReturn;
import com.calculo.frete.repository.CalculoCepRepository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/**
 *
 * @author Léo
 */
@Service
public class CalculoCepService {

    @Autowired
    private CalculoCepRepository calculoCepRepository;

    @Autowired
    private WebClient webClient;

    public ResponseEntity<Object> calcularFrete(CepInput cepInput) {
        try {
            final Mono<CepReturn> cepOrigemReturn = buscarCep(cepInput.getCepOrigem());
            final Mono<CepReturn> cepDestinoReturn = buscarCep(cepInput.getCepDestino());

            final CepReturn cepOrigem = cepOrigemReturn.block();
            final CepReturn cepDestino = cepDestinoReturn.block();

            if (cepDestino.getDdd() == null || cepDestino.getDdd() == null) {
                return ResponseEntity.badRequest().body("Cep não localizado");
            }

            final EnumRegraDescontoEnvio regraDescontoEnvio = definirDesconto(cepOrigem, cepDestino);
            final Double valorFrete = definirValorFrete(regraDescontoEnvio, cepInput.getPeso());
            final Date dataEntrega = calcularDataEntrega(regraDescontoEnvio);

            final CalculoCep calculoCep = new CalculoCep();

            calculoCep.setCepDestino(cepInput.getCepDestino());
            calculoCep.setCepOrigem(cepInput.getCepOrigem());
            calculoCep.setNomeDestinatario(cepInput.getNomeDestinatario());
            calculoCep.setPeso(cepInput.getPeso());
            calculoCep.setValorTotal(valorFrete);
            calculoCep.setDataConsulta(new Date());
            calculoCep.setDataPrevisaoEntrega(dataEntrega);

            calculoCepRepository.save(calculoCep);
            return ResponseEntity.ok(calculoCep);
        } catch (WebClientResponseException.BadRequest e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private Date calcularDataEntrega(EnumRegraDescontoEnvio regraDescontoEnvio) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, regraDescontoEnvio.getPrevisaoEntrega());
        Date date = calendar.getTime();
        return date;

    }

    private Double definirValorFrete(EnumRegraDescontoEnvio regraDescontoEnvio, Double peso) throws Exception {
        final Double valorKG = new Double(1.0);
        Double valorTotal = peso * valorKG;
        return valorTotal - (valorTotal * regraDescontoEnvio.getPorcentagemDesconto() / 100);

    }

    private EnumRegraDescontoEnvio definirDesconto(CepReturn cepOrigem, CepReturn cepDestino) throws Exception {
        if (cepDestino.getDdd().equals(cepOrigem.getDdd())) {
            return EnumRegraDescontoEnvio.DDD_IGUAL;
        } else if (cepDestino.getUf().equals(cepOrigem.getUf())) {
            return EnumRegraDescontoEnvio.ESTADO_IGUAL;
        } else {
            return EnumRegraDescontoEnvio.ESTADO_DIFERENTE;
        }
    }

    private Mono<CepReturn> buscarCep(String cep) throws Exception {

        Mono<CepReturn> cepReturn = this.webClient
                .method(HttpMethod.GET)
                .uri("{cep}/json/", cep)
                .retrieve()
                .bodyToMono(CepReturn.class);
        return cepReturn;
    }

    public ResponseEntity<Object> validaCepInput(CepInput cepInput) {
        try {
            List<String> ceps = new ArrayList();
            ceps.add(cepInput.getCepDestino());
            ceps.add(cepInput.getCepOrigem());

            for (String cep : ceps) {
                cep = cep.replaceAll("[\\D]", "");
                if (cep.length() != 8) {
                    return ResponseEntity.badRequest().body("Informe um Cep valido");
                }
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            ResponseEntity.badRequest().build();
        }
        return null;
    }
}
