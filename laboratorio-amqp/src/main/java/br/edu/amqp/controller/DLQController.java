
package br.edu.amqp.controller;

import br.edu.amqp.service.DLQService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DLQController {

    private final DLQService dlqService;

    public DLQController(DLQService dlqService) {
        this.dlqService = dlqService;
    }

    @PostMapping("/dlq/executar")
    public String executar(RedirectAttributes redirectAttributes) {

        try {
            redirectAttributes.addFlashAttribute(
                "mensagem",
                "Experimento de Dead Letter Queue finalizado!"
            );

            redirectAttributes.addFlashAttribute(
                "resultadosDLQ",
                dlqService.executarExperimento()
            );

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                "mensagem",
                "Erro ao executar DLQ: " + e.getMessage()
            );
        }

        return "redirect:/";
    }
}
