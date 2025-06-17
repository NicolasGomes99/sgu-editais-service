package br.edu.ufape.sguEditaisService.exceptions;

public class DocumentoNotFoundException extends RuntimeException {
    public DocumentoNotFoundException() {
        super("Documento não encontrado.");
    }
}
