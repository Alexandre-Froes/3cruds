package iftm.edu.br.tspi.pmvc.xande.menefreda.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Plano;
import iftm.edu.br.tspi.pmvc.xande.menefreda.repository.PlanoRepository;

@Controller
@RequestMapping("/plano")
public class PlanoController {
    private PlanoRepository repository;

    public static final String URL_LISTA = "planos/listaPlano";
    public static final String URL_FORM = "planos/formPlano";
    public static final String URL_FORM_LISTA = "redirect:/plano";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "plano";
    public static final String ATRIBUTO_LISTA = "planos";

    public PlanoController(PlanoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        List<Plano> planos = repository.listarPlanos();
        planos.sort(Comparator.comparingInt(Plano::getCodigo));
    
        model.addAttribute(ATRIBUTO_LISTA, planos);
        return URL_LISTA;
    }

    @GetMapping("/buscar")
    public String buscarPorTipo(@RequestParam("tipo") String tipo, Model model) {
        List<Plano> planosBusca = repository.buscarPorTipo(tipo);
        model.addAttribute(ATRIBUTO_LISTA, planosBusca);
        if (planosBusca.isEmpty()) {
            model.addAttribute(ATRIBUTO_MENSAGEM, "Nenhum plano encontrado com o tipo " + tipo);
        }
        return URL_LISTA;
    }

    @GetMapping("/novo")
    public String abirFormNovoPlano(Model model) {
        Plano plano = new Plano();
        int proximoCodigo = repository.obterProximoCodigo();
        plano.setCodigo(proximoCodigo);
        model.addAttribute(ATRIBUTO_OBJETO, plano);
        return URL_FORM;
    }

    @GetMapping("/editar/{codigo}")
    public String abrirFormEditarPlano(@PathVariable("codigo") int codigo, Model model, RedirectAttributes redirectAttributes) {
        Plano planoBusca = repository.buscaPorCodigo(codigo);
        if (planoBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "plano com código " + codigo + " não encontrado");
            return URL_FORM_LISTA;
        } else {
            model.addAttribute(ATRIBUTO_OBJETO, planoBusca);
            return URL_FORM;
        }
    }

    public boolean deletePlano(int codigo) {
        if (codigo > 0) {
            return repository.deletePlano(codigo);
        }
        return false;
    }

        @PostMapping("/novo")
    public String salvarPlano(@ModelAttribute("plano") Plano plano, RedirectAttributes redirectAttributes) {
        repository.novoPlano(plano);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Plano " + plano.getTipo() + " cadastrado com sucesso");
        return URL_FORM_LISTA;
    }

    @PostMapping(value = "/excluir/{codigo}")
    public String excluirPlano(@PathVariable("codigo") int codigo, RedirectAttributes redirectAttributes) {  
        repository.deletePlano(codigo);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Plano com código " + codigo + " excluído com sucesso");
        return URL_FORM_LISTA;
    }

    @PostMapping("/editar/{codigo}")
    public String atualizarPlano(@PathVariable("codigo") int codigo, @ModelAttribute("plano") Plano plano, RedirectAttributes redirectAttributes) {
        if (repository.updatePlano(plano)) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Plano " + plano.getTipo() + " atualizado com sucesso");
        } else {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Plano com código " + codigo + " não encontrado");
        }
        return URL_FORM_LISTA;
    }
}