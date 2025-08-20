package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoEditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.DocumentoEdital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DocumentoEditalService {
    DocumentoEdital salvarDocumentoEdital (DocumentoEdital entity);

    DocumentoEdital buscarPorIdDocumentoEdital(Long id) throws DocumentoEditalNotFoundException;

    Page<DocumentoEdital> listarDocumentoEdital(Pageable pageable);

    DocumentoEdital editarDocumentoEdital(Long id, DocumentoEdital entity) throws DocumentoEditalNotFoundException;

    void deletarDocumentoEdital(Long id) throws DocumentoEditalNotFoundException;
}