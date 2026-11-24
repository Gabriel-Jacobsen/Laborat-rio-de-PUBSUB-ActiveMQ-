
package br.edu.amqp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.amqp.service.PriorityService;

@Controller
@RequestMapping("/prioridade")
public class PriorityController {

    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @PostMapping("/enviar")
    public String enviar(RedirectAttributes attributes) {

        var mensagens = priorityService.executarProdutor();

        attributes.addFlashAttribute(
            "mensagem",
            "Envio finalizado! " + mensagens.size() + " mensagens enviadas."
        );

        attributes.addFlashAttribute(
            "mensagensEnviadas",
            mensagens
        );

        return "redirect:/";
    }

    @PostMapping("/consumir")
    public String consumir(RedirectAttributes attributes) {

        var mensagens = priorityService.executarConsumidor();

        attributes.addFlashAttribute(
            "mensagem",
            "Consumo finalizado! " + mensagens.size() + " mensagens recebidas."
        );

        attributes.addFlashAttribute(
            "mensagensRecebidas",
            mensagens
        );

        return "redirect:/";
    }
}
