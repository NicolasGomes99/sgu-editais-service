package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.CampoPersonalizadoRepository;
import br.edu.ufape.sguEditaisService.exceptions.CampoPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CampoPersonalizadoService implements br.edu.ufape.sguEditaisService.servicos.interfaces.CampoPersonalizadoService {
    private final CampoPersonalizadoRepository repository;

    @Override
    public CampoPersonalizado salvar(CampoPersonalizado entity) {
        return repository.save(entity);
    }

    @Override
    public CampoPersonalizado buscar(Long id) throws  CampoPersonalizadoNotFoundException{
        return repository.findById(id).orElseThrow(CampoPersonalizadoNotFoundException::new);
    }

    @Override
    public List<CampoPersonalizado> listar(){
        return repository.findAll();
    }

    @Override
    public CampoPersonalizado editar(Long id, CampoPersonalizado entity) throws CampoPersonalizadoNotFoundException{
       CampoPersonalizado campoExistente = buscar(id);
    campoExistente.setNome(entity.getNome());
    campoExistente.setRotulo(entity.getRotulo());
    campoExistente.setObrigatorio(entity.isObrigatorio());
    campoExistente.setTipoCampo(entity.getTipoCampo());
    campoExistente.setOpcoes(entity.getOpcoes());
    campoExistente.setEtapa(entity.getEtapa());
    campoExistente.setEdital(entity.getEdital());
    return salvar(campoExistente);
    }

    @Override
    public void deletar(Long id) throws CampoPersonalizadoNotFoundException{
        CampoPersonalizado campoExistente = repository.findById(id).orElseThrow(CampoPersonalizadoNotFoundException::new);
        repository.delete(campoExistente);
    }
}
