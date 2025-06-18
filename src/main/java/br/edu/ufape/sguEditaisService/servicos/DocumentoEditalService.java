package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.DocumentoEditalRepository;
import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.DocumentoEdital;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentoEditalService implements br.edu.ufape.sguEditaisService.servicos.interfaces.DocumentoEditalService {
    private final DocumentoEditalRepository repository;

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
    public DocumentoEdital editar(Long id, DocumentoEdital entity) throws  DocumentoEditalNotFoundException{
        DocumentoEdital documentoExistente = buscar(id);
        documentoExistente.setNome(entity.getNome());
        documentoExistente.setCaminho(entity.getCaminho());
        documentoExistente.setDataUpload(entity.getDataUpload());
        documentoExistente.setEdital(entity.getEdital());
        return repository.save(documentoExistente);
    }

    @Override
    public void deletarDocumentoEdital(Long id) throws DocumentoEditalNotFoundException {
        DocumentoEdital documentoEdital = documentoEditalRepository.findById(id).orElseThrow(DocumentoEditalNotFoundException::new);
        documentoEditalRepository.delete(documentoEdital);
    }
}
