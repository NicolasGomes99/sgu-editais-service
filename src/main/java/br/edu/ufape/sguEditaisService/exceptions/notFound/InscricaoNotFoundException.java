package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class InscricaoNotFoundException extends NotFoundException {
    public InscricaoNotFoundException(Long id) {
        super("Inscrição", id);
    }
}
