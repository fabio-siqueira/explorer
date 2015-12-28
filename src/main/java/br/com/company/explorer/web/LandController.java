package br.com.company.explorer.web;

import br.com.company.explorer.domain.Land;
import br.com.company.explorer.domain.LandRepository;
import br.com.company.explorer.exception.LandNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Fábio Siqueira on 12/18/15.
 */
@RestController
@RequestMapping(value = "/lands", produces="application/json;charset=UTF-8")
public class LandController {

    @Autowired
    LandRepository landRepository;

    @RequestMapping(method=GET)
    public List<Land> index() {
        return landRepository.findAll();
    }

    @RequestMapping(method=POST, consumes="application/json;charset=UTF-8")
    public ResponseEntity<?> save(@RequestBody Land input) {
        Land land = landRepository.save(input);
        return new ResponseEntity(land, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method=GET)
    public Land show(@PathVariable Long id) {
        Land land = landRepository.findOne(id);
        if (land == null) {
            throw new LandNotFoundException(id);
        }
        return land;
    }

    @RequestMapping(value = "/{id}", method=PUT, consumes="application/json;charset=UTF-8")
    public Land update(@PathVariable Long id, @RequestBody Land land) {
        Land update = landRepository.findOne(id);
        if (update == null) {
            throw new LandNotFoundException(id);
        }

        update.setTopLimit(land.getTopLimit());
        update.setRightLimit(land.getRightLimit());
        return landRepository.save(update);
    }

    @RequestMapping(value = "/{id}", method=DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Land land = landRepository.findOne(id);
        if (land == null) {
            throw new LandNotFoundException(id);
        }
        landRepository.delete(land);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}


