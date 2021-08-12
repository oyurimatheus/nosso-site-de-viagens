package me.oyurimatheus.nossositedeviagens.companhias;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import me.oyurimatheus.nossositedeviagens.paises.Pais;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "companhias")
class Companhia {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Pais pais;

    private LocalDateTime criadoEm = now();

    /**
     * @deprecated hibernate eyes only
     */
    @Deprecated
    private Companhia() { }

    public Companhia(String nome, Pais pais) {
        this.nome = nome;
        this.pais = pais;
    }

    public Long getId() {
        return id;
    }
}
