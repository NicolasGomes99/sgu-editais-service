package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EditalNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.Edital;
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
@RequestMapping("/edital")
public class EditalController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<EditalResponse> salvar(@Valid @RequestBody EditalRequest request) {
        Edital entity = request.convertToEntity(request, modelMapper);
        Edital salvo = fachada.salvarEdital(entity);
        return new ResponseEntity<>(new EditalResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<EditalResponse> editar(@PathVariable Long id, @Valid @RequestBody EditalRequest request) throws EditalNotFoundException {
        Edital entity = request.convertToEntity(request, modelMapper);
        Edital atualizado = fachada.editarEdital(id, entity);
        return new ResponseEntity<>(new EditalResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditalResponse> buscar(@PathVariable Long id) throws EditalNotFoundException {
        Edital entity = fachada.buscarPorIdEdital(id);
        return new ResponseEntity<>(new EditalResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<EditalResponse>> listar(@PageableDefault(sort = "id") Pageable pageable) {
        Page<Edital> page = fachada.listarEdital().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
        Page<EditalResponse> response = page.map(e -> new EditalResponse(e, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws EditalNotFoundException {
        fachada.deletarEdital(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}