package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class ValorCampoNotFoundException extends RuntimeException {
    public ValorCampoNotFoundException() {
        super("Valor de campo não encontrado");
    }
}
