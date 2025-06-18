package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.DocumentoEditalRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.DocumentoEdital;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentoEditalService implements br.edu.ufape.sguEditaisService.servicos.interfaces.DocumentoEditalService {
    private final DocumentoEditalRepository documentoEditalRepository;
    private final ModelMapper modelMapper;

    @Override
    public DocumentoEdital salvarDocumetoEdial(DocumentoEdital entity) {
        return  documentoEditalRepository.save(entity);
    }

    @Override
    public DocumentoEdital buscarPorIdDocumentoEdital(Long id) throws DocumentoEditalNotFoundException{
        return documentoEditalRepository.findById(id).orElseThrow(DocumentoEditalNotFoundException::new);
    }

    @Override
    public List<DocumentoEdital> listarDocumentoEdital() {
        return documentoEditalRepository.findAll();
    }

    @Override
    public DocumentoEdital editarDocumentoEdital(Long id, DocumentoEdital entity) throws  DocumentoEditalNotFoundException{
        DocumentoEdital documentoEdital = documentoEditalRepository.findById(id).orElseThrow(DocumentoEditalNotFoundException::new);
        modelMapper.map(entity, documentoEdital);
        return documentoEditalRepository.save(documentoEdital);
    }

    @Override
    public void deletarDocumentoEdital(Long id) throws DocumentoEditalNotFoundException {
        DocumentoEdital documentoEdital = documentoEditalRepository.findById(id).orElseThrow(DocumentoEditalNotFoundException::new);
        documentoEditalRepository.delete(documentoEdital);
    }
}
