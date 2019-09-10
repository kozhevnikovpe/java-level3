package Lesson5;

public class Car extends Thread {

    private Race race;
    private int speed;

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, String name) {
        this.race = race;
        this.speed = speed;
        setName(name);
    }

    @Override
    public void run() {
        race.startCar(this);
        race.stagesCar(this);
        race.finishCar(this);
    }
}
