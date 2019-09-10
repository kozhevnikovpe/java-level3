package Lesson5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Race {

        private ArrayList<Stage> stages;
        public ArrayList<Stage> getStages() { return stages; }
        private ArrayList<Car> cars = new ArrayList<>();
        public ArrayList<Car> getCars() { return cars; }
        CountDownLatch startLatch;
        CountDownLatch finishLatch;
        AtomicInteger place = new AtomicInteger(0);

        public Race(Stage... stages) {
            this.stages = new ArrayList<>(Arrays.asList(stages));
        }

        public void add(Car car){
            cars.add(car);
        }

        public void runRace(){
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

            startLatch = new CountDownLatch(cars.size());
            finishLatch = new CountDownLatch(cars.size());

            for (Car car: cars){
                car.start();
            }
            try {
                startLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            try {
                finishLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        }

        public void startCar(Car car){
            try {
                System.out.println(car.getName() + " готовится");
                Thread.sleep(500 + (int)(Math.random() * 800));
                System.out.println(car.getName() + " готов");
                startLatch.countDown();
                startLatch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void stagesCar(Car car){
            for (Stage stage: stages ) {
                stage.go(car);
            }
        }

        public void finishCar(Car car){
            try {
                place.incrementAndGet();
                System.out.println(car.getName() + " занял "+place + " место");
                finishLatch.countDown();
                startLatch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
