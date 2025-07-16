package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.ValorCampoRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.ValorCampoNotFoundException;
import br.edu.ufape.sguEditaisService.models.ValorCampo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ValorCampoService implements br.edu.ufape.sguEditaisService.servicos.interfaces.ValorCampoService {
    private final ValorCampoRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public ValorCampo salvarValorCampo(ValorCampo entity) {
        return repository.save(entity);
    }

    @Override
    public ValorCampo buscarPorIdValorCampo(Long id) throws ValorCampoNotFoundException {
        return repository.findById(id).orElseThrow(ValorCampoNotFoundException::new);
    }

    @Override
    public List<ValorCampo> listarValorCampo() {
        return repository.findAll();
    }

    @Override
    public ValorCampo editarValorCampo(Long id, ValorCampo entity) throws ValorCampoNotFoundException {
        ValorCampo original = repository.findById(id).orElseThrow(ValorCampoNotFoundException::new);
        modelMapper.map(entity, original);
        return repository.save(original);
    }

    @Override
    public void deletarValorCampo(Long id) throws ValorCampoNotFoundException {
        ValorCampo entity = repository.findById(id).orElseThrow(ValorCampoNotFoundException::new);
        repository.delete(entity);
    }
}