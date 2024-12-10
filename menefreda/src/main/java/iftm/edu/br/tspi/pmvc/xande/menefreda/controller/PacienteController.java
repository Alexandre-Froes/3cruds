package iftm.edu.br.tspi.pmvc.xande.menefreda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Medico;
import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Paciente;
import iftm.edu.br.tspi.pmvc.xande.menefreda.repository.PacienteRepository;

@Controller
@RequestMapping("/paciente")
public class PacienteController {
    private final PacienteRepository repository;

    public static final String URL_LISTA = "pacientes/listaPaciente";
    public static final String URL_FORM = "pacientes/formPaciente";
    public static final String URL_FORM_LISTA = "redirect:/paciente";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "paciente";
    public static final String ATRIBUTO_LISTA = "pacientes";

    public PacienteController(PacienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        List<Paciente> pacientes = repository.listarPacientes();
        model.addAttribute(ATRIBUTO_LISTA, pacientes);
        return URL_LISTA;
    }

    @GetMapping("/buscar")
    public String buscaPorNome(@RequestParam("nome") String nome, Model model) {
        List<Paciente> pacienteBusca = repository.buscarPorNome(nome);
        model.addAttribute(ATRIBUTO_LISTA, pacienteBusca);
        if(pacienteBusca.isEmpty()){
            model.addAttribute(ATRIBUTO_MENSAGEM, nome+" não encontrado");
        }
        return URL_LISTA;
    }

    @GetMapping("/novo")
    public String abrirFormNovoPaciente(Model model) {
        Paciente paciente = new Paciente();
        model.addAttribute(ATRIBUTO_OBJETO, paciente);
        return URL_FORM;
    }

    @GetMapping("/editar/{cpf}")
    public String abrirFormEditarPaciente(@PathVariable("cpf") String cpf, Model model, RedirectAttributes redirectAttributes) {
        Paciente pacienteBusca = repository.buscaPorCpf(cpf);
        if (pacienteBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Paciente com CPF " + cpf + " não encontrado");
            return URL_FORM_LISTA;
        } else {
            model.addAttribute(ATRIBUTO_OBJETO, pacienteBusca);
            return URL_FORM;
        }
    }

    @PostMapping("/novo")
    public String salvarPaciente(@ModelAttribute("paciente") Paciente paciente, RedirectAttributes redirectAttributes) {
        repository.novoPaciente(paciente);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Paciente " + paciente.getNome() + " cadastrado com sucesso");
        return URL_FORM_LISTA;
    }

    @PostMapping("/excluir/{cpf}")
    public String excluirPaciente(@PathVariable("cpf") String cpf, RedirectAttributes redirectAttributes) {
        if (repository.deletePaciente(cpf)) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Paciente com CPF " + cpf + " excluído com sucesso");
        } else {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Paciente com CPF " + cpf + " não encontrado");
        }
        return URL_FORM_LISTA;
    }

    @PostMapping("/editar/{cpf}")
    public String atualizarPaciente(@PathVariable("cpf") String cpf, @ModelAttribute("paciente") Paciente paciente, RedirectAttributes redirectAttributes) {
        if (repository.updatePaciente(paciente)) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Paciente " + paciente.getNome() + " atualizado com sucesso");
        } else {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Paciente com CPF " + cpf + " não encontrado");
        }
        return URL_FORM_LISTA;
    }
}