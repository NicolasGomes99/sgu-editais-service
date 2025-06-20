package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class DocumentoNotFoundException extends RuntimeException {
    public DocumentoNotFoundException() {
        super("Documento não encontrado.");
    }
}
