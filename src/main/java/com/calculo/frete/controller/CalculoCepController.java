package com.calculo.frete.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.calculo.frete.model.CepInput;
import com.calculo.frete.service.CalculoCepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Léo
 */
@Api(value = "Calculo Frete")
@RestController
@RequestMapping("/frete")
public class CalculoCepController {

    @Autowired
    private CalculoCepService calculoCepService;

    @ApiOperation(value = "Realiza Calculo Frete")
    @PostMapping("/calcular")
    private ResponseEntity<Object> calcularFrete(@Valid @RequestBody CepInput cepInput) {
        ResponseEntity response = calculoCepService.validaCepInput(cepInput);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return calculoCepService.calcularFrete(cepInput);
        }
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((erro) -> {
            String field = ((FieldError) erro).getField();
            String mensagem = erro.getDefaultMessage();
            erros.put(field, mensagem);
        });
        return erros;
    }
}
