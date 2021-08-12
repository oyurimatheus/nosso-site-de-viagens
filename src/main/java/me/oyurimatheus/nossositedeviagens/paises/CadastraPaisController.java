package me.oyurimatheus.nossositedeviagens.paises;

import me.oyurimatheus.nossositedeviagens.compartilhado.validacoes.CampoUnicoValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
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

        var pais = request.paraPais();
        var novoPais = repository.save(pais);

        URI uri = uriBuilder.path("/paises/{id}")
                            .buildAndExpand(novoPais.getId())
                            .toUri();

        return created(uri).build();

    }

    @InitBinder(value = { "novoPaisRequest" })
    void initBinder(WebDataBinder binder) {

        binder.addValidators(new CampoUnicoValidator<>("nome",
                        "pais.nome.jaCadastrado",
                        NovoPaisRequest.class,
                        repository::existsByNome));
    }

}
