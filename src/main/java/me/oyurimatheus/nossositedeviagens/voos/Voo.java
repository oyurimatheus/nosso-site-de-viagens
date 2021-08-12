package me.oyurimatheus.nossositedeviagens.voos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import java.util.TreeSet;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "voos")
class Voo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Positive
    @Min(1)
    private int lugaresDisponiveis;
}
