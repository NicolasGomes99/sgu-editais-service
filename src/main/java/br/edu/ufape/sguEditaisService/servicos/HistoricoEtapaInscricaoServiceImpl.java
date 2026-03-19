package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.HistoricoEtapaInscricaoRepository;
import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import br.edu.ufape.sguEditaisService.servicos.interfaces.HistoricoEtapaInscricaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoEtapaInscricaoServiceImpl implements HistoricoEtapaInscricaoService {

    private final HistoricoEtapaInscricaoRepository repository;

    @Override
    public HistoricoEtapaInscricao salvar(HistoricoEtapaInscricao historico) {
        return repository.save(historico);
    }

    @Override
    public List<HistoricoEtapaInscricao> listarPorInscricao(Long inscricaoId) {
        return repository.findByInscricaoIdOrderByDataMudancaDesc(inscricaoId);
    }
}