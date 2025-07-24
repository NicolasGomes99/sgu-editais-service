package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.statusPersonalizado.StatusPersonalizadoResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.StatusPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("status-personalizado")
@RequiredArgsConstructor
public class StatusPersonalizadoController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<StatusPersonalizadoResponse> salvar(@Valid @RequestBody StatusPersonalizadoRequest request) {
        StatusPersonalizado statusPersonalizado = request.convertToEntity(request, modelMapper);
        StatusPersonalizado salvo = fachada.salvarStatusPersonalizado(statusPersonalizado);
        return new ResponseEntity<>(new StatusPersonalizadoResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusPersonalizadoResponse> buscarPorId(@PathVariable Long id) throws StatusPersonalizadoNotFoundException {
        StatusPersonalizado statusPersonalizado = fachada.buscarPorIdStatusPersonalizado(id);
        return ResponseEntity.ok(new StatusPersonalizadoResponse(statusPersonalizado, modelMapper));
    }

    @GetMapping
    public ResponseEntity<List<StatusPersonalizadoResponse>> listar() {
        List<StatusPersonalizado> statusPersonalizados = fachada.listarStatusPersonalizado();
        List<StatusPersonalizadoResponse> responses = statusPersonalizados.stream()
                .map(status -> new StatusPersonalizadoResponse(status, modelMapper))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StatusPersonalizadoResponse> editar(@PathVariable Long id, @Valid @RequestBody StatusPersonalizadoRequest request) throws StatusPersonalizadoNotFoundException {
        StatusPersonalizado entity = request.convertToEntity(request, modelMapper);
        StatusPersonalizado atualizado = fachada.editarStatusPersonalizado(id, entity);
        return ResponseEntity.ok(new StatusPersonalizadoResponse(atualizado, modelMapper));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws StatusPersonalizadoNotFoundException {
        fachada.deletarStatusPersonalizado(id);
        return ResponseEntity.noContent().build();
    }
}