import java.util.Random;
import java.util.Scanner;

class Vehicle {
    private int speed;
    public Vehicle(int speed) {
        this.speed = speed;
    }
    public Vehicle() {
        this.speed = 5;
    }
    public int getSpeed()
    {
        return this.speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    void start() {
        System.out.println("Vehicle is starting...");
    }
}

class Car extends Vehicle {
    private boolean running;
    public Car()
    {
        this.setSpeed(2);
        this.running = false;
    }
    public Car(int speed)
    {
        this.setSpeed( (speed == -1) ? 2: speed);
    }
    public Car(boolean running) {
        super();
        this.running = running;
    }
    public boolean getRunning() { return this.running; }
    public void setRunning(boolean running) {this.running = running;}

    void start() {
        System.out.println("Car is starting.");
    }

    void drive() {
        super.start();
        this.start();
        this.setRunning(true);
        System.out.println("Car is now driving!");
    }
}

public class Main
{
    public static void main(String[] args)
    {
        Car myCar = new Car();
        myCar.drive();
        System.out.println(myCar.getSpeed());
        System.out.println(myCar.getRunning());

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        boolean exit = false;
        while(!exit)
        {
            System.out.println("Enter your choice\n" +
                    "1 - jump\n" +
                    "0 - exit");
            int selection = scanner.nextInt();
            scanner.nextLine();

            switch(selection) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    System.out.println("The car jumped!");
                    break;
                default:
                    System.out.println("Invalid selection!");
                    break;
            }

        }
        System.out.println("You just stopped jumping.");

    }

}