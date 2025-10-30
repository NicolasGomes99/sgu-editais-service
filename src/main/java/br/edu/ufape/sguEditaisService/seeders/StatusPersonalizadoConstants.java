package br.edu.ufape.sguEditaisService.seeders;

/**
 * Armazena os IDs fixos dos StatusPersonalizados padrão criados pelo Seeder.
 * A lógica interna do sistema (ex: InscricaoService) deve usar estas constantes
 * para identificar status-chave (como aprovação/reprovação) de forma robusta,
 * sem depender de nomes (String).
 */
public final class StatusPersonalizadoConstants {

    // --- Status de ETAPA (IDs 1-10) ---
    public static final Long ETAPA_PENDENTE_ID = 1L;
    public static final Long ETAPA_EM_ANALISE_ID = 2L;
    public static final Long ETAPA_APROVADA_ID = 3L;
    public static final Long ETAPA_REPROVADA_ID = 4L;
    public static final Long ETAPA_DEFERIDA_ID = 5L;
    public static final Long ETAPA_INDEFERIDA_ID = 6L;

    // --- Status de INSCRICAO (IDs 11-20) ---
    public static final Long INSCRICAO_EM_ANDAMENTO_ID = 11L;
    public static final Long INSCRICAO_CONCLUIDA_ID = 12L;
    public static final Long INSCRICAO_REPROVADA_ID = 13L;
    public static final Long INSCRICAO_CANCELADA_ID = 14L;

    // --- Status de EDITAL (IDs 21-30) ---
    public static final Long EDITAL_RASCUNHO_ID = 21L;
    public static final Long EDITAL_PUBLICADO_ID = 22L;
    public static final Long EDITAL_ABERTO_ID = 23L;
    public static final Long EDITAL_EM_ANDAMENTO_ID = 24L;
    public static final Long EDITAL_FINALIZADO_ID = 25L;
    public static final Long EDITAL_CANCELADO_ID = 26L;

}
