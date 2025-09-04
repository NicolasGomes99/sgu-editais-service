package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.CampoPersonalizadoRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.CampoPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CampoPersonalizadoService implements br.edu.ufape.sguEditaisService.servicos.interfaces.CampoPersonalizadoService {
    private final CampoPersonalizadoRepository campoPersonalizadoRepository;
    private final ModelMapper modelMapper;

    @Override
    public CampoPersonalizado salvarCampoPersonalizado(CampoPersonalizado entity) {
        return campoPersonalizadoRepository.save(entity);
    }

    @Override
    public CampoPersonalizado buscarPorIdCampoPersonalizado(Long id) throws  CampoPersonalizadoNotFoundException{
        return campoPersonalizadoRepository.findById(id).orElseThrow(()-> new CampoPersonalizadoNotFoundException(id));
    }

    @Override
    public Page<CampoPersonalizado> listarCampoPersonalizado(Pageable pageable){
        return campoPersonalizadoRepository.findAll(pageable);
    }

    @Override
     public CampoPersonalizado editarCampoPersonalizado(Long id, CampoPersonalizado entity) throws CampoPersonalizadoNotFoundException {
        CampoPersonalizado campoPersonalizado = campoPersonalizadoRepository.findById(id).orElseThrow(()-> new CampoPersonalizadoNotFoundException(id));
        modelMapper.map(entity, campoPersonalizado);
        return campoPersonalizadoRepository.save(campoPersonalizado);
    }

    @Override
    public void deletarCampoPersonalizado(Long id) throws CampoPersonalizadoNotFoundException{
        CampoPersonalizado campoPersonalizado = campoPersonalizadoRepository.findById(id).orElseThrow(()-> new CampoPersonalizadoNotFoundException(id));
        campoPersonalizadoRepository.delete(campoPersonalizado);
    }

    @Override
    public List<CampoPersonalizado> listarCamposPorEdital(Long editalId) {
        return campoPersonalizadoRepository.findByEditalIdAndEtapaIdIsNull(editalId);
    }

    @Override
    public List<CampoPersonalizado> listarCamposPorTipoEdital(Long tipoEditalId) {
        return campoPersonalizadoRepository.findByTipoEditalModeloIdAndEtapaIdIsNull(tipoEditalId);
    }

    @Override
    public List<CampoPersonalizado> listarCamposPorEtapa(Long etapaId) {
        return campoPersonalizadoRepository.findByEtapaId(etapaId);
    }

    @Override
    public CampoPersonalizado alternarObrigatoriedade(Long campoId) {
        CampoPersonalizado campo = this.buscarPorIdCampoPersonalizado(campoId);
        campo.setObrigatorio(!campo.isObrigatorio());
        return campoPersonalizadoRepository.save(campo);
    }
}
