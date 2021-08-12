package me.oyurimatheus.nossositedeviagens.aeroportos;

import com.fasterxml.jackson.annotation.JsonCreator;
import me.oyurimatheus.nossositedeviagens.paises.Pais;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.Function;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

class NovoAeroportoRequest {

    @NotBlank
    private String nome;

    @NotNull
    @Min(1)
    private Long paisId;

    @JsonCreator(mode = PROPERTIES)
    public NovoAeroportoRequest(String nome, Long paisId) {
        this.nome = nome;
        this.paisId = paisId;
    }

    public Aeroporto paraAeroporto(Function<Long, Optional<Pais>> procuraPaisPorId) {

        var pais = procuraPaisPorId.apply(paisId).orElseThrow(() -> new IllegalStateException("Identificador invalido para pais"));

        return new Aeroporto(nome, pais);

    }
}
