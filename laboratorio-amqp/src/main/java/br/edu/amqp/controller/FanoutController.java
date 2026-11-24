
package br.edu.amqp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.amqp.service.FanoutService;

@Controller
@RequestMapping("/fanout")
public class FanoutController {

    private final FanoutService fanoutService;

    public FanoutController(FanoutService fanoutService) {
        this.fanoutService = fanoutService;
    }

    @PostMapping("/executar")
    public String executar(
            @RequestParam(defaultValue = "Mensagem de teste Fan-out") String conteudo,
            RedirectAttributes attributes) {

        var mensagens = fanoutService.executarExperimento(conteudo);

        attributes.addFlashAttribute(
            "mensagem",
            "Fan-out finalizado! " + mensagens.size()
                + " assinantes receberam a mensagem."
        );

        attributes.addFlashAttribute(
            "mensagensRecebidas",
            mensagens
        );

        return "redirect:/";
    }
}
