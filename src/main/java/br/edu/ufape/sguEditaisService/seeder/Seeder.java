package br.edu.ufape.sguEditaisService.seeder;

import br.edu.ufape.sguEditaisService.dados.EtapaRepository;
import br.edu.ufape.sguEditaisService.dados.StatusPersonalizadoRepository;
import br.edu.ufape.sguEditaisService.dados.TipoEditalRepository;
import br.edu.ufape.sguEditaisService.models.Etapa;
import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {

    private final StatusPersonalizadoRepository statusRepository;
    private final TipoEditalRepository tipoEditalRepository;
    private final EtapaRepository etapaRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        seedStatusInteligentes();
        seedModeloFluxoPadrao();
    }

    private void seedStatusInteligentes() {
        // Status Gerais (Não avançam etapa)
        criarStatus("Inscrição Iniciada", TipoStatus.INSCRICAO, false);
        criarStatus("Em Análise", TipoStatus.INSCRICAO, false);
        criarStatus("Pendência", TipoStatus.INSCRICAO, false);
        criarStatus("Aguardando Pagamento", TipoStatus.INSCRICAO, false);
        criarStatus("Indeferido", TipoStatus.INSCRICAO, false);

        // Status de Sucesso (AVANÇAM ETAPA!)
        criarStatus("Deferido", TipoStatus.INSCRICAO, true);
        criarStatus("Pago", TipoStatus.INSCRICAO, true);
        criarStatus("Isento", TipoStatus.INSCRICAO, true);
        criarStatus("Aprovado", TipoStatus.ETAPA, true);
    }

    private void criarStatus(String nome, TipoStatus tipo, boolean concluiEtapa) {
        Optional<StatusPersonalizado> existente = statusRepository.findAll().stream()
                .filter(s -> s.getNome().equalsIgnoreCase(nome) && s.getTipoStatus() == tipo)
                .findFirst();

        if (existente.isEmpty()) {
            StatusPersonalizado novo = new StatusPersonalizado();
            novo.setNome(nome);
            novo.setTipoStatus(tipo);
            novo.setConcluiEtapa(concluiEtapa);
            statusRepository.save(novo);
            System.out.println("Seeder: Status '" + nome + "' criado (Conclui Etapa: " + concluiEtapa + ")");
        }
    }

    private void seedModeloFluxoPadrao() {
        String nomeModelo = "Fluxo Padrão (Com Pagamento)";
        if (tipoEditalRepository.findAll().stream().anyMatch(t -> t.getNome().equals(nomeModelo))) {
            return;
        }

        TipoEdital modelo = new TipoEdital();
        modelo.setNome(nomeModelo);
        modelo.setDescricao("Fluxo: Inscrição -> Pagamento -> Homologação -> Resultado.");
        TipoEdital salvo = tipoEditalRepository.save(modelo);

        criarEtapa(salvo, "Inscrição", 1);
        criarEtapa(salvo, "Pagamento/Isenção", 2);
        criarEtapa(salvo, "Homologação", 3);
        criarEtapa(salvo, "Resultado Final", 4);
    }

    private void criarEtapa(TipoEdital modelo, String nome, int ordem) {
        Etapa e = new Etapa();
        e.setNome(nome);
        e.setOrdem(ordem);
        e.setObrigatoria(true);
        e.setTipoEditalModelo(modelo);
        etapaRepository.save(e);
    }
}