package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.TipoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
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
@RequestMapping("/tipo-edital")
public class TipoEditalController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<TipoEditalResponse> salvar(@Valid @RequestBody TipoEditalRequest request) {
        TipoEdital entity = request.convertToEntity(request, modelMapper);
        TipoEdital salvo = fachada.salvarTipoEdital(entity);
        return new ResponseEntity<>(new TipoEditalResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/duplicar")
    public ResponseEntity<TipoEditalResponse> duplicarModelo(@PathVariable Long id) {
        TipoEdital novoModelo = fachada.duplicarTipoEdital(id);
        return new ResponseEntity<>(new TipoEditalResponse(novoModelo, modelMapper), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TipoEditalResponse> editar(@PathVariable Long id, @Valid @RequestBody TipoEditalRequest request) throws TipoEditalNotFoundException {
        TipoEdital entity = request.convertToEntity(request, modelMapper);
        TipoEdital atualizado = fachada.editarTipoEdital(id, entity);
        return new ResponseEntity<>(new TipoEditalResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEditalResponse> buscar(@PathVariable Long id) throws TipoEditalNotFoundException {
        TipoEdital entity = fachada.buscarPorIdTipoEdital(id);
        return new ResponseEntity<>(new TipoEditalResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<TipoEditalResponse>> listar(@PageableDefault(sort = "id") Pageable pageable) {
        Page<TipoEdital> page = fachada.listarTipoEdital(pageable);
        Page<TipoEditalResponse> response = page.map(t -> new TipoEditalResponse(t, modelMapper));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{tipoEditalId}/etapas")
    public ResponseEntity<List<EtapaResponse>> listarEtapasPorTipoEdital(@PathVariable Long tipoEditalId) {
        List<EtapaResponse> response = fachada.listarEtapasPorTipoEdital(tipoEditalId).stream()
                .map(etapa -> new EtapaResponse(etapa, modelMapper))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{tipoEditalId}/campos")
    public ResponseEntity<List<CampoPersonalizadoResponse>> listarCamposPorTipoEdital(@PathVariable Long tipoEditalId) {
        List<CampoPersonalizadoResponse> response = fachada.listarCamposPorTipoEdital(tipoEditalId).stream()
                .map(campo -> new CampoPersonalizadoResponse(campo, modelMapper))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws TipoEditalNotFoundException {
        fachada.deletarTipoEdital(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}