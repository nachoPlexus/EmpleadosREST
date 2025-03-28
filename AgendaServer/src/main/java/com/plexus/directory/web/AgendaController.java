package com.plexus.directory.web;

import com.plexus.directory.facade.AgendaFacade;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080")
@RequestMapping("/agenda/employees")
public class AgendaController {
    private final AgendaFacade facade;
    public AgendaController(AgendaFacade facade) {
        this.facade = facade;
    }


}
