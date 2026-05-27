package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.EtapaRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EtapaNotFoundException;
import br.edu.ufape.sguEditaisService.models.Etapa;
import br.edu.ufape.sguEditaisService.servicos.interfaces.EtapaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EtapaServiceImpl implements EtapaService {

    private final EtapaRepository repository;

    @Override
    public Etapa salvar(Etapa etapa) {
        return repository.save(etapa);
    }

    @Override
    public Etapa buscar(Long id) throws EtapaNotFoundException {
        return repository.findById(id).orElseThrow(() -> new EtapaNotFoundException(id));
    }

    @Override
    public List<Etapa> listarPorTipoEdital(Long tipoEditalId) {
        return repository.findByTipoEditalIdOrderByOrdemAsc(tipoEditalId);
    }

    @Override
    public Etapa editar(Long id, Etapa etapa) throws EtapaNotFoundException {
        Etapa existente = buscar(id);
        existente.setNome(etapa.getNome());
        existente.setDescricao(etapa.getDescricao());
        existente.setOrdem(etapa.getOrdem());
        // Não editamos o TipoEdital aqui, isso é imutável para a etapa!
        return repository.save(existente);
    }

    @Override
    public void deletar(Long id) throws EtapaNotFoundException {
        repository.delete(buscar(id));
    }
}