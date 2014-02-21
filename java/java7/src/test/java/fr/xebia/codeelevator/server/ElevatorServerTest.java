package fr.xebia.codeelevator.server;

import com.sun.net.httpserver.HttpExchange;
import fr.xebia.codeelevator.Command;
import fr.xebia.codeelevator.Direction;
import fr.xebia.codeelevator.ElevatorEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ElevatorServerTest {
    @Mock
    private ElevatorEngine elevatorEngine;

    @Mock
    private HttpExchange exchange;

    @Test
    public void should_reset() throws IOException {
        ElevatorServer elevatorServer = new ElevatorServer(elevatorEngine);
        when(exchange.getRequestURI()).thenReturn(URI.create("http://localhost:8081/reset?cause=a+cause"));

        elevatorServer.handle(exchange);

        verify(elevatorEngine).reset("a cause");
    }

    @Test
    public void should_call() throws IOException {
        ElevatorServer elevatorServer = new ElevatorServer(elevatorEngine);
        when(exchange.getRequestURI()).thenReturn(URI.create("http://localhost:8081/call?atFloor=3&to=DOWN"));

        elevatorServer.handle(exchange);

        verify(elevatorEngine).call(3, Direction.DOWN);
    }

    @Test
    public void should_go() throws IOException {
        ElevatorServer elevatorServer = new ElevatorServer(elevatorEngine);
        when(exchange.getRequestURI()).thenReturn(URI.create("http://localhost:8081/go?floorToGo=1"));

        elevatorServer.handle(exchange);

        verify(elevatorEngine).go(1);
    }

    @Test
    public void should_nextCommand() throws IOException {
        ElevatorServer elevatorServer = new ElevatorServer(elevatorEngine);
        when(elevatorEngine.nextCommand()).thenReturn(Command.DOWN);
        when(exchange.getRequestURI()).thenReturn(URI.create("http://localhost:8081/nextCommand"));
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(exchange.getResponseBody()).thenReturn(responseBody);

        elevatorServer.handle(exchange);

        verify(elevatorEngine).nextCommand();
        assertThat(responseBody.toString()).isEqualTo("DOWN");
    }

    @Test
    public void should_userHasEntered() throws IOException {
        ElevatorServer elevatorServer = new ElevatorServer(elevatorEngine);
        when(exchange.getRequestURI()).thenReturn(URI.create("http://localhost:8081/userHasEntered"));

        elevatorServer.handle(exchange);

        verify(elevatorEngine).userHasEntered();
    }

    @Test
    public void should_userHasExited() throws IOException {
        ElevatorServer elevatorServer = new ElevatorServer(elevatorEngine);
        when(exchange.getRequestURI()).thenReturn(URI.create("http://localhost:8081/userHasExited"));

        elevatorServer.handle(exchange);

        verify(elevatorEngine).userHasExited();
    }
}
