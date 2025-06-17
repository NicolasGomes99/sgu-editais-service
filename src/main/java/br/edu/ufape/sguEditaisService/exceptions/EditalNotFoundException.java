package br.edu.ufape.sguEditaisService.exceptions;

public class EditalNotFoundException extends RuntimeException {
    public EditalNotFoundException() {
        super("Edital não encontrado");
    }
}
