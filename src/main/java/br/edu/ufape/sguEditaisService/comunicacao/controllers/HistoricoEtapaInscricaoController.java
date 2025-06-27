package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao.HistoricoEtapaInscricaoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.historicoEtapaInscricao.HistoricoEtapaInscricaoResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.HistoricoEtapaInscricaoNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/historico-etapa-inscricao")
public class HistoricoEtapaInscricaoController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<HistoricoEtapaInscricaoResponse> salvar(@Valid @RequestBody HistoricoEtapaInscricaoRequest request) {
        HistoricoEtapaInscricao entity = request.convertToEntity(request, modelMapper);
        HistoricoEtapaInscricao salvo = fachada.salvarHistoricoEtapaInscricao(entity);
        return new ResponseEntity<>(new HistoricoEtapaInscricaoResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<HistoricoEtapaInscricaoResponse> editar(@PathVariable Long id, @Valid @RequestBody HistoricoEtapaInscricaoRequest request) throws HistoricoEtapaInscricaoNotFoundException {
        HistoricoEtapaInscricao entity = request.convertToEntity(request, modelMapper);
        HistoricoEtapaInscricao atualizado = fachada.editarHistoricoEtapaInscricao(id, entity);
        return new ResponseEntity<>(new HistoricoEtapaInscricaoResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoEtapaInscricaoResponse> buscar(@PathVariable Long id) throws HistoricoEtapaInscricaoNotFoundException {
        HistoricoEtapaInscricao entity = fachada.buscarPorIdHistoricoEtapaInscricao(id);
        return new ResponseEntity<>(new HistoricoEtapaInscricaoResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<HistoricoEtapaInscricaoResponse>> listar(@PageableDefault(sort = "id") Pageable pageable) {
        Page<HistoricoEtapaInscricao> page = fachada.listarHistoricoEtapaInscricao().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
        Page<HistoricoEtapaInscricaoResponse> response = page.map(h -> new HistoricoEtapaInscricaoResponse(h, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws HistoricoEtapaInscricaoNotFoundException {
        fachada.deletarHistoricoEtapaInscricao(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}