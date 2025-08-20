package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.TipoEditalRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.TipoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import br.edu.ufape.sguEditaisService.models.Etapa;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

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
        return repository.findById(id).orElseThrow(() -> new TipoEditalNotFoundException(id));
    }

    @Override
    public Page<TipoEdital> listarTipoEdital(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public TipoEdital editarTipoEdital(Long id, TipoEdital entity) throws TipoEditalNotFoundException {
        TipoEdital original = repository.findById(id).orElseThrow(() -> new TipoEditalNotFoundException(id));
        modelMapper.map(entity, original);
        return repository.save(original);
    }

    @Override
    public void deletarTipoEdital(Long id) throws TipoEditalNotFoundException {
        TipoEdital entity = repository.findById(id).orElseThrow(() -> new TipoEditalNotFoundException(id));
        repository.delete(entity);
    }

    @Override
    @Transactional
    public TipoEdital duplicarTipoEdital(Long id) {
        TipoEdital original = this.buscarPorIdTipoEdital(id);

        TipoEdital copia = new TipoEdital();
        modelMapper.map(original, copia);
        copia.setId(null);
        copia.setNome(original.getNome() + " (Cópia)");

        TipoEdital modeloSalvo = repository.save(copia);

        if (original.getEtapasModelo() != null) {
            modeloSalvo.setEtapasModelo(original.getEtapasModelo().stream().map(etapaModelo -> {
                Etapa novaEtapa = new Etapa();
                modelMapper.map(etapaModelo, novaEtapa);
                novaEtapa.setId(null);
                novaEtapa.setTipoEditalModelo(modeloSalvo);

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
            }).collect(Collectors.toList()));
        }

        if (original.getCamposPersonalizadosModelo() != null) {
            modeloSalvo.setCamposPersonalizadosModelo(original.getCamposPersonalizadosModelo().stream().map(campoModelo -> {
                CampoPersonalizado novoCampo = new CampoPersonalizado();
                modelMapper.map(campoModelo, novoCampo);
                novoCampo.setId(null);
                novoCampo.setTipoEditalModelo(modeloSalvo);
                return novoCampo;
            }).collect(Collectors.toList()));
        }

        return repository.save(modeloSalvo);
    }
}