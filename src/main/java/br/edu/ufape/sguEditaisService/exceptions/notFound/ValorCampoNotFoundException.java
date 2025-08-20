package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class ValorCampoNotFoundException extends NotFoundException {
    public ValorCampoNotFoundException(Long id) {
        super("Valor do campo", id);
    }
}
