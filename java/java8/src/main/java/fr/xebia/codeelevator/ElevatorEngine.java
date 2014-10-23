package fr.xebia.codeelevator;

import java.util.ArrayList;
import java.util.List;
import fr.xebia.codeelevator.DoorsStatus.DoorsStatus;

public class ElevatorEngine {

    private  int currentFloor = 0;
    private  DoorsStatus doorsStatus = DoorsStatus.Closed;
    private  PeopleAtFloors peopleAtFloors = new PeopleAtFloors();
    private final List<Integer> stagesToGo = new ArrayList<>();

    public ElevatorEngine() {
    }



    public ElevatorEngine(int currentFloor, DoorsStatus doorsStatus, PeopleAtFloors peopleAtFloors) {
        this.currentFloor = currentFloor;
        this.doorsStatus = doorsStatus;
        this.peopleAtFloors = peopleAtFloors;
    }


    public Command nextCommand() {
        Command nextCommand = Command.NOTHING;
        if (!stagesToGo.isEmpty()) {
            return Command.UP;
        }
        switch (doorsStatus) {
            case Opened:
                nextCommand = Command.CLOSE;
                doorsStatus = DoorsStatus.Closed;
                break;
            case Closed:
                if (peopleAtFloors.pplInElevatorAtFloor0 != 0) {
                    nextCommand = Command.OPEN;
                    doorsStatus = DoorsStatus.Opened;
                    peopleAtFloors.pplInElevatorAtFloor0 = 0;
                }
        }
        System.out.format("nextCommand : %s%n", nextCommand);
        return nextCommand;
    }

    public void call(int atFloor, Direction to) {
        System.out.format("call(%d, %s)%n", atFloor, to);

        if (atFloor == 0 && to.equals(Direction.UP)) {
            peopleAtFloors.pplInElevatorAtFloor0++;
        }
    }

    public void go(int floorToGo) {
        stagesToGo.add(floorToGo);
        System.out.format("go(%d)%n", floorToGo);
    }

    public void userHasEntered() {}

    public void userHasExited() {
        System.out.format("userHasExited%n");
    }

    public void reset(String cause) {
        System.out.format("reset(%s)%n", cause);
    }

    boolean isAtFloor(int floor) {
        return true;
    }

    public DoorsStatus getDoorsStatus() {
        return doorsStatus;
    }
}
