package iftm.edu.br.tspi.pmvc.xande.menefreda.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import iftm.edu.br.tspi.pmvc.xande.menefreda.repository.PacienteRepository;
import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Paciente;   

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/pacientes")
public class PacienteController {
    
    private PacienteRepository pacienteRepository;

    public static final String URL_LISTA = "pacientes/listaPaciente";
    public static final String URL_FORM = "pacientes/formPaciente";
    public static final String URL_FORM_LISTA = "redirect:/paciente";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "paciente";
    public static final String ATRIBUTO_LISTA = "pacientes";

    public PacienteController(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @GetMapping("")
    public String listar(Model model) {
        List<Paciente> pacientes = pacienteRepository.listar();
        model.addAttribute(ATRIBUTO_LISTA, pacientes);
        return URL_LISTA;
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute(ATRIBUTO_OBJETO, new Paciente());
        return URL_FORM;
    }
    @PostMapping("/novo")
    public String salvar(@ModelAttribute Paciente paciente, Model model) {
        if(paciente.getCpf() != null) {
            pacienteRepository.salvar(paciente);
        } else {
            pacienteRepository.atualizar(paciente);
        }

        return listar(model);
    }

    @PostMapping("/excluir/{cpf}")
    public String excluir(@PathVariable("cpf") String cpf, Model model) {
        pacienteRepository.excluir(cpf);
        model.addAttribute(ATRIBUTO_MENSAGEM, "Paciente excluído com sucesso");
        
        return listar(model);
    }
    

    @GetMapping("/editar/{cpf}")
    public String editar(@PathVariable("cpf") String cpf, Model model) {
        Paciente paciente = pacienteRepository.buscaPorCpf(cpf);
        model.addAttribute(ATRIBUTO_OBJETO, paciente);
        return URL_FORM;
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam("nome") String nome, Model model) {
        List<Paciente> pacientes = pacienteRepository.buscaPorNome(nome);

        if (pacientes.isEmpty()) {
            model.addAttribute(ATRIBUTO_MENSAGEM, 
            "Paciente com o nome: " +nome+ " não encontrado(a)");
        }

        model.addAttribute("pacientes", pacientes);
        return URL_LISTA;
    }

}
