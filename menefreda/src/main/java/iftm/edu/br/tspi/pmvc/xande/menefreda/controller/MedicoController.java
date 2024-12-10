package iftm.edu.br.tspi.pmvc.xande.menefreda.controller;

import java.util.Comparator;
import java.util.List;


import org.springframework.stereotype.Controller;

import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Medico;
import iftm.edu.br.tspi.pmvc.xande.menefreda.repository.MedicoRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/medico")
public class MedicoController {
    private final MedicoRepository repository;

    public static final String URL_LISTA = "medicos/listaMedico";
    public static final String URL_FORM = "medicos/formMedico";
    public static final String URL_FORM_LISTA = "redirect:/medico";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "medico";
    public static final String ATRIBUTO_LISTA = "medicos";

    public MedicoController(MedicoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        List<Medico> medicos = repository.listarMedicos();
        medicos.sort(Comparator.comparingInt(Medico::getCodigo));
    
        model.addAttribute(ATRIBUTO_LISTA, medicos);
        return URL_LISTA;
    }

    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam("nome") String nome, Model model) {
        List<Medico> medicosBusca = repository.buscarPorNome(nome);
        model.addAttribute(ATRIBUTO_LISTA, medicosBusca);
        if (medicosBusca.isEmpty()) {
            model.addAttribute(ATRIBUTO_MENSAGEM, "Nenhum médico encontrado com o nome " + nome);
        }
        return URL_LISTA;
    }

    @GetMapping("/novo")
    public String abirFormNovoMedico(Model model) {
        Medico medico = new Medico();
        int proximoCodigo = repository.obterProximoCodigo();
        medico.setCodigo(proximoCodigo);
        model.addAttribute(ATRIBUTO_OBJETO, medico);
        return URL_FORM;
    }

    @GetMapping("/editar/{codigo}")
    public String abrirFormEditarMedico(@PathVariable("codigo") int codigo, Model model, RedirectAttributes redirectAttributes) {
        Medico medicoBusca = repository.buscaPorCodigo(codigo);
        if (medicoBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Médico com código " + codigo + " não encontrado");
            return URL_FORM_LISTA;
        } else {
            model.addAttribute(ATRIBUTO_OBJETO, medicoBusca);
            return URL_FORM;
        }
    }

    @PostMapping("/novo")
    public String salvarMedico(@ModelAttribute("medico") Medico medico, RedirectAttributes redirectAttributes) {
        repository.novoMedico(medico);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Médico " + medico.getCodigo() + " cadastrado com sucesso");
        return URL_FORM_LISTA;
    }

    @PostMapping(value = "/excluir/{codigo}")
    public String excluirMedico(@PathVariable("codigo") int codigo, RedirectAttributes redirectAttributes) {  
        repository.deleteMedico(codigo);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Médico com código " + codigo + " excluído com sucesso");
        return URL_FORM_LISTA;
    }

    @PostMapping("/editar/{codigo}")
    public String atualizarMedico(@PathVariable("codigo") int codigo, @ModelAttribute("medico") Medico medico, RedirectAttributes redirectAttributes) {
        if (repository.updateMedico(medico)) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Médico " + medico.getNome() + " atualizado com sucesso");
        } else {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Médico com código " + codigo + " não encontrado");
        }
        return URL_FORM_LISTA;
    }
}