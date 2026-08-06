package br.edu.ufape.sguEditaisService.features.edital;

import br.edu.ufape.sguEditaisService.exceptions.business.RegraNegocioException;
import br.edu.ufape.sguEditaisService.exceptions.notFound.ResourceNotFoundException;
import br.edu.ufape.sguEditaisService.features.edital.campoedital.CampoEdital;
import br.edu.ufape.sguEditaisService.features.edital.dto.EditalResponse;
import br.edu.ufape.sguEditaisService.features.edital.dto.InstanciarEditalRequest;
import br.edu.ufape.sguEditaisService.features.edital.etapainstancia.EtapaInstancia;
import br.edu.ufape.sguEditaisService.features.edital.etapainstancia.campoetapainstancia.CampoEtapaInstancia;
import br.edu.ufape.sguEditaisService.features.tipoedital.EstadoModelo;
import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEdital;
import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEditalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EditalService {

    private final EditalRepository editalRepository;
    private final TipoEditalService tipoEditalService;

    @Transactional
    public EditalResponse instanciar(InstanciarEditalRequest request) {
        // 1. Busca a Planta Baixa (Molde)
        TipoEdital molde = tipoEditalService.buscarEntidade(request.tipoEditalId());

        // 2. Regra de Negócio: Só instancia se o molde estiver FINALIZADO
        if (molde.getEstado() != EstadoModelo.FINALIZADO) {
            throw new RegraNegocioException("Só é possível instanciar um edital a partir de um modelo que já foi FINALIZADO.");
        }

        // 3. Cria a Instância (Edital Real)
        Edital editalInstanciado = Edital.instanciarDe(molde, request.titulo(), request.cursoId());

        // 4. DEEP COPY: Clonar os Campos Globais
        molde.getCamposModelo().forEach(campoMolde -> {
            CampoEdital campoClone = CampoEdital.clonarDe(
                    campoMolde.getTitulo(),
                    campoMolde.getTipoCampo(),
                    campoMolde.isObrigatorio(),
                    campoMolde.getConfiguracoes()
            );
            editalInstanciado.adicionarCampoGlobal(campoClone);
        });

        // 5. DEEP COPY: Clonar as Etapas e seus respectivos Campos
        molde.getEtapasModelo().forEach(etapaMolde -> {
            EtapaInstancia etapaClone = EtapaInstancia.clonarDe(
                    etapaMolde.getNome(),
                    etapaMolde.getDescricao(),
                    etapaMolde.getOrdem(),
                    etapaMolde.getConfiguracoes()
            );

            // Laço interno: clonar os campos específicos desta etapa
            etapaMolde.getCamposEtapa().forEach(campoEtapaMolde -> {
                CampoEtapaInstancia campoEtapaClone = CampoEtapaInstancia.clonarDe(
                        campoEtapaMolde.getTitulo(),
                        campoEtapaMolde.getTipoCampo(),
                        campoEtapaMolde.isObrigatorio(),
                        campoEtapaMolde.getConfiguracoes()
                );
                etapaClone.adicionarCampo(campoEtapaClone);
            });

            // Conecta a etapa clonada ao edital instanciado
            editalInstanciado.adicionarEtapa(etapaClone);
        });

        // 6. Graças ao CascadeType.ALL nas entidades "Edital" e "EtapaInstancia",
        // um único save no banco salva todas as dezenas/centenas de instâncias geradas de uma vez!
        Edital editalSalvo = editalRepository.save(editalInstanciado);

        return EditalResponse.from(editalSalvo);
    }

    public Edital buscarEntidade(Long id) {
        return editalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Edital", id));
    }

    @Transactional(readOnly = true)
    public EditalResponse buscarPorId(Long id) {
        Edital edital = buscarEntidade(id);
        return EditalResponse.from(edital);
    }
}