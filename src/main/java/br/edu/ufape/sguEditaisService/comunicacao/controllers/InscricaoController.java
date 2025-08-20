package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.InscricaoNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.Inscricao;
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
@RequestMapping("/inscricao")
public class InscricaoController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<InscricaoResponse> salvar(@Valid @RequestBody InscricaoRequest request) {
        Inscricao entity = request.convertToEntity(request, modelMapper);
        Inscricao salvo = fachada.salvarInscricao(entity);
        return new ResponseEntity<>(new InscricaoResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<InscricaoResponse> editar(@PathVariable Long id, @Valid @RequestBody InscricaoRequest request) throws InscricaoNotFoundException {
        Inscricao entity = request.convertToEntity(request, modelMapper);
        Inscricao atualizado = fachada.editarInscricao(id, entity);
        return new ResponseEntity<>(new InscricaoResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscricaoResponse> buscar(@PathVariable Long id) throws InscricaoNotFoundException {
        Inscricao entity = fachada.buscarPorIdInscricao(id);
        return new ResponseEntity<>(new InscricaoResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<InscricaoResponse>> listar(@PageableDefault(sort = "id") Pageable pageable) {
        Page<Inscricao> page = fachada.listarInscricao(pageable);
        Page<InscricaoResponse> response = page.map(i -> new InscricaoResponse(i, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws InscricaoNotFoundException {
        fachada.deletarInscricao(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
