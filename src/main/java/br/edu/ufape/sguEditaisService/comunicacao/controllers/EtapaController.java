package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EtapaNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.Etapa;
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
@RequestMapping("/etapa")
public class EtapaController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<EtapaResponse> salvar(@Valid @RequestBody EtapaRequest request) {
        Etapa entity = request.convertToEntity(request, modelMapper);
        Etapa salvo = fachada.salvarEtapa(entity);
        return new ResponseEntity<>(new EtapaResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<EtapaResponse> editar(@PathVariable Long id, @Valid @RequestBody EtapaRequest request) throws EtapaNotFoundException {
        Etapa entity = request.convertToEntity(request, modelMapper);
        Etapa atualizado = fachada.editarEtapa(id, entity);
        return new ResponseEntity<>(new EtapaResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtapaResponse> buscar(@PathVariable Long id) throws EtapaNotFoundException {
        Etapa entity = fachada.buscarPorIdEtapa(id);
        return new ResponseEntity<>(new EtapaResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<EtapaResponse>> listar(@PageableDefault(sort = "id") Pageable pageable) {
        Page<Etapa> page = fachada.listarEtapa().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
        Page<EtapaResponse> response = page.map(e -> new EtapaResponse(e, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws EtapaNotFoundException {
        fachada.deletarEtapa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}