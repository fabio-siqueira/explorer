package br.com.company.explorer.domain;

import br.com.company.explorer.ExplorerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static br.com.company.explorer.domain.CardinalDirection.*;
import static org.junit.Assert.assertSame;

/**
 * Created by Fábio Siqueira on 12/29/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ExplorerApplication.class)
public class ProbeTests {

    Land land;

    @Before
    public void setup() throws Exception {
        land = new Land(5, 5);
    }

    @Test
    public void goAhead() {

        Probe probe = new Probe();
        probe.setLand(land);

        probe.goAhead();
        assertSame(1, probe.getLongitude());

        probe.setDirection(EAST);
        probe.goAhead();
        assertSame(1, probe.getLatitude());

        probe.setDirection(SOUTH);
        probe.goAhead();
        assertSame(Land.ZERO, probe.getLongitude());

        probe.setDirection(WEST);
        probe.goAhead();
        assertSame(Land.ZERO, probe.getLatitude());
    }


    @Test
    public void mustNotGoAhead() {
        Probe probe = new Probe(5, 5, NORTH, land);

        probe.goAhead();
        assertSame(land.getTopLimit(), probe.getLongitude());

        probe.setDirection(EAST);
        probe.goAhead();
        assertSame(land.getRightLimit(), probe.getLatitude());

        probe.setLongitude(Land.ZERO);
        probe.setLatitude(Land.ZERO);

        probe.setDirection(SOUTH);
        probe.goAhead();
        assertSame(Land.ZERO, probe.getLongitude());

        probe.setDirection(WEST);
        probe.goAhead();
        assertSame(Land.ZERO, probe.getLatitude());
    }

    @Test
    public void turnLeft() {

        Probe probe = new Probe();

        probe.turnLeft();
        assertSame(WEST, probe.getDirection());

        probe.turnLeft();
        assertSame(SOUTH, probe.getDirection());

        probe.turnLeft();
        assertSame(EAST, probe.getDirection());

        probe.turnLeft();
        assertSame(NORTH, probe.getDirection());

        probe.turnLeft();
        assertSame(WEST, probe.getDirection());

    }

    @Test
    public void turnRight() {

        Probe probe = new Probe();

        probe.turnRight();
        assertSame(EAST, probe.getDirection());

        probe.turnRight();
        assertSame(SOUTH, probe.getDirection());

        probe.turnRight();
        assertSame(WEST, probe.getDirection());

        probe.turnRight();
        assertSame(NORTH, probe.getDirection());

        probe.turnRight();
        assertSame(EAST, probe.getDirection());
    }
}
