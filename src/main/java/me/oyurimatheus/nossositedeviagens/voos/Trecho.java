package me.oyurimatheus.nossositedeviagens.voos;

import me.oyurimatheus.nossositedeviagens.rotas.Rota;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.Duration;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "trechos")
public class Trecho implements Comparable<Trecho> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Rota rota;

    @NotNull
    @ManyToOne
    private Voo voo;

    @Enumerated(STRING)
    private Tipo tipo;

    private Duration tempoDeParada;

    /**
     * @deprecated frameworks eyes only
     */
    @Deprecated
    private Trecho() { }

    public Trecho(@NotNull Rota rota,
                  @NotNull Voo voo,
                  Tipo tipo,
                  Duration tempoDeParada) {
        this.rota = rota;
        this.voo = voo;
        this.tipo = tipo;
        this.tempoDeParada = tempoDeParada;
    }

    @Override
    public int compareTo(Trecho trecho) {
        if (this == trecho || this.equals(trecho) || rota.equals(trecho.rota)) {
            return 0;
        }

        if (rota.destino().equals(trecho.rota.origem())) {
            return -1;
        }

        if (rota.origem().equals(trecho.rota.destino())) {
            return 1;
        }

        throw new IllegalArgumentException("trechos nao possuem ordem logica");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trecho trecho = (Trecho) o;
        return rota.equals(trecho.rota) &&
                voo.equals(trecho.voo) &&
                tipo == trecho.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rota, voo, tipo);
    }
}
