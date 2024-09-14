package ru.babenko.console;

import java.util.Optional;


public interface ScenarioProvider {
    Optional<Scenario> tryGetScenario();
}
