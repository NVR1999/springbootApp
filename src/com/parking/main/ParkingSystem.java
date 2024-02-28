5package com.parking.main;
import java.util.Random;
import java.util.Scanner;
import com.parking.entites.ParkingSpot;
import com.parking.entites.Ticket;
import com.parking.entites.Vehicle;
import com.parking.service.ParkingLotService;
import com.parking.util.VehicleSize;

public class ParkingSystem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Parking System!");

        System.out.print("Enter the total number of small parking spots: ");
        int numSmall = input.nextInt();
        System.out.print("Enter the total number of medium parking spots: ");
        int numMedium = input.nextInt();
        System.out.print("Enter the total number of large parking spots: ");
        int numLarge = input.nextInt();
        

        ParkingLotService parkingLot = new ParkingLotService(numSmall, numMedium, numLarge);

        while (true) {
            System.out.println("\n1.Park a vehicle");
            System.out.println("2. Find Nearest spot for parking");
            System.out.println("3. Check status for parking");
            System.out.println("4. Retrieving Vehicle from Parking");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    parkVehicle(parkingLot, input);
                    break;
                case 2:
                    findSpotForValet(parkingLot);
                    break;
                case 3:
                	checkParkingLotStatus(parkingLot);
                	break;
                case 4:
                	retrieveVehicle(parkingLot, input);
                	break;
                case 5:
                    System.out.println("Exiting Parking System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
    // code for parking a vehicle and generating a ticket
    private static void parkVehicle(ParkingLotService parkingLot, Scanner scanner) {
        System.out.print("Enter vehicle license plate: ");
        String licensePlate = scanner.next();

        System.out.println("Choose vehicle size: ");
        System.out.println("1. Small");
        System.out.println("2. Medium");
        System.out.println("3. Large");
        System.out.print("Enter your choice: ");
        int sizeChoice = scanner.nextInt();

        VehicleSize size;
        switch (sizeChoice) {
            case 1:;
                size = VehicleSize.SMALL;
                break;
            case 2:
                size = VehicleSize.MEDIUM;
                break;
            case 3:
                size = VehicleSize.LARGE;
                break;
            default:
                System.out.println("Invalid size choice.");
                size = VehicleSize.SMALL;
        }

        Vehicle vehicle = new Vehicle(licensePlate, size);
        // calling parkVehicle method in ParkingLotService
        ParkingSpot spot = parkingLot.parkVehicle(vehicle);

        if (spot != null) {
        	 Random random = new Random(); 
        	 //random number generating for ticket
             int rand_ticket = random.nextInt(1000);
             //random number generating for spot
             int rand_spot = random.nextInt(1000);
            Ticket ticket = new Ticket("T"+rand_ticket, vehicle.getLicensePlate());
            ParkingSpot pSpot = new ParkingSpot();
            pSpot.setSpotNumber(rand_ticket);
            pSpot.setParkedVehicle(vehicle);
            pSpot.setOccupied(true);
            System.out.println("Your Vehicle is parked at spot " + rand_spot);
            System.out.println("Ticket number is : " + ticket.getTicketId() + " and Vehicle number is : " + ticket.getVehicleNo());
            parkingLot.saveTicketAndParkingSpot(pSpot, ticket);
        } else {
            System.out.println("Parking lot is full. Unable to park the vehicle.");
        }
    }
    
    //code for finding a spot for valet parking
    private static void findSpotForValet(ParkingLotService parkingLot) {
    	Random random = new Random(); 
    	int rand_spot = random.nextInt(1000);
        ParkingSpot spot = parkingLot.nearestSpot();
        if (spot != null) {
            System.out.println("Park a vehicle at spot " + rand_spot);
        } else {
            System.out.println("Parking lot is full. No spot available for Valet parking.");
        }
    }
    
    private static void checkParkingLotStatus(ParkingLotService parkingLot) {
        if (parkingLot.isFull()) {
            System.out.println("Parking Lot is Full.");
        } else if (parkingLot.isEmpty()) {
            System.out.println("Parking Lot is Empty.");
        } else {
            System.out.println("Parking Lot is partially occupied.");
        }
    }
    
    private static void retrieveVehicle(ParkingLotService parkingLot, Scanner input) {
    	System.out.print("Enter the ticket number: ");
        String ticketNumber = input.next();

        // calling retrieveVehicle method in ParkingLotService
        Vehicle retrievedVehicle = parkingLot.retrieveVehicle(ticketNumber);

        if (retrievedVehicle != null) {
            System.out.println("Vehicle with license plate " + retrievedVehicle.getLicensePlate() + " retrieved successfully.");
        } else {
            System.out.println("Invalid ticket number. Unable to retrieve the vehicle.");
        }
    }
   
}
