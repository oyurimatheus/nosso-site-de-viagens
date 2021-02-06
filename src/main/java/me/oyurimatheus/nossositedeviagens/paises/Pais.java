package me.oyurimatheus.nossositedeviagens.paises;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "paises")
class Pais {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String nome;

    /**
     * @deprecated hibernate eyes only
     */
    @Deprecated
    private Pais() { }

    public Pais(@NotBlank String nome) {
        Assert.hasText(nome, "Nome do pais nao pode ser em branco");

        this.nome = nome;
    }

    public Long getId() {
        return id;
    }
}
