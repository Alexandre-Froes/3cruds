package iftm.edu.br.tspi.pmvc.xande.menefreda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import iftm.edu.br.tspi.pmvc.xande.menefreda.repository.MedicoRepository;
import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Medico;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/medicos")
public class MedicoController {
    
    private MedicoRepository medicoRepository;

    public static final String URL_LISTA = "medicos/listaMedico";
    public static final String URL_FORM = "medicos/formMedico";
    public static final String URL_REDIRECT_LISTA = "redirect:/medico";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "medico";
    public static final String ATRIBUTO_LISTA = "medicos";

    public MedicoController(MedicoRepository medicoRepository){
        this.medicoRepository = medicoRepository;
    }

    @GetMapping("")
    public String listar(Model model){
        List<Medico> medicos = medicoRepository.listar();
        model.addAttribute(ATRIBUTO_LISTA, medicos);
        return URL_LISTA;
    }

    @GetMapping("/novo")
    public String novo(Model model){
        model.addAttribute(ATRIBUTO_OBJETO, new Medico());
        return URL_FORM;
    }

    @PostMapping("/novo")
    public String salvar(@ModelAttribute Medico medico, Model model){
        if(medico.getCodigo() == null){
            medicoRepository.salvar(medico);
        }else{
            medicoRepository.atualizar(medico);
        }

        return listar(model);
    }

    @PostMapping("/excluir/{codigo}")
    public String excluir(@PathVariable("codigo") Integer codigo, Model model){
        medicoRepository.excluir(codigo);
        model.addAttribute(ATRIBUTO_MENSAGEM, "Médico excluido com sucesso!");
        
        return listar(model);
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable("codigo") Integer codigo, Model model){
        Medico medico = medicoRepository.buscaPorCodigo(codigo);
        model.addAttribute(ATRIBUTO_OBJETO, medico);
        return URL_FORM;
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam("nome") String nome, Model model){
        List<Medico> medicos = medicoRepository.buscaPorNome(nome);

        if(medicos.isEmpty()){
            model.addAttribute(ATRIBUTO_MENSAGEM, "Médico com nome: " + nome + "não encontrado!");
        }

        model.addAttribute("medicos", medicos);
        return URL_LISTA;
    }
}
