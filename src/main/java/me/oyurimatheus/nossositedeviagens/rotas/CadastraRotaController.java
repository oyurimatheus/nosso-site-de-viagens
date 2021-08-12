package me.oyurimatheus.nossositedeviagens.rotas;

import me.oyurimatheus.nossositedeviagens.aeroportos.AeroportoRepository;
import me.oyurimatheus.nossositedeviagens.compartilhado.validacoes.CampoUnicoValidator;
import me.oyurimatheus.nossositedeviagens.paises.NovoPaisRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/rotas")
class CadastraRotaController {

    private final RotaRepository rotaRepository;
    private final AeroportoRepository aeroportoRepository;


    CadastraRotaController(RotaRepository rotaRepository,
                           AeroportoRepository aeroportoRepository) {
        this.rotaRepository = rotaRepository;
        this.aeroportoRepository = aeroportoRepository;
    }

    @PostMapping
    public ResponseEntity<?> salva(@RequestBody @Valid NovaRotaRequest request,
                                   UriComponentsBuilder uriBuilder) {

        var rota = request.paraRota(aeroportoRepository::findById);
        if (rotaRepository.existsByNome(rota.getNome())) {
            HashMap<String, Object> resposta = new HashMap<>();

            resposta.put("message", "rota ja cadastrada");
            return badRequest().body(resposta);
        }

        var novaRota = rotaRepository.save(rota);

        URI uri = uriBuilder.path("/rotas/{id}")
                .buildAndExpand(novaRota.getId())
                .toUri();

        return created(uri).build();
    }

    @InitBinder(value = { "novaRotaRequest" })
    void initBinder(WebDataBinder binder) {

        binder.addValidators(new CampoUnicoValidator<>("nome",
                "rota.nome.jaCadastrada",
                NovoPaisRequest.class,
                rotaRepository::existsByNome),
                new SegmentoValidator());
    }
}
