package me.oyurimatheus.nossositedeviagens.companhias;

import me.oyurimatheus.nossositedeviagens.compartilhado.validacoes.CampoUnicoValidator;
import me.oyurimatheus.nossositedeviagens.paises.NovoPaisRequest;
import me.oyurimatheus.nossositedeviagens.paises.PaisRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/companhias")
class CadastraCompanhiaController {

    private final CompanhiaRepository companhiaRepository;
    private final PaisRepository paisRepository;

    CadastraCompanhiaController(CompanhiaRepository companhiaRepository,
                                PaisRepository paisRepository) {
        this.companhiaRepository = companhiaRepository;
        this.paisRepository = paisRepository;
    }

    @PostMapping
    public ResponseEntity<?> salva(@RequestBody @Valid NovaCompanhiaRequest request,
                                   UriComponentsBuilder uriBuilder) {

        var companhia = request.paraCompanhia(paisRepository::findById);

        var novaCompanhia = companhiaRepository.save(companhia);

        URI uri = uriBuilder.path("/companhias/{id}")
                            .buildAndExpand(novaCompanhia.getId())
                            .toUri();

        return created(uri).build();
    }

    @InitBinder(value = { "novaCompanhiaRequest" })
    void initBinder(WebDataBinder binder) {

        binder.addValidators(new CampoUnicoValidator<>("nome",
                "companhia.nome.jaCadastrado",
                NovoPaisRequest.class,
                companhiaRepository::existsByNome));
    }
}
