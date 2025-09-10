package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.AtualizarStatusRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.TransformarEmModeloRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EditalNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.Edital;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
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
@RequestMapping("/edital")
public class EditalController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<EditalResponse> salvar(@Valid @RequestBody EditalRequest request) {
        Edital entity = request.convertToEntity(request, modelMapper);
        return new ResponseEntity<>(fachada.salvarEdital(entity), HttpStatus.CREATED);
    }

    @PostMapping("/from-template/{templateId}")
    public ResponseEntity<EditalResponse> criarEditalPorModelo(@PathVariable Long templateId, @Valid @RequestBody EditalRequest request) {
        Edital editalBase = request.convertToEntity(request, modelMapper);
        return new ResponseEntity<>(fachada.criarEditalAPartirDeModelo(templateId, editalBase), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/transformar-em-modelo")
    public ResponseEntity<TipoEditalResponse> transformarEmModelo(@PathVariable Long id, @Valid @RequestBody TransformarEmModeloRequest request) {
        TipoEdital novoModelo = fachada.transformarEditalEmModelo(id, request.getNomeModelo(), request.getDescricaoModelo());
        return new ResponseEntity<>(new TipoEditalResponse(novoModelo, modelMapper), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EditalResponse> editar(@PathVariable Long id, @Valid @RequestBody EditalRequest request) throws EditalNotFoundException {
        Edital entity = request.convertToEntity(request, modelMapper);
        return new ResponseEntity<>(fachada.editarEdital(id, entity), HttpStatus.OK);
    }

    @PatchMapping("/{id}/atualizar-status")
    public ResponseEntity<EditalResponse> atualizarStatus(@PathVariable Long id, @Valid @RequestBody AtualizarStatusRequest request) {
        return new ResponseEntity<>(fachada.atualizarStatusEdital(id, request.getStatusId()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditalResponse> buscar(@PathVariable Long id) throws EditalNotFoundException {
        return new ResponseEntity<>(fachada.buscarPorIdEdital(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<EditalResponse>> listar(@PageableDefault(sort = "id") Pageable pageable) {
        return ResponseEntity.ok(fachada.listarEdital(pageable));
    }

    @GetMapping("/publicados")
    public ResponseEntity<Page<EditalResponse>> listarPublicados(@PageableDefault(sort = "fimIncricao") Pageable pageable) {
        return ResponseEntity.ok(fachada.listarEditaisPublicados(pageable));
    }

    @GetMapping("/{editalId}/etapas")
    public ResponseEntity<List<EtapaResponse>> listarEtapasPorEdital(@PathVariable Long editalId) {
        List<EtapaResponse> response = fachada.listarEtapasPorEdital(editalId).stream()
                .map(etapa -> new EtapaResponse(etapa, modelMapper))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{editalId}/campos")
    public ResponseEntity<List<CampoPersonalizadoResponse>> listarCamposPorEdital(@PathVariable Long editalId) {
        List<CampoPersonalizadoResponse> response = fachada.listarCamposPorEdital(editalId).stream()
                .map(campo -> new CampoPersonalizadoResponse(campo, modelMapper))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws EditalNotFoundException {
        fachada.deletarEdital(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}