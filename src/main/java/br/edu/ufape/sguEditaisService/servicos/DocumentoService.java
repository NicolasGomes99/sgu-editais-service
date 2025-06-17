package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.DocumentoRepository;
import br.edu.ufape.sguEditaisService.exceptions.DocumentoNotFoundException;
import br.edu.ufape.sguEditaisService.models.Documento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentoService implements br.edu.ufape.sguEditaisService.servicos.interfaces.DocumentoInterface{
    private DocumentoRepository repository;

    @Override
    public Documento salvar(Documento entity) {
        return repository.save(entity);
    }

    @Override
    public Documento buscar(Long id) throws DocumentoNotFoundException {
        return repository.findById(id).orElseThrow(DocumentoNotFoundException::new);
    }

    @Override
    public List<Documento> listar() {
        return repository.findAll();
    }

    @Override
    public Documento editar(Long id, Documento entity) throws DocumentoNotFoundException{
        Documento documentoExistente = buscar(id);
        documentoExistente.setNome(entity.getNome());
        documentoExistente.setCaminho(entity.getCaminho());
        documentoExistente.setDataUpload(entity.getDataUpload());
        documentoExistente.setEtapa(entity.getEtapa());
        documentoExistente.setInscricao(entity.getInscricao());
        return repository.save(documentoExistente);
    }

    @Override
    public void deletar(Long id) throws DocumentoNotFoundException{
        Documento documentoExistente = repository.findById(id).orElseThrow(DocumentoNotFoundException::new);
        repository.delete(documentoExistente);
    }
}
