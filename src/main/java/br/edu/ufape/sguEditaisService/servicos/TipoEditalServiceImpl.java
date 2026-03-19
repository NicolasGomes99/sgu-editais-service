package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.TipoEditalRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.TipoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
import br.edu.ufape.sguEditaisService.servicos.interfaces.TipoEditalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoEditalServiceImpl implements TipoEditalService {

    // APENAS o seu próprio repositório injetado. Acoplamento zero!
    private final TipoEditalRepository repository;

    @Override
    public TipoEdital salvar(TipoEdital tipoEdital) {
        return repository.save(tipoEdital);
    }

    @Override
    public TipoEdital buscar(Long id) throws TipoEditalNotFoundException {
        return repository.findById(id).orElseThrow(() -> new TipoEditalNotFoundException(id));
    }

    @Override
    public List<TipoEdital> listar() {
        return repository.findAll();
    }

    @Override
    public TipoEdital editar(Long id, TipoEdital tipoEdital) throws TipoEditalNotFoundException {
        TipoEdital existente = buscar(id);
        existente.setNome(tipoEdital.getNome());
        existente.setDescricao(tipoEdital.getDescricao());
        return repository.save(existente);
    }

    @Override
    public void deletar(Long id) throws TipoEditalNotFoundException {
        TipoEdital existente = buscar(id);
        repository.delete(existente);
    }
}