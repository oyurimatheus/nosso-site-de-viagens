package me.oyurimatheus.nossositedeviagens.aeroportos;

import me.oyurimatheus.nossositedeviagens.paises.Pais;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "aeroportos")
public class Aeroporto {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    // FIXME: nome eh unico por pais, ou eh unico no mundo?
    @NotBlank
    @Column(unique = true)
    private String nome;

    @NotNull
    @ManyToOne
    private Pais pais;

    /**
     * @deprecated hibernate eyes only
     */
    @Deprecated
    private Aeroporto() { }

    public Aeroporto(@NotBlank String nome, @NotNull Pais pais) {
        this.nome = nome;
        this.pais = pais;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        Aeroporto aeroporto = (Aeroporto) o;
        return nome.equals(aeroporto.nome) &&
                pais.equals(aeroporto.pais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, pais);
    }
}
