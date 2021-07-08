package edu.school21.classes;

public class Car {
    private String manufacturer;
    private String model;
    private double mileage;

    public Car() {
    }

    public Car(String manufacturer, String model, Double mileage) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.mileage = mileage;
    }

    public void start() {
        System.out.println("Brrrrrrr");
    }

    public void start(String driver, String passenger) {
        System.out.printf("%s and %s went by car\n", driver, passenger);
    }

    @Override
    public String toString() {
        return "Car{" +
                "manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", mileage=" + mileage +
                '}';
    }
}
