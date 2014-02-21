package fr.xebia.codeelevator;

public class ElevatorEngine {
    public Command nextCommand() {
        System.out.format("nextCommand : %s%n", Command.NOTHING);
        return Command.NOTHING;
    }

    public void call(int atFloor, Direction to) {
        System.out.format("call(%d, %s)%n", atFloor, to);
    }

    public void go(int floorToGo) {
        System.out.format("go(%d)%n", floorToGo);
    }

    public void userHasEntered() {
        System.out.format("userHasEntered%n");
    }

    public void userHasExited() {
        System.out.format("userHasExited%n");
    }

    public void reset(String cause) {
        System.out.format("reset(%s)%n", cause);
    }
}
