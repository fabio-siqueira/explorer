package br.com.company.explorer.service;

import br.com.company.explorer.ExplorerApplication;
import br.com.company.explorer.domain.Land;
import br.com.company.explorer.domain.Probe;
import br.com.company.explorer.exception.InvalidParametersException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static br.com.company.explorer.domain.CardinalDirection.*;

/**
 * Created by Fábio Siqueira on 12/17/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ExplorerApplication.class)
public class NavigationServiceTests {

    @Autowired
    private NavigationService navigationService;

    @Test
    public void firstCase() {

        Probe probe = new Probe(1, 2, NORTH, new Land(5, 5));

        List<String> commands = Arrays.asList("L", "M", "L", "M", "L", "M", "L", "M", "M");

        String result = "";

        try {
            result = navigationService.move(probe, commands);

        } catch (InvalidParametersException e) {
            assertNull(e);
        }
        assertEquals("1 3 N", result);
    }

    @Test
    public void secondCase() {

        Probe probe = new Probe(3, 3, EAST, new Land(5, 5));

        List<String> commands = Arrays.asList("M", "M", "R", "M", "M", "R", "M", "R", "R", "M");

        String result = "";

        try {
            result = navigationService.move(probe, commands);

        } catch (InvalidParametersException e) {
            assertNull(e);
        }
        assertEquals("5 1 E", result);
    }

    @Test(expected=InvalidParametersException.class)
    public void mustNotMoveWithInvalidCommands() throws InvalidParametersException{

        Probe probe = new Probe(5, 5, EAST, new Land(5, 5));

        List<String> commands = Arrays.asList("B");
        navigationService.move(probe, commands);

    }

}
