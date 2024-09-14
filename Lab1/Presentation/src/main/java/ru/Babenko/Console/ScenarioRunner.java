package ru.babenko.console;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.Vector;

@Component
@Scope("prototype")
public class ScenarioRunner {
    private final List<ScenarioProvider> scenarioProviders;

    public ScenarioRunner(List<ScenarioProvider> scenarioProviders) {
        this.scenarioProviders = scenarioProviders;
    }

    public void run() {
        var scenarioVector = new Vector<Scenario>();

        for (var provider : scenarioProviders) {
            var scenario = provider.tryGetScenario();
            scenario.ifPresent(scenarioVector::add);
        }

        System.out.println("Choose scenario:");

        for (int i = 0; i < scenarioVector.size(); i++) {
            System.out.println(i + 1 + ". " + scenarioVector.get(i).getScenarioName());
        }

        int scenarioNumber = new Scanner(System.in).nextInt();

        if (scenarioNumber < 1 || scenarioNumber > scenarioVector.size()) {
            System.out.println("Invalid scenario number");
            return;
        }

        scenarioVector.get(scenarioNumber - 1).run();
    }
}
