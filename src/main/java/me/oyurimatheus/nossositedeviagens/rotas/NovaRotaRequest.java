package me.oyurimatheus.nossositedeviagens.rotas;

import com.fasterxml.jackson.annotation.JsonCreator;
import me.oyurimatheus.nossositedeviagens.aeroportos.Aeroporto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Function;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

class NovaRotaRequest {

    private String nome;

    @Positive
    @Min(1)
    private long duracao;

    @NotNull
    private Long aeroportoOrigem;

    @NotNull
    private Long aeroportoDestino;

    @JsonCreator(mode = PROPERTIES)
    public NovaRotaRequest(String nome,
                           @Positive @Min(1) int duracao,
                           @NotNull Long aeroportoOrigem,
                           @NotNull Long aeroportoDestino) {
        this.nome = nome;
        this.duracao = duracao;
        this.aeroportoOrigem = aeroportoOrigem;
        this.aeroportoDestino = aeroportoDestino;
    }

    public Rota paraRota(Function<Long, Optional<Aeroporto>> buscaAeroportoPorId) {

        Aeroporto origem = buscaAeroportoPorId.apply(aeroportoOrigem).orElseThrow(() -> new IllegalStateException("Identificador invalido para aeroportoOrigem"));
        Aeroporto destino = buscaAeroportoPorId.apply(aeroportoDestino).orElseThrow(() -> new IllegalStateException("Identificador invalido para aeroportoDestino"));

        String nomeDaRota = nome != null ? nome : Rota.geraNome(origem, destino);
        return new Rota(nomeDaRota, Duration.ofMinutes(duracao), new Segmento(origem, destino));
    }

    public boolean segmentoEhValido() {
        return !aeroportoOrigem.equals(aeroportoDestino);
    }
}
