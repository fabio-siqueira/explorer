package br.com.company.explorer.service;

import br.com.company.explorer.domain.CardinalDirection;
import br.com.company.explorer.domain.Probe;
import br.com.company.explorer.exception.InvalidParametersException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Fábio Siqueira on 12/17/15.
 */
@Component
public class NavigationService {

    public static final List<String> ACCEPTED_COMMANDS = Arrays.asList("R", "L", "M");


    public String move(Integer currentLatitude, Integer currentLongitude, CardinalDirection currentDirection,
                       Integer maxLat, Integer maxLng, List<String> commands) throws InvalidParametersException {

        Probe probe = new Probe(currentLatitude, currentLongitude, currentDirection);

        for(String command : commands) {

            if (!ACCEPTED_COMMANDS.contains(command)) {
                throw new InvalidParametersException("Parameter '" + commands + "' is not allowed.");
            }

            switch (command) {
                case "R":
                    turnRight(probe);
                    break;

                case "L":
                    turnLeft(probe);
                    break;

                case "M":
                    goAhead(probe, maxLat, maxLng);

            }
        }

        return probe.toString();
    }

    public void goAhead(Probe probe, Integer maxLat, Integer maxLng) {

        switch (probe.getDirection()) {

            case NORTH:
                if (probe.getLongitude() + 1 <= maxLng) {
                    probe.setLongitude(probe.getLongitude() + 1);
                }
                break;

            case EAST:
                if (probe.getLatitude() + 1 <= maxLat) {
                    probe.setLatitude(probe.getLatitude() + 1);
                }
                break;

            case SOUTH:
                if (probe.getLongitude() - 1 >= 0) {
                    probe.setLongitude(probe.getLongitude() - 1);
                }
                break;

            case WEST:
                if (probe.getLatitude() - 1 >= 0) {
                    probe.setLatitude(probe.getLatitude() - 1);
                }
        }
    }

    public void turnLeft(Probe probe) {

        switch (probe.getDirection()) {

            case NORTH:
                probe.setDirection(CardinalDirection.WEST);
                break;

            case WEST:
                probe.setDirection(CardinalDirection.SOUTH);
                break;

            case SOUTH:
                probe.setDirection(CardinalDirection.EAST);
                break;

            case EAST:
                probe.setDirection(CardinalDirection.NORTH);

        }
    }

    public void turnRight(Probe probe) {

        switch (probe.getDirection()) {

            case NORTH:
                probe.setDirection(CardinalDirection.EAST);
                break;

            case EAST:
                probe.setDirection(CardinalDirection.SOUTH);
                break;

            case SOUTH:
                probe.setDirection(CardinalDirection.WEST);
                break;

            case WEST:
                probe.setDirection(CardinalDirection.NORTH);

        }
    }

}
