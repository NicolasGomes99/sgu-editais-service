package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.EditalRepository;
import br.edu.ufape.sguEditaisService.dados.EtapaRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EditalNotFoundException;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EtapaNotFoundException;
import br.edu.ufape.sguEditaisService.models.Edital;
import br.edu.ufape.sguEditaisService.models.Etapa;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EtapaService implements br.edu.ufape.sguEditaisService.servicos.interfaces.EtapaService {
    private final EtapaRepository etapaRepository;
    private final EditalRepository editalRepository;
    private final ModelMapper modelMapper;

    private void verificarSeEditalEstaCongelado(Etapa etapa) {
        if (etapa.getEdital() != null && etapa.getEdital().getId() != null) {
            Edital edital = editalRepository.findById(etapa.getEdital().getId())
                    .orElseThrow(() -> new EditalNotFoundException(etapa.getEdital().getId()));

            if (edital.getInicioInscricao() != null && LocalDateTime.now().isAfter(edital.getInicioInscricao())) {
                throw new IllegalStateException("Não é possível modificar as etapas de um edital com inscrições já iniciadas.");
            }
        }
    }

    @Override
    public Etapa salvarEtapa(Etapa entity) {
        verificarSeEditalEstaCongelado(entity);
        return etapaRepository.save(entity);
    }

    @Override
    public Etapa buscarPorIdEtapa(Long id) throws EtapaNotFoundException {
        return etapaRepository.findById(id).orElseThrow(() -> new EtapaNotFoundException(id));
    }

    @Override
    public Page<Etapa> listarEtapa(Pageable pageable) {
        return etapaRepository.findAll(pageable);
    }

    @Override
    public Etapa editarEtapa(Long id, Etapa entity) throws EtapaNotFoundException {
        Etapa etapa = etapaRepository.findById(id).orElseThrow(() -> new EtapaNotFoundException(id));
        verificarSeEditalEstaCongelado(etapa);
        modelMapper.map(entity, etapa);
        return etapaRepository.save(etapa);
    }

    @Override
    public void deletarEtapa(Long id) throws EtapaNotFoundException{
        Etapa etapa = etapaRepository.findById(id).orElseThrow(() -> new EtapaNotFoundException(id));
        verificarSeEditalEstaCongelado(etapa);
        etapaRepository.delete(etapa);
    }

    @Override
    public List<Etapa> listarEtapasPorEdital(Long editalId) {
        return etapaRepository.findByEditalIdOrderByOrdemAsc(editalId);
    }

    @Override
    public List<Etapa> listarEtapasPorTipoEdital(Long tipoEditalId) {
        return etapaRepository.findByTipoEditalModeloIdOrderByOrdemAsc(tipoEditalId);
    }

    @Override
    @Transactional
    public void atualizarOrdemEtapas(List<Long> idsEtapasEmOrdem) {
        if (idsEtapasEmOrdem == null || idsEtapasEmOrdem.isEmpty()) {
            return;
        }

        List<Etapa> etapas = etapaRepository.findAllById(idsEtapasEmOrdem);
        if (etapas.size() != idsEtapasEmOrdem.size()) {
            throw new EtapaNotFoundException("Uma ou mais etapas na lista de reordenação não foram encontradas.");
        }

        long editalPaiId = -1;
        long tipoEditalPaiId = -1;
        boolean isEditalContext = etapas.getFirst().getEdital() != null;

        if(isEditalContext) {
            editalPaiId = etapas.getFirst().getEdital().getId();
        } else {
            tipoEditalPaiId = etapas.getFirst().getTipoEditalModelo().getId();
        }

        for (Etapa etapa : etapas) {
            verificarSeEditalEstaCongelado(etapa);
            boolean mesmoEdital = isEditalContext && etapa.getEdital() != null && etapa.getEdital().getId() == editalPaiId;
            boolean mesmoModelo = !isEditalContext && etapa.getTipoEditalModelo() != null && etapa.getTipoEditalModelo().getId() == tipoEditalPaiId;
            if(!mesmoEdital && !mesmoModelo){
                throw new IllegalArgumentException("Todas as etapas a serem reordenadas devem pertencer ao mesmo Edital ou ao mesmo Modelo.");
            }
        }

        for (int i = 0; i < idsEtapasEmOrdem.size(); i++) {
            Long etapaId = idsEtapasEmOrdem.get(i);
            int novaOrdem = i;
            // A busca pela stream garante que estamos usando a entidade correta da lista já carregada
            Etapa etapaParaAtualizar = etapas.stream().filter(e -> e.getId().equals(etapaId)).findFirst().get();
            etapaParaAtualizar.setOrdem(novaOrdem);
            etapaRepository.save(etapaParaAtualizar);
        }
    }

    @Override
    public Optional<Etapa> buscarProximaEtapa(Long editalId, int ordemAtual) {
        // Busca todas as etapas do edital ordenadas
        List<Etapa> etapas = etapaRepository.findByEditalIdOrderByOrdemAsc(editalId);

        // Filtra as que tem ordem maior que a atual e pega a primeira
        return etapas.stream()
                .filter(e -> e.getOrdem() > ordemAtual)
                .findFirst();
    }

}