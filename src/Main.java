import java.util.Random;
import java.util.Scanner;

//this code demonstrates some features of inheritance and constructors

class Vehicle {
    private int speed;

    //notice we have two constructors. one with an argument "public Vehicle(int speed)", and one with no args "public Vehicle()".
    //the compiler knows which one to use based on how you create your vehicle object. For example:
    // Vehicle myVehicle = new Vehicle(3); //putting this in main will create an instance of Vehicle with speed = 3
    // Vehicle myVehicle = new Vehicle(); //putting this in main will create an instance of Vehicle with speed = 5
    public Vehicle(int speed) {
        this.speed = speed;
    }
    public Vehicle() {
        this.speed = 5;
    }

    //Here is the getter and setter for speed
    public int getSpeed()
    {
        return this.speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //this function is overridden by Car.start() below.
    void start() {
        System.out.println("Vehicle is starting...");
    }
}

class Car extends Vehicle {
    //By saying that the Car class extends Vehicle, we give the Car everything that Vehicle has (a speed, and the start function)
    private boolean running;

    //again, we have an overloaded constructor with three options, "public Car()", "public Car(int speed)", and "public Car(boolean running".
    //you can make as many constructors as you want, as long as they all receive different arguments.
    public Car()
    {
        //this.setSpeed(2); references the Vehicle class's setSpeed() method.
        this.setSpeed(2);
        //we used the setter because Vehicle.speed is private
        //if Vehicle.speed were public or protected, then we could just write:
        //   this.speed = 2;
        this.running = false;
    }
    public Car(int speed)
    {
        //this is an example of a ternary operator. It's the same as...
        // if (speed == -1) {
        //     this.setSpeed(2);
        // }
        // else {
        //     this.setSpeed(speed)
        // }
        this.setSpeed( (speed == -1) ? 2: speed);
    }
    public Car(boolean running) {
        //in this case, we call super(), which invokes the Vehicle constructor, setting speed to 5.
        // since there are multiple constructors for Vehicle, you can also call the other ones, i.e. "super(2)" for the constructor with arguments.
        super();
        this.running = running;
    }
    //here's the getter and setter for running
    public boolean getRunning() { return this.running; }
    public void setRunning(boolean running) {this.running = running;}

    // here we have overridden the Vehicle.start method.
    void start() {
        System.out.println("Car is starting.");
    }

    void drive() {
        //since Vehicle.start() is overridden by Car.start(), we can call either one here.
        //super.start() calls the start method in Vehicle, because that's the superclass (the one Car extends)
        super.start();
        //this.start() calls the start method in Car. 
        //If we didn't have a start method in Car, then this.start() and super.start() would do the same thing.
        this.start();
        
        //since we're driving, let's set running to true.
        this.setRunning(true);
        System.out.println("Car is now driving!");
    }
}

public class Main
{
    public static void main(String[] args)
    {
        //making the car object with the no-arg constructor
        Car myCar = new Car();
        //myCar.drive() should output... 
        // "Vehicle is starting" because of the line super.start()
        // "Car is starting" because of the line this.start()
        // "Car is now driving" because of the print in the drive function.
        myCar.drive();
        System.out.println(myCar.getSpeed()); //should be 2
        System.out.println(myCar.getRunning()); // should be true

        //getting ready to take some input
        Scanner scanner = new Scanner(System.in);
        //totally forgot to use this random number generator. Let's do it next class.
        //if you're interested, you would use it like "int myNumber = random.nextInt(100)" for a number 0-99
        Random random = new Random();

        //for fun, we'll let the user tell the car to jump or something.
        boolean exit = false;
        while(!exit)
        {
            System.out.println("Enter your choice\n" +
                    "1 - jump\n" +
                    "0 - exit");
            int selection = scanner.nextInt();
            scanner.nextLine();

            //demonstrating the switch statement
            switch(selection) {
                    // each case ends with "break" because otherwise it will just continue to the next case.
                    //try removing "break" from case 1: and see what happens when you jump.
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
        //let the user know the program is done.
        System.out.println("You just stopped jumping.");

    }

}
