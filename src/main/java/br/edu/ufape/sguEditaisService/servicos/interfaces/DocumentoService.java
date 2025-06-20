package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.notFound.DocumentoNotFoundException;
import br.edu.ufape.sguEditaisService.models.Documento;

import java.util.List;

public interface DocumentoService {
     Documento salvarDocumento(Documento entity);

     Documento buscarPorIdDocumento(Long id) throws DocumentoNotFoundException;

     List<Documento> listarDocumento();

     Documento editarDocumento(Long id, Documento entity) throws DocumentoNotFoundException;

     void deletarDocumento(Long id) throws DocumentoNotFoundException;
}
