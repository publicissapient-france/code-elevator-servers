package fr.xebia.codeelevator;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElevatorEngineTest {
    @Test
    public void should_always_returns_nothing() {
        ElevatorEngine elevatorEngine = new ElevatorEngine();

        Command nextCommand = elevatorEngine.nextCommand();

        assertThat(nextCommand).isEqualTo(Command.NOTHING);
    }
}
