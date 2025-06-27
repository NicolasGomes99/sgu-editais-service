package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo.ValorCampoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.valorCampo.ValorCampoResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.ValorCampoNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.ValorCampo;
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
@RequestMapping("/valor-campo")
public class ValorCampoController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<ValorCampoResponse> salvar(@Valid @RequestBody ValorCampoRequest request) {
        ValorCampo entity = request.convertToEntity(request, modelMapper);
        ValorCampo salvo = fachada.salvarValorCampo(entity);
        return new ResponseEntity<>(new ValorCampoResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<ValorCampoResponse> editar(@PathVariable Long id, @Valid @RequestBody ValorCampoRequest request) throws ValorCampoNotFoundException {
        ValorCampo entity = request.convertToEntity(request, modelMapper);
        ValorCampo atualizado = fachada.editarValorCampo(id, entity);
        return new ResponseEntity<>(new ValorCampoResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValorCampoResponse> buscar(@PathVariable Long id) throws ValorCampoNotFoundException {
        ValorCampo entity = fachada.buscarPorIdValorCampo(id);
        return new ResponseEntity<>(new ValorCampoResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ValorCampoResponse>> listar(@PageableDefault(sort = "id") Pageable pageable) {
        Page<ValorCampo> page = fachada.listarValorCampo().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
        Page<ValorCampoResponse> response = page.map(v -> new ValorCampoResponse(v, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws ValorCampoNotFoundException {
        fachada.deletarValorCampo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
