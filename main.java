import java.util.*;

class car {
    private String carid;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public car(String carid, String brand, String model, double basePricePerDay) {// parametrized constructor
        this.carid = carid;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getcarid() {
        return carid;
    }

    public String getbrand() {
        return brand;
    }

    public String getmodel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class customer {
    private String customerId;
    private String name;

    public customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;

    }

    public String getcustomerId() {
        return customerId;
    }

    public String getname() {
        return name;
    }

}

class Rental {
    private car car;
    private customer customer;
    private int days;

    public Rental(car car, customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public car getcar() {
        return car;
    }

    public customer getcustomer() {
        return customer;
    }

    public int getdays() {
        return days;
    }
}

class CarRentalSystem {
    private List<car> cars;
    private List<customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addcar(car car) {
        cars.add(car);
    }

    public void addcustomer(customer customer) {
        customers.add(customer);
    }

    public void rentcar(car car, customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not Availabel for Rent");
        }
    }

    public void returnCar(car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getcar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("Car return SucessFully...");
        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu(){
        Scanner scanner=new Scanner(System.in);

        while(true){
            System.out.println("****Car Rentals System****");
            System.out.println("1.Rent a Car.");
            System.out.println("2.Return a Car.");
            System.out.println("3.Exit.");
            System.out.println("Enter Your Choice:");

            int choice=scanner.nextInt();
            scanner.nextLine();
            if(choice==1){
                System.out.println("\n==Rent a Car==\n");
                System.out.println("Enter your Name:");
                String customerName=scanner.nextLine();


                System.out.println("\nAvailabel Cars:");
                for(car car:cars){
                    if(car.isAvailable()){
                      System.out.println(car.getcarid()+""+car.getbrand()+" "+car.getmodel());

                    }
                }
                System.out.println("\nEnter the car Id you want to rent: ");
                String carid=scanner.nextLine();

                System.out.println("Enter Number of days for Rental:");
                int rentalDays=scanner.nextInt();
                scanner.nextLine();

                customer newCustomer=new customer("CUS" +(customers.size()+1),customerName);
                addcustomer(newCustomer);

                car selectedcar=null;
                for(car car :cars){
                    if(car.getcarid().equals(carid)&&car.isAvailable()){
                        selectedcar=car;
                        break;
                    }
                }
                if(selectedcar!=null){
                    double totalPrice=selectedcar.calculatePrice(rentalDays);
                    System.out.println("\n==Rental Information==\n");
                    System.out.println("Customer Id: "+newCustomer.getcustomerId());
                    System.out.println("Customer Name:"+newCustomer.getname());
                    System.out.println("Car:"+selectedcar.getbrand()+" "+selectedcar.getmodel());
                    System.out.println("Rental Days"+rentalDays);
                    System.out.printf("Total Price:$%.2f%n",totalPrice);

                    System.out.println("\nConfirm rental(Y/N):");
                    String confirm=scanner.nextLine();

                    if(confirm.equalsIgnoreCase( "Y")){
                        rentcar(selectedcar,newCustomer,rentalDays);
                        System.out.println("\nCar rented SucessFully....");
                    }else{
                        System.out.println("\n Rental Canceled");
                    }

                }else{
                    System.out.println("\n Invalid Car selection or Car not Availabel for rent");
                }
                
                

            }else if(choice==2){
                System.out.println("\n==Return a Car==\n");
                System.out.println("Enter Carid you want to return:");
                String carid=scanner.nextLine();

                car carToReturn= null;
                for(car car:cars){
                    if(car.getcarid().equals(carid)&&!car.isAvailable()){
                        carToReturn=car;
                        break;
                    }
                }

                    if(carToReturn!=null){
                        customer customer=null;
                        for(Rental rental:rentals){
                            if(rental.getcar()==carToReturn){
                                customer=rental.getcustomer();
                                break;
                            }
                        }
                        
                        if(customer!=null){
                            returnCar(carToReturn);
                            System.out.println("Car rented Sucessfully by "+customer.getname());

                        }else{
                            System.out.println("Car was not rented or rental information is missing");
                        }
                    }else{
                        System.out.println("Invalid car Id or car is not rented");
                    }
                    }else if(choice==3){
                        break;
                    }else{
                        System.out.println("Invalid choice. Please enter a valid option");
                    }
                }
                System.out.println("\nTHANKYOU FOR USING CAR RENTAL SYSTEM");
            }

}
public class main{
    public static void main(String[] args) {
        CarRentalSystem rentalSystem=new CarRentalSystem();

        car car1=new car("c001", "toyata", "canry", 60.0);
        car car2=new car("c002", "honda", "city", 70.0);
        car car3=new car("c003", "mahindra", "thar", 150.0);
        rentalSystem.addcar(car1);
        rentalSystem.addcar(car2);
        rentalSystem.addcar(car3);

        rentalSystem.menu();
    }
}
