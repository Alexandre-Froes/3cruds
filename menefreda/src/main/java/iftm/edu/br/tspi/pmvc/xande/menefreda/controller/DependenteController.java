package iftm.edu.br.tspi.pmvc.xande.menefreda.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import iftm.edu.br.tspi.pmvc.xande.menefreda.repository.PacienteRepository;
import iftm.edu.br.tspi.pmvc.xande.menefreda.repository.DependenteRepository;

import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Dependente;
import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Paciente;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/dependentes")
public class DependenteController {
    private final DependenteRepository dependenteRepository;
    private final PacienteRepository pacienteRepository;

    public static final String URL_LISTA = "dependentes/listaDependente";
    public static final String URL_FORM = "dependentes/formDependente";
    public static final String URL_FORM_LISTA = "redirect:/dependente";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "dependente";
    public static final String ATRIBUTO_LISTA = "dependentes";

    public DependenteController(DependenteRepository dependenteRepository, PacienteRepository pacienteRepository) {
        this.dependenteRepository = dependenteRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @GetMapping("")
    public String listar(Model model) {
        List<Dependente> dependentes = dependenteRepository.listar();
        model.addAttribute(ATRIBUTO_LISTA, dependentes);

        return URL_LISTA;
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        List<Paciente> pacientes = pacienteRepository.listar();
        model.addAttribute("pacientes", pacientes);

        model.addAttribute(ATRIBUTO_OBJETO, new Dependente());
        return URL_FORM;
    }
    
    @PostMapping("/novo")
    public String salvar(@ModelAttribute Dependente dependente, Model model) {
        if(dependente.getCodigo() != null) {
            dependenteRepository.atualizar(dependente);
        } else {
            dependenteRepository.salvar(dependente);
        }
        return listar(model);
    }

    @PostMapping("/excluir/{cpf}")
    public String excluir(@PathVariable("cpf") String cpf, Model model) {
        dependenteRepository.excluir(cpf);
        model.addAttribute(ATRIBUTO_MENSAGEM, "Dependente excluído com sucesso");

        return listar(model);
    }

    @GetMapping("/editar/{cpf}")
    public String editar(@PathVariable("cpf") String cpf, Model model) {
        List<Paciente> pacientes = pacienteRepository.listar();
        model.addAttribute("pacientes", pacientes);
        Dependente dependente = dependenteRepository.buscaPorCpfDep(cpf);
        model.addAttribute(ATRIBUTO_OBJETO, dependente);

        return URL_FORM;
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam("nome") String nome, Model model) {
        List<Dependente> dependentes = dependenteRepository.buscaPorNomeDep(nome);
        
        if (dependentes.isEmpty()) {
            model.addAttribute(ATRIBUTO_MENSAGEM, "Nenhum dependente encontrado");
        } else {
            model.addAttribute(ATRIBUTO_LISTA, dependentes);
            
        }
        
        return URL_LISTA;
    }
}
