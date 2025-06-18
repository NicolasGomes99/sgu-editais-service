package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.EditalRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.Edital;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EditalService implements br.edu.ufape.sguEditaisService.servicos.interfaces.EditalService{
    private final EditalRepository editalRepository;
    private final ModelMapper modelMapper;

    @Override
    public Edital salvarEdital(Edital entity) {
        return editalRepository.save(entity);
    }

    @Override
    public Edital buscarPorIdEdital(Long id) throws  EditalNotFoundException{
        return editalRepository.findById(id).orElseThrow(EditalNotFoundException::new);
    }

    @Override
    public List<Edital> listarEdital() {
        return editalRepository.findAll();
    }

    @Override
     public Edital editarEdital(Long id, Edital entity) throws EditalNotFoundException {
        Edital edital = editalRepository.findById(id).orElseThrow(EditalNotFoundException::new);
        modelMapper.map(entity, edital);
        return editalRepository.save(edital);
    }


    @Override
    public void deletarEdital(Long id) throws EditalNotFoundException {
        Edital edital = editalRepository.findById(id).orElseThrow(EditalNotFoundException::new);
        editalRepository.delete(edital);
    }
}
