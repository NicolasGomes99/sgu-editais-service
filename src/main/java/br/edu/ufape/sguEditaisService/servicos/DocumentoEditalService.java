package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.DocumentoEditalRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.DocumentoEdital;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class DocumentoEditalService implements br.edu.ufape.sguEditaisService.servicos.interfaces.DocumentoEditalService {
    private final DocumentoEditalRepository documentoEditalRepository;
    private final ModelMapper modelMapper;

    @Override
    public DocumentoEdital salvarDocumentoEdital(DocumentoEdital entity) {
        entity.setDataUpload(LocalDateTime.now());
        return  documentoEditalRepository.save(entity);
    }

    @Override
    public DocumentoEdital buscarPorIdDocumentoEdital(Long id) throws DocumentoEditalNotFoundException{
        return documentoEditalRepository.findById(id).orElseThrow(() -> new DocumentoEditalNotFoundException(id));
    }

    @Override
    public Page<DocumentoEdital> listarDocumentoEdital(Pageable pageable) {
        return documentoEditalRepository.findAll(pageable);
    }

    @Override
    public DocumentoEdital editarDocumentoEdital(Long id, DocumentoEdital entity) throws  DocumentoEditalNotFoundException{
        DocumentoEdital documentoEdital = documentoEditalRepository.findById(id).orElseThrow(() -> new DocumentoEditalNotFoundException(id));
        modelMapper.map(entity, documentoEdital);
        return documentoEditalRepository.save(documentoEdital);
    }

    @Override
    public void deletarDocumentoEdital(Long id) throws DocumentoEditalNotFoundException {
        DocumentoEdital documentoEdital = documentoEditalRepository.findById(id).orElseThrow(() -> new DocumentoEditalNotFoundException(id));
        documentoEditalRepository.delete(documentoEdital);
    }
}