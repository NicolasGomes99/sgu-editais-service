package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.DocumentoRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoNotFoundException;
import br.edu.ufape.sguEditaisService.models.Documento;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentoService implements br.edu.ufape.sguEditaisService.servicos.interfaces.DocumentoService {
    private final DocumentoRepository documentoRepository;
    private final ModelMapper modelMapper;

    @Override
    public Documento salvarDocumento(Documento entity) {
        entity.setDataUpload(LocalDateTime.now());
        return documentoRepository.save(entity);
    }

    @Override
    public Documento buscarPorIdDocumento(Long id) throws DocumentoNotFoundException {
        return documentoRepository.findById(id).orElseThrow(DocumentoNotFoundException::new);
    }

    @Override
    public List<Documento> listarDocumento() {
        return documentoRepository.findAll();
    }

    @Override
    public Documento editarDocumento(Long id, Documento entity) throws DocumentoNotFoundException {
        Documento documento = documentoRepository.findById(id).orElseThrow(DocumentoNotFoundException::new);
        modelMapper.map(entity, documento);
        return documentoRepository.save(documento);
    }

    @Override
    public void deletarDocumento(Long id) throws DocumentoNotFoundException{
        Documento documento = documentoRepository.findById(id).orElseThrow(DocumentoNotFoundException::new);
        documentoRepository.delete(documento);
    }
}
