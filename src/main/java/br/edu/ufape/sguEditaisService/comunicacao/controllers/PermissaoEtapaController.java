package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.permissaoEtapa.PermissaoEtapaRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.permissaoEtapa.PermissaoEtapaResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.PermissaoEtapaNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.PermissaoEtapa;
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
@RequestMapping("/permissao-etapa")
public class PermissaoEtapaController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<PermissaoEtapaResponse> salvar(@Valid @RequestBody PermissaoEtapaRequest request) {
        PermissaoEtapa entity = request.convertToEntity(request, modelMapper);
        PermissaoEtapa salvo = fachada.salvarPermissaoEtapa(entity);
        return new ResponseEntity<>(new PermissaoEtapaResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<PermissaoEtapaResponse> editar(@PathVariable Long id, @Valid @RequestBody PermissaoEtapaRequest request) throws PermissaoEtapaNotFoundException {
        PermissaoEtapa entity = request.convertToEntity(request, modelMapper);
        PermissaoEtapa atualizado = fachada.editarPermissaoEtapa(id, entity);
        return new ResponseEntity<>(new PermissaoEtapaResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissaoEtapaResponse> buscar(@PathVariable Long id) throws PermissaoEtapaNotFoundException {
        PermissaoEtapa entity = fachada.buscarPorIdPermissaoEtapa(id);
        return new ResponseEntity<>(new PermissaoEtapaResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<PermissaoEtapaResponse>> listar(@PageableDefault(sort = "id") Pageable pageable) {
        Page<PermissaoEtapa> page = fachada.listarPermissaoEtapa().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
        Page<PermissaoEtapaResponse> response = page.map(p -> new PermissaoEtapaResponse(p, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws PermissaoEtapaNotFoundException {
        fachada.deletarPermissaoEtapa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
