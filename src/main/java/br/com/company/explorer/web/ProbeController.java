package br.com.company.explorer.web;

import br.com.company.explorer.domain.Land;
import br.com.company.explorer.domain.LandRepository;
import br.com.company.explorer.domain.Probe;
import br.com.company.explorer.domain.ProbeRepository;
import br.com.company.explorer.exception.LandNotFoundException;
import br.com.company.explorer.exception.ProbeNotFoundException;
import br.com.company.explorer.service.NavigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Fábio Siqueira on 12/18/15.
 */
@RestController
@RequestMapping(value = "lands/{landId}/probes", produces="application/json;charset=UTF-8")
public class ProbeController {

    @Autowired
    NavigationService navigationService;

    @Autowired
    ProbeRepository probeRepository;

    @Autowired
    LandRepository landRepository;

    @RequestMapping(method=GET)
    public Set<Probe> index(@PathVariable Long landId) {
        Land land = landRepository.findOne(landId);
        if (land == null) {
            throw new LandNotFoundException(landId);
        }
        return landRepository.findOne(landId).getProbes();
    }

    @RequestMapping(method=POST, consumes="application/json;charset=UTF-8")
    public ResponseEntity<?> save(@PathVariable Long landId, @RequestBody Probe input) {
        Land land = landRepository.findOne(landId);
        input.setLand(land);
        Probe probe = probeRepository.save(input);
        return new ResponseEntity(probe, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id}", method=GET)
    public Probe show(@PathVariable Long landId, @PathVariable Long id) {
        Land land = landRepository.findOne(landId);
        if (land == null) {
            throw new LandNotFoundException(landId);
        }
        Probe probe = land.getProbeById(id);
        if (probe == null) {
            throw new ProbeNotFoundException(id);
        }
        return land.getProbeById(id);
    }

    @RequestMapping(path = "/{id}", method=PUT, consumes="application/json;charset=UTF-8")
    public ResponseEntity<?> update(@PathVariable Long landId, @PathVariable Long id, @RequestBody Probe update) {
        Land land = landRepository.findOne(landId);
        if (land == null) {
            throw new LandNotFoundException(landId);
        }
        Probe probe = land.getProbeById(id);
        if (probe == null) {
            throw new ProbeNotFoundException(id);
        }

        probe.setLatitude(update.getLatitude());
        probe.setLongitude(update.getLongitude());
        probe.setDirection(update.getDirection());

        probeRepository.save(probe);

        return new ResponseEntity(probe, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method=DELETE)
    public ResponseEntity<?> delete(@PathVariable Long landId, @PathVariable Long id) {
        Land land = landRepository.findOne(landId);
        if (land == null) {
            throw new LandNotFoundException(landId);
        }
        Probe probe = land.getProbeById(id);
        if (probe == null) {
            throw new ProbeNotFoundException(id);
        }
        probeRepository.delete(probe.getId());
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "/{id}/move", method=POST, consumes="application/json;charset=UTF-8")
    public ResponseEntity<?> move(@PathVariable Long landId, @PathVariable Long id, @RequestBody RequestWrapper wrapper) {
        Land land = landRepository.findOne(landId);
        if (land == null) {
            throw new LandNotFoundException(landId);
        }
        Probe probe = land.getProbeById(id);
        if (probe == null) {
            throw new ProbeNotFoundException(id);
        }

        navigationService.move(probe, wrapper.getCommands());
        probeRepository.save(probe);

        return new ResponseEntity(probe, HttpStatus.OK);
    }

}

class RequestWrapper implements Serializable {
    private List<String> commands;

    public RequestWrapper() {
        super();
    }

    public RequestWrapper(List<String> commands) {
        this.commands = commands;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}

