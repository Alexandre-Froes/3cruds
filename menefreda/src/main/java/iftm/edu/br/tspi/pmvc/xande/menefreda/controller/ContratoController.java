package iftm.edu.br.tspi.pmvc.xande.menefreda.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Contrato;
import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Paciente;
import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Plano;
import iftm.edu.br.tspi.pmvc.xande.menefreda.repository.ContratoRepository;
import iftm.edu.br.tspi.pmvc.xande.menefreda.repository.PacienteRepository;
import iftm.edu.br.tspi.pmvc.xande.menefreda.repository.PlanoRepository;

@Controller
@RequestMapping("/contratos")
public class ContratoController {
    private final ContratoRepository contratoRepository;
    private final PacienteRepository pacienteRepository;
    private final PlanoRepository planoRepository;

    public static final String URL_LISTA = "contratos/listaContrato";
    public static final String URL_FORM = "contratos/formContrato";
    public static final String URL_FORM_LISTA = "redirect:/contrato";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "contrato";
    public static final String ATRIBUTO_LISTA = "contratos";

    public ContratoController(ContratoRepository contratoRepository, PacienteRepository pacienteRepository, PlanoRepository planoRepository) {
        this.contratoRepository = contratoRepository;
        this.pacienteRepository = pacienteRepository;
        this.planoRepository = planoRepository;
    }

    @GetMapping("")
    public String listar(Model model) {
        List<Contrato> contratos = contratoRepository.listar();
        model.addAttribute(ATRIBUTO_LISTA, contratos);
        return URL_LISTA;
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        List<Paciente> pacientes = pacienteRepository.listar();
        model.addAttribute("pacientes", pacientes);

        List<Plano> planos = planoRepository.listar();
        model.addAttribute("planos", planos);

        model.addAttribute(ATRIBUTO_OBJETO, new Contrato());
        return URL_FORM;
    }

    @PostMapping("/novo")
    public String salvar(@ModelAttribute Contrato contrato, Model model) {
        if(contrato.getCodigo() != null) {
            contratoRepository.salvar(contrato);
        } else {
            contratoRepository.atualizar(contrato);
        }

        return listar(model);
    }

    @PostMapping("/excluir/{codigo}")   
    public String excluir(@PathVariable("codigo") Integer codigo, Model model) {
        contratoRepository.excluir(codigo);
        model.addAttribute(ATRIBUTO_MENSAGEM, "Contrato excluído com sucesso");
        
        return listar(model);
    }

    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable("codigo") Integer codigo, Model model) {
        Contrato contrato = contratoRepository.buscaPorCodigo(codigo);
        model.addAttribute(ATRIBUTO_OBJETO, contrato);
        
        return URL_FORM;
    }
    
    @GetMapping("/buscarNome")
    public String buscar(@RequestParam("nome") String nome, Model model) {
        List<Contrato> contratos = contratoRepository.buscaPorNome(nome);

        if (contratos.isEmpty()) {
            model.addAttribute(ATRIBUTO_MENSAGEM, 
            "Contrato com o nome: " +nome+ " não encontrado(a)");
        }

        return URL_LISTA;
    }
}
