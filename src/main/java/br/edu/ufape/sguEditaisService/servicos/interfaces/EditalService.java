package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.exceptions.EditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.Edital;

import java.util.List;

public interface EditalService {
    Edital salvar(Edital entity);

    Edital buscar(Long id) throws EditalNotFoundException;

    List<Edital> listar();

    Edital editar(Long id, Edital entity) throws EditalNotFoundException;

    void deletar(Long id) throws EditalNotFoundException;
}
