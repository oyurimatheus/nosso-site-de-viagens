package me.oyurimatheus.nossositedeviagens.rotas;

import org.springframework.data.repository.Repository;

public interface RotaRepository extends Repository<Rota, Long> {

    Rota save(Rota rota);

    boolean existsByNome(String nome);
}
