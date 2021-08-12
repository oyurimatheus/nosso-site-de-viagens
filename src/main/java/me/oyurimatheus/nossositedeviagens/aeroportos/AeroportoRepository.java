package me.oyurimatheus.nossositedeviagens.aeroportos;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AeroportoRepository extends Repository<Aeroporto, Long> {

    Aeroporto save(Aeroporto aeroporto);

    Optional<Aeroporto> findById(Long id);

    boolean existsByNome(String nome);
}
