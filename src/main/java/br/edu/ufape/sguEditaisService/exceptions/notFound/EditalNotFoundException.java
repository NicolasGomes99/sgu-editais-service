package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class EditalNotFoundException extends NotFoundException {
    public EditalNotFoundException(Long id) {
        super("Edital", id);
    }
}
