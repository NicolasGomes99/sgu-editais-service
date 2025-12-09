package br.edu.ufape.sguEditaisService.seeder;

import br.edu.ufape.sguEditaisService.dados.EtapaRepository;
import br.edu.ufape.sguEditaisService.dados.StatusPersonalizadoRepository;
import br.edu.ufape.sguEditaisService.dados.TipoEditalRepository;
import br.edu.ufape.sguEditaisService.models.Etapa;
import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import br.edu.ufape.sguEditaisService.models.TipoEdital;
import br.edu.ufape.sguEditaisService.models.enums.StatusBase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
        seedStatus();
        seedModelosCanonicos();
    }

    private void seedStatus() {
        Arrays.stream(StatusBase.values()).forEach(statusEnum -> {
            Optional<StatusPersonalizado> existente = statusRepository.findAll().stream()
                    .filter(s -> s.getNome().equalsIgnoreCase(statusEnum.getNome())
                            && s.getTipoStatus() == statusEnum.getTipo())
                    .findFirst();

            if (existente.isEmpty()) {
                StatusPersonalizado novo = new StatusPersonalizado();
                novo.setNome(statusEnum.getNome());
                novo.setTipoStatus(statusEnum.getTipo());
                statusRepository.save(novo);
                System.out.println("Seeder: Status '" + statusEnum.getNome() + "' criado.");
            }
        });
    }

    private void seedModelosCanonicos() {
        String nomeModelo = "Fluxo Padrão com Pagamento";

        Optional<TipoEdital> existente = tipoEditalRepository.findAll().stream()
                .filter(t -> t.getNome().equals(nomeModelo))
                .findFirst();

        if (existente.isPresent()) {
            return;
        }

        TipoEdital modelo = new TipoEdital();
        modelo.setNome(nomeModelo);
        modelo.setDescricao("Modelo canônico contendo Inscrição, Pagamento e Homologação.");
        // idUnidadeAdministrativa pode ser null para modelos globais do sistema

        TipoEdital modeloSalvo = tipoEditalRepository.save(modelo);

        // Criando as Etapas Canônicas
        criarEtapaModelo(modeloSalvo, "Inscrição", "Preenchimento de dados e envio de documentos.", 1, true);
        criarEtapaModelo(modeloSalvo, "Pagamento/Isenção", "Análise de pagamento ou deferimento de isenção.", 2, true);
        criarEtapaModelo(modeloSalvo, "Homologação das Inscrições", "Resultado preliminar das inscrições.", 3, true);
        criarEtapaModelo(modeloSalvo, "Recursos", "Período para interposição de recursos.", 4, false);
        criarEtapaModelo(modeloSalvo, "Resultado Final", "Publicação do resultado final.", 5, true);

        System.out.println("Seeder: Modelo Canônico '" + nomeModelo + "' criado com sucesso.");
    }

    private void criarEtapaModelo(TipoEdital modelo, String nome, String descricao, int ordem, boolean obrigatoria) {
        Etapa etapa = new Etapa();
        etapa.setNome(nome);
        etapa.setDescricao(descricao);
        etapa.setOrdem(ordem);
        etapa.setObrigatoria(obrigatoria);
        etapa.setTipoEditalModelo(modelo);

        etapaRepository.save(etapa);
    }
}