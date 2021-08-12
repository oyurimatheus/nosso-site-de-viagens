package me.oyurimatheus.nossositedeviagens.companhias;

import org.springframework.data.repository.Repository;

public interface CompanhiaRepository extends Repository<Companhia, Long> {

    Companhia save(Companhia companhia);

    boolean existsByNome(String nome);
}
