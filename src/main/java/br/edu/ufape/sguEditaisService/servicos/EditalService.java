package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.EditalRepository;
import br.edu.ufape.sguEditaisService.dados.EtapaRepository;
import br.edu.ufape.sguEditaisService.dados.TipoEditalRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EditalNotFoundException;
import br.edu.ufape.sguEditaisService.exceptions.notFound.TipoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.*;
import br.edu.ufape.sguEditaisService.servicos.interfaces.StatusPersonalizadoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EditalService implements br.edu.ufape.sguEditaisService.servicos.interfaces.EditalService{
    private final EditalRepository editalRepository;
    private final EtapaRepository etapaRepository;
    private final TipoEditalRepository tipoEditalRepository;
    private final StatusPersonalizadoService statusPersonalizadoService;
    private final ModelMapper modelMapper;

    @Override
    public Edital salvarEdital(Edital entity) {
        return editalRepository.save(entity);
    }

    @Override
    public Edital buscarPorIdEdital(Long id) throws  EditalNotFoundException{
        return editalRepository.findById(id).orElseThrow(() -> new EditalNotFoundException(id));
    }

    @Override
    public Page<Edital> listarEdital(Pageable pageable) {
        return editalRepository.findAll(pageable);
    }

    @Override
    public Edital editarEdital(Long id, Edital entity) throws EditalNotFoundException {
        Edital edital = editalRepository.findById(id).orElseThrow(() -> new EditalNotFoundException(id));
        modelMapper.map(entity, edital);
        return editalRepository.save(edital);
    }

    @Override
    public void deletarEdital(Long id) throws EditalNotFoundException {
        Edital edital = editalRepository.findById(id).orElseThrow(() -> new EditalNotFoundException(id));
        editalRepository.delete(edital);
    }

    @Override
    @Transactional
    public Edital criarEditalAPartirDeModelo(Long templateId, Edital editalBase) {
        TipoEdital modelo = tipoEditalRepository.findById(templateId)
                .orElseThrow(() -> new TipoEditalNotFoundException(templateId));

        editalBase.setTipoEdital(modelo);
        editalBase.setCamposPersonalizados(new ArrayList<>());
        editalBase.setDatasEtapas(new ArrayList<>());

        Edital novoEdital = editalRepository.save(editalBase);

        if (modelo.getEtapasModelo() != null) {
            List<Etapa> novasEtapas = modelo.getEtapasModelo().stream().map(etapaModelo -> {
                Etapa novaEtapa = new Etapa();
                modelMapper.map(etapaModelo, novaEtapa);
                novaEtapa.setId(null);
                novaEtapa.setEdital(novoEdital);
                novaEtapa.setTipoEditalModelo(null);

                if(etapaModelo.getCamposPersonalizados() != null){
                    novaEtapa.setCamposPersonalizados(etapaModelo.getCamposPersonalizados().stream().map(campoModelo -> {
                        CampoPersonalizado novoCampo = new CampoPersonalizado();
                        modelMapper.map(campoModelo, novoCampo);
                        novoCampo.setId(null);
                        novoCampo.setEtapa(novaEtapa);
                        return novoCampo;
                    }).collect(Collectors.toList()));
                }
                return novaEtapa;
            }).collect(Collectors.toList());

            etapaRepository.saveAll(novasEtapas);
        }

        if (modelo.getCamposPersonalizadosModelo() != null) {
            novoEdital.setCamposPersonalizados(modelo.getCamposPersonalizadosModelo().stream().map(campoModelo -> {
                CampoPersonalizado novoCampo = new CampoPersonalizado();
                modelMapper.map(campoModelo, novoCampo);
                novoCampo.setId(null);
                novoCampo.setEdital(novoEdital);
                return novoCampo;
            }).collect(Collectors.toList()));
        }

        return editalRepository.save(novoEdital);
    }

    @Override
    @Transactional
    public TipoEdital transformarEditalEmModelo(Long editalId, String nomeModelo, String descricaoModelo) {
        Edital editalOriginal = this.buscarPorIdEdital(editalId);

        TipoEdital novoModelo = new TipoEdital();
        novoModelo.setNome(nomeModelo);
        novoModelo.setDescricao(descricaoModelo);

        TipoEdital modeloSalvo = tipoEditalRepository.save(novoModelo);

        if (editalOriginal.getDatasEtapas() != null && !editalOriginal.getDatasEtapas().isEmpty()) {
            modeloSalvo.setEtapasModelo(editalOriginal.getDatasEtapas().stream()
                    .map(DataEtapa::getEtapa)
                    .map(etapaOriginal -> {
                        Etapa novaEtapaModelo = new Etapa();
                        modelMapper.map(etapaOriginal, novaEtapaModelo);
                        novaEtapaModelo.setId(null);
                        novaEtapaModelo.setEdital(null);
                        novaEtapaModelo.setTipoEditalModelo(modeloSalvo);

                        if(etapaOriginal.getCamposPersonalizados() != null){
                            novaEtapaModelo.setCamposPersonalizados(etapaOriginal.getCamposPersonalizados().stream().map(campoOriginal -> {
                                CampoPersonalizado novoCampoModelo = new CampoPersonalizado();
                                modelMapper.map(campoOriginal, novoCampoModelo);
                                novoCampoModelo.setId(null);
                                novoCampoModelo.setEtapa(novaEtapaModelo);
                                return novoCampoModelo;
                            }).collect(Collectors.toList()));
                        }
                        return novaEtapaModelo;
                    }).collect(Collectors.toList()));
        }

        if (editalOriginal.getCamposPersonalizados() != null) {
            modeloSalvo.setCamposPersonalizadosModelo(editalOriginal.getCamposPersonalizados().stream().map(campoOriginal -> {
                CampoPersonalizado novoCampoModelo = new CampoPersonalizado();
                modelMapper.map(campoOriginal, novoCampoModelo);
                novoCampoModelo.setId(null);
                novoCampoModelo.setEdital(null);
                novoCampoModelo.setTipoEditalModelo(modeloSalvo);
                return novoCampoModelo;
            }).collect(Collectors.toList()));
        }

        return tipoEditalRepository.save(modeloSalvo);
    }

    @Override
    public Page<Edital> listarEditaisPublicados(Pageable pageable) {
        return editalRepository.findByInscricoesAbertas(LocalDateTime.now(), pageable);
    }

    @Override
    @Transactional
    public Edital atualizarStatusEdital(Long editalId, Long novoStatusId) {
        Edital edital = this.buscarPorIdEdital(editalId);
        StatusPersonalizado novoStatus = statusPersonalizadoService.buscarPorIdStatusPersonalizado(novoStatusId);

        if(novoStatus.getTipoStatus() != br.edu.ufape.sguEditaisService.models.enums.TipoStatus.EDITAL){
            throw new IllegalArgumentException("O status fornecido não é aplicável a um Edital.");
        }

        edital.setStatusAtual(novoStatus);
        return editalRepository.save(edital);
    }
}
