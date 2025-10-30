package br.edu.ufape.sguEditaisService.seeders;

import br.edu.ufape.sguEditaisService.dados.StatusPersonalizadoRepository;
import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Popula o banco com os Status Personalizados essenciais para o fluxo do sistema.
 * Os IDs são fixos para permitir que a lógica de negócio (ex: transição de etapa)
 * possa identificar status-chave (como Aprovação ou Reprovação) de forma confiável.
 */

@Component
@RequiredArgsConstructor
public class StatusPersonalizadoSeeder {

    private final StatusPersonalizadoRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void popularStatusPadrao() {
        if (repository.count() > 0) {
            // Se já existem status, não executa o seeder.
            return;
        }

        List<StatusPersonalizado> statusPadrao = Arrays.asList(
                // --- Status de ETAPA (IDs 1-10) ---
                new StatusPersonalizado(StatusPersonalizadoConstants.ETAPA_PENDENTE_ID, "Etapa: Pendente", TipoStatus.ETAPA),
                new StatusPersonalizado(StatusPersonalizadoConstants.ETAPA_EM_ANALISE_ID, "Etapa: Em Análise", TipoStatus.ETAPA),
                new StatusPersonalizado(StatusPersonalizadoConstants.ETAPA_APROVADA_ID, "Etapa: Aprovado", TipoStatus.ETAPA),
                new StatusPersonalizado(StatusPersonalizadoConstants.ETAPA_REPROVADA_ID, "Etapa: Reprovado", TipoStatus.ETAPA),
                new StatusPersonalizado(StatusPersonalizadoConstants.ETAPA_DEFERIDA_ID, "Etapa: Deferido", TipoStatus.ETAPA),
                new StatusPersonalizado(StatusPersonalizadoConstants.ETAPA_INDEFERIDA_ID, "Etapa: Indeferido", TipoStatus.ETAPA),

                // --- Status de INSCRICAO (IDs 11-20) ---
                new StatusPersonalizado(StatusPersonalizadoConstants.INSCRICAO_EM_ANDAMENTO_ID, "Inscrição: Em Andamento", TipoStatus.INSCRICAO),
                new StatusPersonalizado(StatusPersonalizadoConstants.INSCRICAO_CONCLUIDA_ID, "Inscrição: Concluída", TipoStatus.INSCRICAO),
                new StatusPersonalizado(StatusPersonalizadoConstants.INSCRICAO_REPROVADA_ID, "Inscrição: Reprovada", TipoStatus.INSCRICAO),
                new StatusPersonalizado(StatusPersonalizadoConstants.INSCRICAO_CANCELADA_ID, "Inscrição: Cancelada", TipoStatus.INSCRICAO),

                // --- Status de EDITAL (IDs 21-30) ---
                new StatusPersonalizado(StatusPersonalizadoConstants.EDITAL_RASCUNHO_ID, "Edital: Rascunho", TipoStatus.EDITAL),
                new StatusPersonalizado(StatusPersonalizadoConstants.EDITAL_PUBLICADO_ID, "Edital: Publicado", TipoStatus.EDITAL),
                new StatusPersonalizado(StatusPersonalizadoConstants.EDITAL_ABERTO_ID, "Edital: Inscrições Abertas", TipoStatus.EDITAL),
                new StatusPersonalizado(StatusPersonalizadoConstants.EDITAL_EM_ANDAMENTO_ID, "Edital: Em Andamento", TipoStatus.EDITAL),
                new StatusPersonalizado(StatusPersonalizadoConstants.EDITAL_FINALIZADO_ID, "Edital: Finalizado", TipoStatus.EDITAL),
                new StatusPersonalizado(StatusPersonalizadoConstants.EDITAL_CANCELADO_ID, "Edital: Cancelado", TipoStatus.EDITAL)
        );

        repository.saveAll(statusPadrao);
    }
}

