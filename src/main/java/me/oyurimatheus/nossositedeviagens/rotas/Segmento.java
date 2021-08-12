package me.oyurimatheus.nossositedeviagens.rotas;

import me.oyurimatheus.nossositedeviagens.aeroportos.Aeroporto;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.Assert.state;

@Embeddable
class Segmento {

    @NotNull
    private Aeroporto origem;

    @NotNull
    private Aeroporto destino;

    /**
     * @deprecated hibernate eyes only
     */
    @Deprecated
    private Segmento() { }

    public Segmento(@NotNull Aeroporto origem,
                    @NotNull Aeroporto destino) {

        notNull(origem, "origem nao pode ser nula");
        notNull(destino, "destino nao pode ser nulo");
        state(!origem.equals(destino), "origem nao pode ser igual destino");

        this.origem = origem;
        this.destino = destino;
    }

    public Aeroporto getOrigem() {
        return origem;
    }

    public Aeroporto getDestino() {
        return destino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segmento segmento = (Segmento) o;
        return origem.equals(segmento.origem) &&
                destino.equals(segmento.destino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origem, destino);
    }
}
