package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.models.ValorCampo;
import br.edu.ufape.sguEditaisService.exceptions.notFound.ValorCampoNotFoundException;

import java.util.List;

public interface ValorCampoService {
    ValorCampo salvarValorCampo(ValorCampo entity);

    ValorCampo buscarPorIdValorCampo(Long id) throws ValorCampoNotFoundException;

    List<ValorCampo> listarValorCampo();

    ValorCampo editarValorCampo(Long id, ValorCampo entity) throws ValorCampoNotFoundException;

    void deletarValorCampo(Long id) throws ValorCampoNotFoundException;
}
