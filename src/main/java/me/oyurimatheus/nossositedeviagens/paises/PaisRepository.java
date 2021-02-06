package me.oyurimatheus.nossositedeviagens.paises;

import org.springframework.data.repository.Repository;

interface PaisRepository extends Repository<Pais, Long> {

    Pais save(Pais pais);
}
