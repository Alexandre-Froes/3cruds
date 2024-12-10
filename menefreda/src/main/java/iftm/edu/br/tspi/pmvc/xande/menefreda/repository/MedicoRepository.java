package iftm.edu.br.tspi.pmvc.xande.menefreda.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Medico;

@Component
public class MedicoRepository {
    private final List<Medico> medicos;
    public MedicoRepository() {
        this.medicos = new ArrayList<>();
        this.medicos.add(new Medico(1, "Henrique Bonaquê", "Podólogo", "99 99999-9999"));
        this.medicos.add(new Medico(2, "Renata Hermana", "Cardiologista", "99 99999-9999"));
    }

    public List<Medico> listarMedicos() {
        return this.medicos;
    }

    public List<Medico> buscarPorNome(String nome) {
        List<Medico> medicoBusca = new ArrayList<>();
        for(Medico medico: this.medicos) {
            if (medico.getNome().toLowerCase().contains(nome.toLowerCase())) {
                medicoBusca.add(medico);
            }
        }
        return medicoBusca;
    }

    public Medico buscaPorCodigo(int codigo) {
        for (Medico medico : medicos) {
            if (medico.getCodigo() == codigo) {
                return medico;
            }
        }
        return null;
    }

    public void novoMedico(Medico medico) {
        this.medicos.add(medico);
    }

    public boolean deleteMedico(int codigo) {
        Medico medico = new Medico(codigo);
        return this.medicos.remove(medico);
    }

    public boolean updateMedico(Medico medico) {
        int i = this.medicos.indexOf(medico);
        if (i != -1) {
            this.medicos.set(i, medico);
            return true;
        }
        return false;
    }
    
    public int obterProximoCodigo() {
        for(int i = 1; i < this.medicos.size(); i++) {
            if(i + 1 != this.medicos.get(i).getCodigo()) {
                return i + 1;
            }
        }
    return this.medicos.size() + 1;
    }
}
