package me.oyurimatheus.nossositedeviagens.paises;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PaisRepository extends Repository<Pais, Long> {

    Pais save(Pais pais);

    Optional<Pais> findById(Long id);

    boolean existsByNome(String nome);
}
