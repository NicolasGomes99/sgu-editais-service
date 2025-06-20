package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.CampoPersonalizadoRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.CampoPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
        return campoPersonalizadoRepository.findById(id).orElseThrow(CampoPersonalizadoNotFoundException::new);
    }

    @Override
    public List<CampoPersonalizado> listarCampoPersonalizado(){
        return campoPersonalizadoRepository.findAll();
    }

    @Override
     public CampoPersonalizado editarCampoPersonalizado(Long id, CampoPersonalizado entity) throws CampoPersonalizadoNotFoundException {
        CampoPersonalizado campoPersonalizado = campoPersonalizadoRepository.findById(id).orElseThrow(CampoPersonalizadoNotFoundException::new);
        modelMapper.map(entity, campoPersonalizado);
        return campoPersonalizadoRepository.save(campoPersonalizado);
    }

    @Override
    public void deletarCampoPersonalizado(Long id) throws CampoPersonalizadoNotFoundException{
        CampoPersonalizado campoPersonalizado = campoPersonalizadoRepository.findById(id).orElseThrow(CampoPersonalizadoNotFoundException::new);
        campoPersonalizadoRepository.delete(campoPersonalizado);
    }
}
