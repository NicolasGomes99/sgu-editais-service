package br.edu.ufape.sguEditaisService.exceptions;

public class DocumentoEditalNotFoundException extends RuntimeException {
    public DocumentoEditalNotFoundException() {
        super("Documento do edital não encontrado.");
    }
}
