package com.parking.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.parking.entites.ParkingSpot;
import com.parking.entites.Ticket;
import com.parking.entites.Vehicle;
import com.parking.util.ParkingSpotSize;
public class ParkingLotService {
	private int numSmall;
	private int numMedium;
	private int numLarge;
   
	// Map to store parking spots and corresponding tickets
    private Map<ParkingSpot, Ticket> parkingMap;
    
	// Method to initialize the parking lot with spots
    public ParkingLotService(int numSmall, int numMedium, int numLarge) {
    	parkingMap = new HashMap<>();

        for (int i = 1; i <= numSmall; i++) {
            parkingMap.put(new ParkingSpot(i, ParkingSpotSize.SMALL ), null);
        }

        for (int i = 1; i <= numMedium; i++) {
            parkingMap.put(new ParkingSpot(i, ParkingSpotSize.MEDIUM), null);
        }

        for (int i = 1; i <= numLarge; i++) {
            parkingMap.put(new ParkingSpot(i, ParkingSpotSize.LARGE), null);
        }
    }
    public void saveTicketAndParkingSpot(ParkingSpot ps, Ticket t){
    	parkingMap.put(ps,t);
    }
    
    public boolean isEmpty() {
        for (ParkingSpot spot : parkingMap.keySet()) {
            if (!spot.isOccupied()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isFull() {
        for (ParkingSpot spot : parkingMap.keySet()) {
            if (!spot.isOccupied()) {
                return false; // If any spot is unoccupied, parking lot is not full
            }
        }
        return true;
    }
    
    public ParkingSpot nearestSpot() {
        for (ParkingSpot spot : parkingMap.keySet()) {
        	if (!spot.isOccupied()) {
                return spot;
            }
        }
        return null; 
    }
    public ParkingSpot parkVehicle(Vehicle vehicle) {
        for (ParkingSpot spot : parkingMap.keySet()) {
            if (!spot.isOccupied() && canVehicleFitInSpot(vehicle, spot)) {
                spot.parkVehicle(vehicle);
                return spot;
            }
        }
        return null; // No available spot
    }
  
  
    public boolean canVehicleFitInSpot(Vehicle vehicle, ParkingSpot spot) {
        switch (vehicle.getSize()) {
            case SMALL:
                return true;
            case MEDIUM:
                return spot.getSize() != ParkingSpotSize.SMALL;
            case LARGE:
                return spot.getSize() == ParkingSpotSize.LARGE;
            default:
                return false;
        }
    }
    	// Method to retrieve a vehicle based on the ticket number
        public Vehicle retrieveVehicle(String ticketNumber) {
        	for (Map.Entry<ParkingSpot, Ticket> entry : parkingMap.entrySet()) {
            	Ticket ticket = entry.getValue();
                if (ticket != null && ticket.getTicketId().equalsIgnoreCase(ticketNumber)) {
                    ParkingSpot spot = entry.getKey();
                    Vehicle retrievedVehicle = spot.getParkedVehicle();
                    spot.vacateSpot(); // Vacate the spot
                    parkingMap.put(spot, null); // Update the parking map
                    return retrievedVehicle;
                }
            }
            return null; // Return null if the ticket number is not found
        }
    }
    

	

