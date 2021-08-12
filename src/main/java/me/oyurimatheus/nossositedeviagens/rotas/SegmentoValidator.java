package me.oyurimatheus.nossositedeviagens.rotas;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class SegmentoValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return NovaRotaRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object alvo, Errors erros) {

        var novaRota = (NovaRotaRequest) alvo;
        if (!novaRota.segmentoEhValido()) {
            erros.rejectValue("aeroportoOrigem",
                    "aeroportoOrigem.aeroportoDestino",
                    "o aeroportoOrigem nao pode ser igual ao aeroportoDestino");
        }


    }
}
