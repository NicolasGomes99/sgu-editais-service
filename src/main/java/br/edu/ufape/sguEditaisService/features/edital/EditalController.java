package br.edu.ufape.sguEditaisService.features.edital;

import br.edu.ufape.sguEditaisService.features.edital.dto.EditalResponse;
import br.edu.ufape.sguEditaisService.features.edital.dto.InstanciarEditalRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/editais")
@RequiredArgsConstructor
public class EditalController {

    private final EditalService editalService;

    @PostMapping("/instanciar")
    public ResponseEntity<EditalResponse> instanciarEdital(@RequestBody @Valid InstanciarEditalRequest request) {
        EditalResponse response = editalService.instanciar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditalResponse> buscarPorId(@PathVariable Long id) {
        EditalResponse response = editalService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }
}