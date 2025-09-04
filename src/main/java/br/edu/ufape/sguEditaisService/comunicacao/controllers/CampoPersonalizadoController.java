package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.CampoPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/campo-personalizado")
public class CampoPersonalizadoController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<CampoPersonalizadoResponse> salvar(@Valid @RequestBody CampoPersonalizadoRequest request) {
        CampoPersonalizado entity = request.convertToEntity(request, modelMapper);
        CampoPersonalizado salvo = fachada.salvarCampoPersonalizado(entity);
        return new ResponseEntity<>(new CampoPersonalizadoResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CampoPersonalizadoResponse> editar(@PathVariable Long id, @Valid @RequestBody CampoPersonalizadoRequest request) throws CampoPersonalizadoNotFoundException {
        CampoPersonalizado entity = request.convertToEntity(request, modelMapper);
        CampoPersonalizado atualizado = fachada.editarCampoPersonalizado(id, entity);
        return new ResponseEntity<>(new CampoPersonalizadoResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @PatchMapping("/{id}/toggle-obrigatorio")
    public ResponseEntity<CampoPersonalizadoResponse> toggleObrigatorio(@PathVariable Long id) {
        CampoPersonalizado atualizado = fachada.alternarObrigatoriedade(id);
        return new ResponseEntity<>(new CampoPersonalizadoResponse(atualizado, modelMapper), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampoPersonalizadoResponse> buscar(@PathVariable Long id) throws CampoPersonalizadoNotFoundException {
        CampoPersonalizado entity = fachada.buscarPorIdCampoPersonalizado(id);
        return new ResponseEntity<>(new CampoPersonalizadoResponse(entity, modelMapper), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CampoPersonalizadoResponse>> listar(@PageableDefault(sort = "id") Pageable pageable) {
        Page<CampoPersonalizado> page = fachada.listarCampoPersonalizado(pageable);
        Page<CampoPersonalizadoResponse> response = page.map(c -> new CampoPersonalizadoResponse(c, modelMapper));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws CampoPersonalizadoNotFoundException {
        fachada.deletarCampoPersonalizado(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}