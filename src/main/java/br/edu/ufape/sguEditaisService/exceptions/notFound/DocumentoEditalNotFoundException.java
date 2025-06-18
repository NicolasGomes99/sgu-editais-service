package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class DocumentoEditalNotFoundException extends RuntimeException {
    public DocumentoEditalNotFoundException() {
        super("Documento do edital não encontrado.");
    }
}
