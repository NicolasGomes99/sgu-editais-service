package br.edu.ufape.sguEditaisService.comunicacao.controllers;

import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalResponse;
import br.edu.ufape.sguEditaisService.exceptions.notFound.TipoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.fachada.Fachada;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
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
@RequestMapping("/tipo-edital")
public class TipoEditalController {
    private final Fachada fachada;
    private final ModelMapper modelMapper;

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<TipoEditalResponse> salvar(@Valid @RequestBody TipoEditalRequest request) {
        TipoEdital entity = request.convertToEntity(request, modelMapper);
        TipoEdital salvo = fachada.salvarTipoEdital(entity);
        return new ResponseEntity<>(new TipoEditalResponse(salvo, modelMapper), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
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
        Page<TipoEdital> page = fachada.listarTipoEdital().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
        Page<TipoEditalResponse> response = page.map(t -> new TipoEditalResponse(t, modelMapper));
        return ResponseEntity.ok(response);
    }

    //    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws TipoEditalNotFoundException {
        fachada.deletarTipoEdital(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
