package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.TipoEditalRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.TipoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TipoEditalService implements br.edu.ufape.sguEditaisService.servicos.interfaces.TipoEditalService {
    private final TipoEditalRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public TipoEdital salvarTipoEdital(TipoEdital entity) {
        return repository.save(entity);
    }

    @Override
    public TipoEdital buscarPorIdTipoEdital(Long id) throws TipoEditalNotFoundException {
        return repository.findById(id).orElseThrow(TipoEditalNotFoundException::new);
    }

    @Override
    public List<TipoEdital> listarTipoEdital() {
        return repository.findAll();
    }

    @Override
    public TipoEdital editarTipoEdital(Long id, TipoEdital entity) throws TipoEditalNotFoundException {
        TipoEdital original = buscarPorIdTipoEdital(id);
        modelMapper.map(entity, original);
        return repository.save(original);
    }

    @Override
    public void deletarTipoEdital(Long id) throws TipoEditalNotFoundException {
        TipoEdital entity = buscarPorIdTipoEdital(id);
        repository.delete(entity);
    }
}