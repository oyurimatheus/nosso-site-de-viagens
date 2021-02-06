package me.oyurimatheus.nossositedeviagens.paises;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/paises")
class CadastraPaisController {

    private final PaisRepository repository;

    CadastraPaisController(PaisRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> salva(@RequestBody @Valid NovoPaisRequest request,
                                   UriComponentsBuilder uriBuilder) {

        Pais pais = request.paraPais();
        Pais novoPais = repository.save(pais);

        URI uri = uriBuilder.path("/paises/{id}")
                            .buildAndExpand(novoPais.getId())
                            .toUri();

        return created(uri).build();

    }

}
