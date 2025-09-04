package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class TipoEditalNotFoundException extends NotFoundException {
    public TipoEditalNotFoundException(Long id) {
        super("Tipo de edital", id);
    }
}