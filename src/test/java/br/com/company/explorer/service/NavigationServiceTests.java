package br.com.company.explorer.service;

import br.com.company.explorer.ExplorerApplication;
import br.com.company.explorer.domain.CardinalDirection;
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

        Integer maxLongitude = 5;
        Integer maxLatitude = 5;

        Integer currentLatitude = 1;
        Integer currentLongitude = 2;

        CardinalDirection currentDirection = CardinalDirection.NORTH;

        List<String> commands = Arrays.asList("L", "M", "L", "M", "L", "M", "L", "M", "M");

        String result = "";

        try {
            result = navigationService.move(currentLatitude, currentLongitude, currentDirection, maxLatitude, maxLongitude, commands);

        } catch (InvalidParametersException e) {
            assertNull(e);
        }
        assertEquals("1 3 N", result);
    }

    @Test
    public void secondCase() {

        Integer maxLongitude = 5;
        Integer maxLatitude = 5;

        Integer currentLatitude = 3;
        Integer currentLongitude = 3;

        CardinalDirection currentDirection = CardinalDirection.EAST;

        List<String> commands = Arrays.asList("M", "M", "R", "M", "M", "R", "M", "R", "R", "M");

        String result = "";

        try {
            result = navigationService.move(currentLatitude, currentLongitude, currentDirection, maxLatitude, maxLongitude, commands);

        } catch (InvalidParametersException e) {
            assertNull(e);
        }
        assertEquals("5 1 E", result);
    }

    @Test(expected=InvalidParametersException.class)
    public void mustNotMove() throws InvalidParametersException{

        Integer maxLongitude = 5;
        Integer maxLatitude = 5;

        Integer currentLatitude = 3;
        Integer currentLongitude = 3;

        CardinalDirection currentDirection = CardinalDirection.EAST;

        List<String> commands = Arrays.asList("B", "M", "M", "R", "M", "M", "R", "M", "R", "R", "M");

        navigationService.move(currentLatitude, currentLongitude, currentDirection, maxLatitude, maxLongitude, commands);
    }

    @Test
    public void goAhead() {

        Probe probe = new Probe();

        Integer maxLongitude = 5;
        Integer maxLatitude = 5;

        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        assertSame(1, probe.getLongitude());

        probe.setDirection(CardinalDirection.EAST);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        assertSame(1, probe.getLatitude());

        probe.setDirection(CardinalDirection.SOUTH);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        assertSame(0, probe.getLongitude());

        probe.setDirection(CardinalDirection.WEST);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        assertSame(0, probe.getLatitude());

        probe.setDirection(CardinalDirection.NORTH);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);

        assertSame(maxLatitude, probe.getLongitude());

        probe.setDirection(CardinalDirection.EAST);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);
        navigationService.goAhead(probe, maxLatitude, maxLongitude);

        assertSame(maxLatitude, probe.getLatitude());
    }

    @Test
    public void turnLeft() {

        Probe probe = new Probe();

        navigationService.turnLeft(probe);
        assertSame(CardinalDirection.WEST, probe.getDirection());

        navigationService.turnLeft(probe);
        assertSame(CardinalDirection.SOUTH, probe.getDirection());

        navigationService.turnLeft(probe);
        assertSame(CardinalDirection.EAST, probe.getDirection());

        navigationService.turnLeft(probe);
        assertSame(CardinalDirection.NORTH, probe.getDirection());

        navigationService.turnLeft(probe);
        assertSame(CardinalDirection.WEST, probe.getDirection());

    }

    @Test
    public void turnRight() {

        Probe probe = new Probe();

        navigationService.turnRight(probe);
        assertSame(CardinalDirection.EAST, probe.getDirection());

        navigationService.turnRight(probe);
        assertSame(CardinalDirection.SOUTH, probe.getDirection());

        navigationService.turnRight(probe);
        assertSame(CardinalDirection.WEST, probe.getDirection());

        navigationService.turnRight(probe);
        assertSame(CardinalDirection.NORTH, probe.getDirection());

        navigationService.turnRight(probe);
        assertSame(CardinalDirection.EAST, probe.getDirection());
    }
}
