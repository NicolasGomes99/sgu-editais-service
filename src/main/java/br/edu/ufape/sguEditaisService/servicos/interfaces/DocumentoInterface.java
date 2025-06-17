package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.DocumentoNotFoundException;
import br.edu.ufape.sguEditaisService.models.Documento;

import java.util.List;

public interface DocumentoInterface {
     Documento salvar(Documento entity);

     Documento buscar(Long id) throws DocumentoNotFoundException;

     List<Documento> listar();

     Documento editar(Long id, Documento entity) throws DocumentoNotFoundException;

     void deletar(Long id) throws DocumentoNotFoundException;
}
