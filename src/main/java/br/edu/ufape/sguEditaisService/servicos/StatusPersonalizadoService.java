package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.exceptions.notFound.StatusPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import br.edu.ufape.sguEditaisService.dados.StatusPersonalizadoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class StatusPersonalizadoService implements br.edu.ufape.sguEditaisService.servicos.interfaces.StatusPersonalizadoService {
    private final StatusPersonalizadoRepository statusPersonalizadoRepository;
    private final ModelMapper modelMapper;

    @Override
    public StatusPersonalizado salvarStatusPersonalizado(StatusPersonalizado statusPersonalizado) {
        return statusPersonalizadoRepository.save(statusPersonalizado);
    }

    @Override
    public StatusPersonalizado buscarPorIdStatusPersonalizado(Long id) throws StatusPersonalizadoNotFoundException {
        return statusPersonalizadoRepository.findById(id).orElseThrow(() -> new StatusPersonalizadoNotFoundException(id));
    }

    @Override
    public List<StatusPersonalizado> listarStatusPersonalizados() {
        return statusPersonalizadoRepository.findAll();
    }

    @Override
    public StatusPersonalizado editarStatusPersonalizado(Long id, StatusPersonalizado statusPersonalizado) throws StatusPersonalizadoNotFoundException {
        StatusPersonalizado statusPersonalizado1 = statusPersonalizadoRepository.findById(id).orElseThrow(() -> new StatusPersonalizadoNotFoundException(id));
        modelMapper.map(statusPersonalizado, statusPersonalizado1);
        return statusPersonalizadoRepository.save(statusPersonalizado1);
    }

    @Override
    public void deletarStatusPersonalizado(Long id) throws StatusPersonalizadoNotFoundException {
        StatusPersonalizado statusPersonalizado = statusPersonalizadoRepository.findById(id).orElseThrow(() -> new StatusPersonalizadoNotFoundException(id));
        statusPersonalizadoRepository.delete(statusPersonalizado);
    }
}