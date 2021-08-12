package me.oyurimatheus.nossositedeviagens.rotas;

import me.oyurimatheus.nossositedeviagens.aeroportos.Aeroporto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "rotas")
public class Rota {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String nome;

    @NotNull
    private Duration duracao;

    @Embedded
    @Column(unique = true)
    private Segmento segmento;


    /**
     * @deprecated hibernate eyes only
     */
    @Deprecated
    private Rota() { }


    /**
     *
     * @param nome nome da rota
     * @param duracao em minutos
     * @param segmento aeroporto de origem e de destino
     */
    public Rota(@NotBlank String nome,
                @NotNull Duration duracao,
                Segmento segmento) {
        this.nome = nome;
        this.duracao = duracao;
        this.segmento = segmento;
    }


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Aeroporto origem() {
        return segmento.getOrigem();
    }

    public Aeroporto destino() {
        return segmento.getDestino();
    }

    public static String geraNome(Aeroporto origem, Aeroporto destino) {
        return String.format("%s-%s", origem.getNome(), destino.getNome());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rota rota = (Rota) o;
        return nome.equals(rota.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
