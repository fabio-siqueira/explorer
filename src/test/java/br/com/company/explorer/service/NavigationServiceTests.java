package br.com.company.explorer.service;

import br.com.company.explorer.ExplorerApplication;
import br.com.company.explorer.domain.CardinalDirection;
import br.com.company.explorer.domain.Probe;
import br.com.company.explorer.exception.InvalidParametersException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Fábio Siqueira on 12/17/15.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ExplorerApplication.class)
public class NavigationServiceTests {

    @Test
    public void move() {

        Integer maxLongitude = 5;
        Integer maxLatitude = 5;

        Integer currentLatitude = 1;
        Integer currentLongitude = 2;

        String currentDirection = "N";

        List<String> commands = Arrays.asList("L", "M", "L", "M", "L", "M", "L", "M", "M");

        NavigationService service = new NavigationService();
        String result = "";

        try {
            result = service.move(currentLatitude, currentLongitude, maxLatitude, maxLongitude, commands);

        } catch (InvalidParametersException e) {
            assert e == null;
        }

        assert result == "1 3 N";
    }

    @Test
    public void turnLeft() {
        NavigationService service = new NavigationService();
        Probe probe = new Probe();

        service.turnLeft(probe);
        assert probe.getDirection() == CardinalDirection.WEST;

        service.turnLeft(probe);
        assert probe.getDirection() == CardinalDirection.SOUTH;

        service.turnLeft(probe);
        assert probe.getDirection() == CardinalDirection.EAST;

        service.turnLeft(probe);
        assert probe.getDirection() == CardinalDirection.NORTH;

    }

    @Test
    public void turnRight() {
        NavigationService service = new NavigationService();
        Probe probe = new Probe();

        service.turnRight(probe);
        assert probe.getDirection() == CardinalDirection.EAST;

        service.turnRight(probe);
        assert probe.getDirection() == CardinalDirection.SOUTH;

        service.turnRight(probe);
        assert probe.getDirection() == CardinalDirection.WEST;

        service.turnRight(probe);
        assert probe.getDirection() == CardinalDirection.NORTH;

    }
}
