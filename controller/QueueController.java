package rw.auca.muyoboke.smarthealthclinic.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import rw.auca.muyoboke.smarthealthclinic.entity.QueueEntry;
import rw.auca.muyoboke.smarthealthclinic.service.QueueService;

import java.util.List;

@Controller
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping("/queue/live")
    public String liveQueue(Model model, Authentication authentication) {
        List<QueueEntry> queue = queueService.getLiveQueue();
        model.addAttribute("queue", queue);
        return "queue/live";
    }
}
