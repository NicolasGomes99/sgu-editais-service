package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.documentoEdital.DocumentoEditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.documentoEdital.DocumentoEditalResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.DocumentoEdital;
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
@RequestMapping("/documento-edital")
public class DocumentoEditalController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<DocumentoEditalResponse> salvar(@Valid @RequestBody DocumentoEditalRequest request) {
        DocumentoEdital entity = request.convertToEntity(request, modelMapper);
        DocumentoEdital salvo = fachada.salvarDocumentoEdital(entity);
        return new ResponseEntity<>(new DocumentoEditalResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<DocumentoEditalResponse> editar(@PathVariable Long id, @Valid @RequestBody DocumentoEditalRequest request) throws DocumentoEditalNotFoundException {
        DocumentoEdital entity = request.convertToEntity(request, modelMapper);
        DocumentoEdital atualizado = fachada.editarDocumentoEdital(id, entity);
        return new ResponseEntity<>(new DocumentoEditalResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoEditalResponse> buscar(@PathVariable Long id) throws DocumentoEditalNotFoundException {
        DocumentoEdital entity = fachada.buscarPorIdDocumentoEdital(id);
        return new ResponseEntity<>(new DocumentoEditalResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<DocumentoEditalResponse>> listar(@PageableDefault(sort = "id") Pageable pageable) {
        Page<DocumentoEdital> page = fachada.listarDocumentoEdital(pageable);
        Page<DocumentoEditalResponse> response = page.map(d -> new DocumentoEditalResponse(d, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws DocumentoEditalNotFoundException {
        fachada.deletarDocumentoEdital(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
