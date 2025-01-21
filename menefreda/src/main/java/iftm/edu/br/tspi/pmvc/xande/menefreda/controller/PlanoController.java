package iftm.edu.br.tspi.pmvc.xande.menefreda.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import iftm.edu.br.tspi.pmvc.xande.menefreda.repository.PlanoRepository;
import iftm.edu.br.tspi.pmvc.xande.menefreda.domain.Plano;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/planos")
public class PlanoController {
    private PlanoRepository planoRepository;

    public static final String URL_LISTA = "planos/listaPlano";
    public static final String URL_FORM = "planos/formPlano";
    public static final String URL_FORM_LISTA = "redirect:/plano";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "plano";
    public static final String ATRIBUTO_LISTA = "planos";

    public PlanoController(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    @GetMapping("")
    public String listar(Model model) {
        List<Plano> planos = planoRepository.listar();
        model.addAttribute(ATRIBUTO_LISTA, planos);
        return URL_LISTA;
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute(ATRIBUTO_OBJETO, new Plano());
        return URL_FORM;
    }

    @PostMapping("/novo")
    public String salvar(@ModelAttribute Plano plano, Model model) {
        if (plano.getCodigo() != null) {
            planoRepository.salvar(plano);
        } else {
            planoRepository.atualizar(plano);
        }

        return listar(model);
    }
    @GetMapping("/editar/{codigo}")
    public String editar(@PathVariable Integer codigo, Model model) {
        Plano plano = planoRepository.buscaPorCodigo(codigo);
        model.addAttribute(ATRIBUTO_OBJETO, plano);

        return URL_FORM;
    }
    @PostMapping("/excluir/{codigo}")
    public String excluir(@PathVariable Integer codigo, Model model) {
        planoRepository.excluir(codigo);
        model.addAttribute(ATRIBUTO_MENSAGEM, "Plano excluído com sucesso.");
        return listar(model);
    }    
}
