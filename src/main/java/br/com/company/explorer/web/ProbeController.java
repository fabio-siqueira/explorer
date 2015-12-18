package br.com.company.explorer.web;

import br.com.company.explorer.domain.Probe;
import br.com.company.explorer.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by Fábio Siqueira on 12/18/15.
 */
@RestController
@RequestMapping(value = "/probes")
public class ProbeController {

    @Autowired
    NavigationService navigationService;

    @RequestMapping(method=GET)
    public Probe index() {
        return new Probe();
    }

    @RequestMapping(method=POST)
    public Probe save() {
        return new Probe();
    }

    @RequestMapping(value = "/{id}", method=GET)
    public Probe show(@PathVariable Long id) {
        return new Probe();
    }

    @RequestMapping(value = "/{id}", method=PUT)
    public Probe update(@PathVariable Long id) {
        return new Probe();
    }

    @RequestMapping(value = "/{id}", method=DELETE)
    public Probe delete(@PathVariable Long id) {
        return new Probe();
    }
}
