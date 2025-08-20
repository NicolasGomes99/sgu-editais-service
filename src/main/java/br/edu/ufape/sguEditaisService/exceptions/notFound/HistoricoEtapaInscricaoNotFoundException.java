package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class HistoricoEtapaInscricaoNotFoundException extends NotFoundException {
    public HistoricoEtapaInscricaoNotFoundException(Long id) {
        super("Histórico da etapa de inscrição", id);
    }
}