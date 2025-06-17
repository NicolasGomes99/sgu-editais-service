package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.DocumentoEditalRepository;
import br.edu.ufape.sguEditaisService.exceptions.DocumentoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.DocumentoEdital;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentoEditalService implements br.edu.ufape.sguEditaisService.servicos.interfaces.DocumentoEditalService {
    private final DocumentoEditalRepository repository;

    @Override
    public DocumentoEdital salvar(DocumentoEdital entity) {
        return  repository.save(entity);
    }

    @Override
    public DocumentoEdital buscar(Long id) throws DocumentoEditalNotFoundException{
        return repository.findById(id).orElseThrow(DocumentoEditalNotFoundException::new);
    }

    @Override
    public List<DocumentoEdital> listar() {
        return repository.findAll();
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
    public void deletar(Long id) throws DocumentoEditalNotFoundException {
        DocumentoEdital documentoExistente = repository.findById(id).orElseThrow(DocumentoEditalNotFoundException::new);
        repository.delete(documentoExistente);
    }
}
