package iftm.edu.br.tspi.pmvc.xande.menefreda.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Plano;

@Component
public class PlanoRepository {
    private final List<Plano> planos;
    
    public PlanoRepository() {
        this.planos = new ArrayList<>();
        this.planos.add(new Plano(1, 100.00, "Bronze"));
        this.planos.add(new Plano(2, 200.00, "Prata"));
    }

    public List<Plano> listarPlanos() {
        return this.planos;
    }

    public List<Plano> buscarPorTipo(String tipo) {
        List<Plano> buscaPlanos = new ArrayList<>();
        for (Plano plano : this.planos) {
            if (plano.getTipo().equalsIgnoreCase(tipo)) {
                buscaPlanos.add(plano);
            }
        }
        return buscaPlanos;
    }

    public Plano buscaPorCodigo(int codigo) {
        for (Plano medico : planos) {
            if (medico.getCodigo() == codigo) {
                return medico;
            }
        }
        return null;
    }

    public void novoPlano(Plano plano) {
        this.planos.add(plano);
    }

    public boolean deletePlano(int codigo) {
        Plano plano = new Plano(codigo);
        return this.planos.remove(plano);
    }

    public boolean updatePlano(Plano plano) {
        int i = this.planos.indexOf(plano);
        if (i != -1) {
            this.planos.set(i, plano);
            return true;
        }
        return false;
    }

    public int obterProximoCodigo() {
        for (int i = 1; i < this.planos.size(); i++) {
            if (i + 1 != this.planos.get(i).getCodigo()) {
                return i + 1;
            }
        }
        return this.planos.size() + 1;
    }
}