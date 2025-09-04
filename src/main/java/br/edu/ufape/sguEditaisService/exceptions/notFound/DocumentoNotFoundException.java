package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class DocumentoNotFoundException extends NotFoundException {
    public DocumentoNotFoundException(Long id) {
        super("Documento", id);
    }
}
