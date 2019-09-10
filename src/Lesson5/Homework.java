package Lesson5;

public class Homework {

        public static final int CARS_COUNT = 4;
        public static void main(String[] args) {

            Race race = new Race(new Road(60), new Tunnel(CARS_COUNT/2), new Road(40));
            for (int i = 1; i <= CARS_COUNT; i++) {
                Car car = new Car(race, 20 + (int) (Math.random() * 10), "Участник "+i);
                race.add(car);
            }

            race.runRace();
        }
    }


