package ru.babenko;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.babenko.console.PresentationConfig;
import ru.babenko.console.ScenarioRunner;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(PresentationConfig.class);
        var scenarioRunner = context.getBean(ScenarioRunner.class);
        while (true) {
            scenarioRunner.run();
        }
    }
}