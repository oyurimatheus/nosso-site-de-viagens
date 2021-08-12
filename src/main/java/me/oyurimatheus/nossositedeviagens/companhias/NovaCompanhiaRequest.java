package me.oyurimatheus.nossositedeviagens.companhias;

import com.fasterxml.jackson.annotation.JsonCreator;
import me.oyurimatheus.nossositedeviagens.paises.Pais;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.Function;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;


class NovaCompanhiaRequest {

    @NotBlank
    private String nome;

    @NotNull
    @Min(1)
    private Long paisId;

    @JsonCreator(mode = PROPERTIES)
    public NovaCompanhiaRequest(@NotBlank String nome,
                                @NotNull @Min(1) Long paisId) {
        this.nome = nome;
        this.paisId = paisId;
    }

    public Companhia paraCompanhia(Function<Long, Optional<Pais>> procuraPaisPorId) {

        Optional<Pais> possivelPais = procuraPaisPorId.apply(paisId);
        Pais pais = possivelPais.orElseThrow(() -> new IllegalStateException("Identificador invalido para pais"));

        return new Companhia(nome, pais);
    }
}
