package br.com.company.explorer.web;

import br.com.company.explorer.domain.CardinalDirection;
import br.com.company.explorer.domain.Probe;
import br.com.company.explorer.exception.InvalidParametersException;
import br.com.company.explorer.service.NavigationService;
import br.com.company.explorer.service.ProbeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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

    @Autowired
    ProbeRepository probeRepository;

    @RequestMapping(method=GET)
    public List<Probe> index() {
        return probeRepository.findAll();
    }

    @RequestMapping(method=POST)
    public Probe save(@RequestBody Probe input) {
        return probeRepository.save(input);
    }

    @RequestMapping(value = "/{id}", method=GET)
    public Probe show(@PathVariable Long id) {
        return probeRepository.getOne(id);
    }

    @RequestMapping(value = "/{id}", method=PUT)
    public Probe update(@PathVariable Long id) {
        List<String> commands = Arrays.asList("L", "M", "L", "M", "L", "M", "L", "M", "M");
        Probe probe = probeRepository.getOne(id);
        try {
            navigationService.move(probe, 5, 5, commands);
            probeRepository.save(probe);
        } catch (InvalidParametersException e) {
            e.printStackTrace();
        }
        return probe;
    }

    @RequestMapping(value = "/{id}", method=DELETE)
    public Probe delete(@PathVariable Long id) {
        return new Probe();
    }
}
