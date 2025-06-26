package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.documento.DocumentoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.documento.DocumentoResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.Documento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documento")
public class DocumentoController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<DocumentoResponse> salvar(@Valid @RequestBody DocumentoRequest request) {
        Documento entity = request.convertToEntity(request, modelMapper);
        Documento salvo = fachada.salvarDocumento(entity);
        return new ResponseEntity<>(new DocumentoResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<DocumentoResponse> editar(@PathVariable Long id, @Valid @RequestBody DocumentoRequest request) throws DocumentoNotFoundException {
        Documento entity = request.convertToEntity(request, modelMapper);
        Documento atualizado = fachada.editarDocumento(id, entity);
        return new ResponseEntity<>(new DocumentoResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoResponse> buscar(@PathVariable Long id) throws DocumentoNotFoundException {
        Documento entity = fachada.buscarPorIdDocumento(id);
        return new ResponseEntity<>(new DocumentoResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<DocumentoResponse>> listar(@PageableDefault(sort = "id") Pageable pageable) {
        Page<Documento> page = fachada.listarDocumento().stream()
                .collect(java.util.stream.Collectors.collectingAndThen(
                        java.util.stream.Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
        Page<DocumentoResponse> response = page.map(d -> new DocumentoResponse(d, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws DocumentoNotFoundException {
        fachada.deletarDocumento(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}