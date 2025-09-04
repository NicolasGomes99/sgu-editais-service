package br.edu.ufape.sguEditaisService.exceptions.notFound;

public abstract class NotFoundException extends RuntimeException {
    protected NotFoundException(String resource, Long id) {
        super(String.format("%s com id %d não encontrado.", resource, id));
    }

    protected NotFoundException(String message) {
        super(message);
    }
}