package iftm.edu.br.tspi.pmvc.xande.menefreda.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Paciente;

@Component
public class PacienteRepository {
    private List<Paciente> pacientes = new ArrayList<>();

    public PacienteRepository() {
        // Adicionando dois pacientes cadastrados
        pacientes.add(new Paciente("12345678901", "João Silva", "Masculino", "joao.silva@example.com", "Rua A, 123", "123456789", "1980-01-01"));
        pacientes.add(new Paciente("98765432100", "Maria Oliveira", "Feminino", "maria.oliveira@example.com", "Rua B, 456", "987654321", "1990-02-02"));
    }

    public List<Paciente> listarPacientes() {
        return new ArrayList<>(pacientes);
    }

    public List<Paciente> buscarPorNome(String nome) {
        List<Paciente> pacienteBusca = new ArrayList<>();
        for(Paciente paciente : this.pacientes) {
            if (paciente.getNome().toLowerCase().contains(nome.toLowerCase())) {
                pacienteBusca.add(paciente);
            }
        }
        return pacienteBusca;
    }

    public Paciente buscaPorCpf(String cpf) {
        Paciente pacienteBusca = new Paciente(cpf);
        int i = pacientes.indexOf(pacienteBusca);
        if (i != -1) {
            return pacientes.get(i);
        } else {
            return null;
        }
    }

    public void novoPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
    }

    public boolean deletePaciente(String cpf) {
        Paciente paciente = new Paciente(cpf);
        return this.pacientes.remove(paciente);
    }

    public boolean updatePaciente(Paciente paciente) {
        int i = this.pacientes.indexOf(paciente);
        if (i != -1) {
            this.pacientes.set(i, paciente);
            return true;
        }
        return false;
    }
}
