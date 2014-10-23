package fr.xebia.codeelevator;

import org.junit.Ignore;
import org.junit.Test;

import static fr.xebia.codeelevator.Direction.UP;
import static fr.xebia.codeelevator.DoorsStatus.DoorsStatus.Closed;
import static org.assertj.core.api.Assertions.assertThat;
import fr.xebia.codeelevator.DoorsStatus.DoorsStatus;

public class ElevatorEngineTest {

    @Test
    public void should_always_returns_nothing() {
        ElevatorEngine elevatorEngine = new ElevatorEngine();

        Command nextCommand = elevatorEngine.nextCommand();

        assertThat(nextCommand).isEqualTo(Command.NOTHING);
    }

    @Test
    public void should_return_floor_0_when_elevator_starts() {
        // given
        ElevatorEngine elevator = new ElevatorEngine();

        // when
        elevator.reset("Start");

        // then
        assertThat(elevator.isAtFloor(0)).isTrue();
        assertThat(elevator.nextCommand()).isEqualTo(Command.NOTHING);
    }

    @Test
    public void should_open_doors_when_elevator_is_at_floor_0_and_user_asks_for_it(){
        // given
        PeopleAtFloors peopleAtFloors = new PeopleAtFloors(0, 0, 0, 0, 0, 0);
        ElevatorEngine elevator = new ElevatorEngine(
                0,
                Closed,
                peopleAtFloors);
        elevator.call(0, UP);

        // when
        Command nextCommand = elevator.nextCommand();

        // then
        assertThat(nextCommand).isEqualTo(Command.OPEN);
        assertThat(peopleAtFloors.pplInElevatorAtFloor0).isEqualTo(0);
        assertThat(elevator.getDoorsStatus()).isEqualTo(DoorsStatus.Opened);
    }

    @Test
         public void should_close_the_doors_when_user_has_entered() {
        // given
        ElevatorEngine elevator = new ElevatorEngine(
                0,
                DoorsStatus.Opened,
                new PeopleAtFloors(1, 0, 0, 0, 0, 0));



        //when
        Command nextCommand = elevator.nextCommand();

        //then
        assertThat(nextCommand).isEqualTo(Command.CLOSE);

    }

    @Test
    public void should_have_the_doors_closed_after_a_close_command() {
        // given
        ElevatorEngine elevator = new ElevatorEngine(
                0,
                DoorsStatus.Opened,
                new PeopleAtFloors(1, 0, 0, 0, 0, 0));

        //when
        Command nextCommand = elevator.nextCommand();

        //then
        assertThat(nextCommand).isEqualTo(Command.CLOSE);
        assertThat(elevator.getDoorsStatus()).isEqualTo(Closed);
    }

    @Test
    public void should_goes_up_when_user_requests_it() {
        // given
        ElevatorEngine elevator = new ElevatorEngine(
                0,
                DoorsStatus.Closed,
                new PeopleAtFloors(0, 0, 0, 0, 0, 0));
        elevator.go(3);

        //when
        Command nextCommand = elevator.nextCommand();

        //then
        assertThat(nextCommand).isEqualTo(Command.UP);
        assertThat(elevator.getDoorsStatus()).isEqualTo(Closed);
    }

    @Test
    public void should_not_move_when_user_request_it_but_doors_are_opened() {
        // given
        ElevatorEngine elevator = new ElevatorEngine(
                0,
                DoorsStatus.Opened,
                new PeopleAtFloors(0, 0, 0, 0, 0, 0));
        elevator.go(3);

        //when
        Command nextCommand = elevator.nextCommand();

        //then
        assertThat(nextCommand).isEqualTo(Command.CLOSE);
        assertThat(elevator.getDoorsStatus()).isEqualTo(Closed);

    }

}
