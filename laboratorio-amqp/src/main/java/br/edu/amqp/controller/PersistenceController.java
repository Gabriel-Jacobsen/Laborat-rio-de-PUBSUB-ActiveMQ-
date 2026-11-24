
package br.edu.amqp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.amqp.service.PersistenceService;

@Controller
@RequestMapping("/persistencia")
public class PersistenceController {

    private final PersistenceService persistenceService;

    public PersistenceController(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @PostMapping("/enviar")
    public String enviar(
            @RequestParam(defaultValue = "Mensagem persistente de teste") String conteudo,
            RedirectAttributes attributes) {

        persistenceService.enviarMensagem(conteudo);

        attributes.addFlashAttribute(
            "mensagem",
            "Mensagem persistente enviada com sucesso!"
        );

        return "redirect:/";
    }

    @PostMapping("/consumir")
    public String consumir(RedirectAttributes attributes) {

        var mensagens = persistenceService.receberMensagem();

        attributes.addFlashAttribute(
            "mensagem",
            mensagens.isEmpty()
                ? "Nenhuma mensagem encontrada na fila."
                : "Consumo finalizado! " + mensagens.size() + " mensagem(ns) recebida(s)."
        );

        attributes.addFlashAttribute("mensagensRecebidas", mensagens);

        return "redirect:/";
    }
}
