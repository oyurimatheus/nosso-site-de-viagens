package me.oyurimatheus.nossositedeviagens.paises;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

public class NovoPaisRequest {

    @NotBlank
    private String nome;

    @JsonCreator(mode = PROPERTIES)
    public NovoPaisRequest(@NotBlank String nome) {
        this.nome = nome;
    }

    public Pais paraPais() {
        return new Pais(nome);
    }
}
