package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.DocumentoEdital;

import java.util.List;

public interface DocumentoEditalService {
    DocumentoEdital salvarDocumetoEdial (DocumentoEdital entity);

    DocumentoEdital buscarPorIdDocumentoEdital(Long id) throws DocumentoEditalNotFoundException;

    List<DocumentoEdital> listarDocumentoEdital();

    DocumentoEdital editarDocumentoEdital(Long id, DocumentoEdital entity) throws DocumentoEditalNotFoundException;

    void deletarDocumentoEdital(Long id) throws DocumentoEditalNotFoundException;
}
