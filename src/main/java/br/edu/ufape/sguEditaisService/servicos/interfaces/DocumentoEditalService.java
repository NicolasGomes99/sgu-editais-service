package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.DocumentoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.DocumentoEdital;

import java.util.List;

public interface DocumentoEditalService {
    DocumentoEdital salvar (DocumentoEdital entity);

    DocumentoEdital buscar(Long id) throws DocumentoEditalNotFoundException;

    List<DocumentoEdital> listar();

    DocumentoEdital editar(Long id, DocumentoEdital entity) throws DocumentoEditalNotFoundException;

    void deletar(Long id) throws DocumentoEditalNotFoundException;
}
