package br.com.company.explorer.service;

import br.com.company.explorer.domain.Probe;
import br.com.company.explorer.exception.InvalidParametersException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Fábio Siqueira on 12/17/15.
 */
@Service
public class NavigationService {

    public static final List<String> ACCEPTED_COMMANDS = Arrays.asList("R", "L", "M");

    public String move(Probe probe, List<String> commands) {

        for(String command : commands) {

            if (!ACCEPTED_COMMANDS.contains(command)) {
                throw new InvalidParametersException("Parameter '" + commands + "' is not allowed.");
            }

            switch (command) {
                case "R":
                    probe.turnRight();
                    break;

                case "L":
                    probe.turnLeft();
                    break;

                case "M":
                    probe.goAhead();
            }
        }

        return probe.toString();
    }
}
