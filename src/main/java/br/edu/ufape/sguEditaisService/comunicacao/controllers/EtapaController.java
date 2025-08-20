package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaReorderRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EtapaNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.Etapa;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/etapa")
public class EtapaController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<EtapaResponse> salvar(@Valid @RequestBody EtapaRequest request) {
        Etapa entity = request.convertToEntity(request, modelMapper);
        Etapa salvo = fachada.salvarEtapa(entity);
        return new ResponseEntity<>(new EtapaResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EtapaResponse> editar(@PathVariable Long id, @Valid @RequestBody EtapaRequest request) throws EtapaNotFoundException {
        Etapa entity = request.convertToEntity(request, modelMapper);
        Etapa atualizado = fachada.editarEtapa(id, entity);
        return new ResponseEntity<>(new EtapaResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @PatchMapping("/reorder")
    public ResponseEntity<Void> reordenarEtapas(@Valid @RequestBody EtapaReorderRequest request) {
        fachada.atualizarOrdemEtapas(request.getIdsEtapasEmOrdem());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EtapaResponse> buscar(@PathVariable Long id) throws EtapaNotFoundException {
        Etapa entity = fachada.buscarPorIdEtapa(id);
        return new ResponseEntity<>(new EtapaResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<EtapaResponse>> listar(@PageableDefault(sort = "id") Pageable pageable) {
        Page<Etapa> page = fachada.listarEtapa(pageable);
        Page<EtapaResponse> response = page.map(e -> new EtapaResponse(e, modelMapper));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{etapaId}/campos")
    public ResponseEntity<List<CampoPersonalizadoResponse>> listarCamposPorEtapa(@PathVariable Long etapaId) {
        List<CampoPersonalizadoResponse> response = fachada.listarCamposPorEtapa(etapaId).stream()
                .map(campo -> new CampoPersonalizadoResponse(campo, modelMapper))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws EtapaNotFoundException {
        fachada.deletarEtapa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}