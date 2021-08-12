package me.oyurimatheus.nossositedeviagens.aeroportos;


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
@RequestMapping("/aeroportos")
public class CadastraAeroportoController {

    private final AeroportoRepository aeroportoRepository;
    private final PaisRepository paisRepository;

    CadastraAeroportoController(AeroportoRepository aeroportoRepository,
                                PaisRepository paisRepository) {
        this.aeroportoRepository = aeroportoRepository;
        this.paisRepository = paisRepository;
    }

    @PostMapping
    public ResponseEntity<?> salva(@RequestBody @Valid NovoAeroportoRequest request,
                                   UriComponentsBuilder uriBuilder) {

        var aeroporto = request.paraAeroporto(paisRepository::findById);

        var novoAeroporto = aeroportoRepository.save(aeroporto);

        URI uri = uriBuilder.path("/aeroportos/{id}")
                            .buildAndExpand(novoAeroporto.getId())
                            .toUri();

        return created(uri).build();
    }

    @InitBinder(value = { "novoAeroportoRequest" })
    void initBinder(WebDataBinder binder) {

        binder.addValidators(new CampoUnicoValidator<>("nome",
                "aeroporto.nome.jaCadastrado",
                NovoPaisRequest.class,
                aeroportoRepository::existsByNome));
    }
}
