package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.dados.EditalRepository;
import br.edu.ufape.sguEditaisService.exceptions.EditalNotFoundException;
import br.edu.ufape.sguEditaisService.models.Edital;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EditalService implements br.edu.ufape.sguEditaisService.servicos.interfaces.EditalService{
    private final EditalRepository repository;

    @Override
    public Edital salvar(Edital entity) {
        return repository.save(entity);
    }

    @Override
    public Edital buscar(Long id) throws  EditalNotFoundException{
        return repository.findById(id).orElseThrow(EditalNotFoundException::new);
    }

    @Override
    public List<Edital> listar() {
        return repository.findAll();
    }

    @Override
    public Edital editar(Long id, Edital entity) throws EditalNotFoundException {
        Edital edtialExistente = buscar(id);
        edtialExistente.setTitulo(entity.getTitulo());
        edtialExistente.setDescricao(entity.getDescricao());
        edtialExistente.setDataPublicacao(entity.getDataPublicacao());
        edtialExistente.setInicioInscricao(entity.getInicioInscricao());
        edtialExistente.setFimIncricao(entity.getFimIncricao());
        edtialExistente.setStatus(entity.getStatus());
        edtialExistente.setTipoEdital(entity.getTipoEdital());
        edtialExistente.setEtapas(entity.getEtapas());
        edtialExistente.setInscricoes(entity.getInscricoes());
        edtialExistente.setDocumentosEdital(entity.getDocumentosEdital());
        edtialExistente.setCamposPersonalizados(entity.getCamposPersonalizados());
        return repository.save(edtialExistente);
    }

    @Override
    public void deletar(Long id) throws EditalNotFoundException {
        Edital edital = repository.findById(id).orElseThrow(EditalNotFoundException::new);
        repository.delete(edital);
    }
}
