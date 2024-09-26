package Task4 ;

import java.util.*;

class Room {
    private int roomNumber;
    private boolean isAvailable;
    private double price;

    // Constructor
    public Room(int roomNumber, double price) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.isAvailable = true;
    }

    // Getters and setters
    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailability(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public double getPrice() {
        return price;
    }
}

class Reservation {
    private static int nextReservationId = 1;
    private int reservationId;
    private String guestName;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;

    // Constructor
    public Reservation(String guestName, Room room, Date checkInDate, Date checkOutDate) {
        this.reservationId = nextReservationId++;
        this.guestName = guestName;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        room.setAvailability(false);
    }

    // Getters
    public int getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }
}

class Hotel {
    private List<Room> rooms;
    private List<Reservation> reservations;

    // Constructor
    public Hotel() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    // Add room to the hotel
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Search for available rooms
    public List<Room> searchAvailableRooms(Date checkInDate, Date checkOutDate) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    // Make a reservation
    public Reservation makeReservation(String guestName, Room room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(guestName, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    // View booking details
    public Reservation viewBookingDetails(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                return reservation;
            }
        }
        return null; // Reservation not found
    }
}

public class HotelReservation {
    public static void main(String args[]) {

        System.out.println("\nWelcome to Hotel VILLAGE ");
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();
        initializeRooms(hotel);

        while (true) {
            System.out.println("\n1. Search for available rooms");
            System.out.println("2. Make a reservation");
            System.out.println("3. View booking details");
            System.out.println("4. Exit");
            
            System.out.print("\nPlease select an option :  ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleSearchAvailableRooms(hotel);
                    break;
                case 2:
                    handleMakeReservation(hotel, scanner);
                    break;
                case 3:
                    handleViewBookingDetails(hotel, scanner);
                    break;
                case 4:
                    System.out.println("\nExiting.....\n");
                    return;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    private static void initializeRooms(Hotel hotel) {
        // Create rooms
        Room room1 = new Room(101, 100.0);
        Room room2 = new Room(102, 120.0);
        Room room3 = new Room(103, 150.0);
        Room room4 = new Room(104, 120.0);
        Room room5 = new Room(105, 160.0);

        // Add rooms to hotel
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        hotel.addRoom(room3);
        hotel.addRoom(room4);
        hotel.addRoom(room5);
    }

    private static void handleSearchAvailableRooms(Hotel hotel) {
        // Perform operations
        Date checkInDate = new Date(); // Current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkInDate);
        calendar.add(Calendar.DATE, 3); // Check out after 3 days
        Date checkOutDate = calendar.getTime();

        // Search for available rooms
        List<Room> availableRooms = hotel.searchAvailableRooms(checkInDate, checkOutDate);
        if (!availableRooms.isEmpty()) {
            System.out.println("\nAvailable rooms:");
            for (Room room : availableRooms) {
                System.out.println("Room Number: " + room.getRoomNumber() + ", Price: $" + room.getPrice());
            }
        } else {
            System.out.println("\nNo available rooms for the selected dates.");
        }
    }

    private static void handleMakeReservation(Hotel hotel, Scanner scanner) {
        System.out.print("\nEnter your name :  ");
        String guestName = scanner.nextLine();

        // Perform operations
        Date checkInDate = new Date(); // Current date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkInDate);
        calendar.add(Calendar.DATE, 3); // Check out after 3 days
        Date checkOutDate = calendar.getTime();

        // Search for available rooms
        List<Room> availableRooms = hotel.searchAvailableRooms(checkInDate, checkOutDate);
        if (!availableRooms.isEmpty()) {
            System.out.println("\nAvailable rooms:");
            for (Room room : availableRooms) {
                System.out.println("Room Number: " + room.getRoomNumber() + ", Price: $" + room.getPrice());
            }

            System.out.print("\nEnter the room number you want to book :  ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Room selectedRoom = null;
            for (Room room : availableRooms) {
                if (room.getRoomNumber() == roomNumber) {
                    selectedRoom = room;
                    break;
                }
            }

            if (selectedRoom != null) {
                // Make a reservation
                Reservation reservation = hotel.makeReservation(guestName, selectedRoom, checkInDate, checkOutDate);
                System.out.println("\nReservation made successfully. Your reservation ID is: " + reservation.getReservationId());
            } else {
                System.out.println("\nInvalid room number.");
            }
        } else {
            System.out.println("\nNo available rooms for the selected dates.");
        }
    }

    private static void handleViewBookingDetails(Hotel hotel, Scanner scanner) {
        System.out.print("\nEnter your reservation ID :  ");
        int reservationId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // View booking details
        Reservation reservation = hotel.viewBookingDetails(reservationId);
        if (reservation != null) {
            System.out.println("\nBooking details ==>>  \n");
            System.out.println("Guest Name :  " + reservation.getGuestName());
            System.out.println("Room Number :  " + reservation.getRoom().getRoomNumber());
            System.out.println("Check-in Date :  " + reservation.getCheckInDate());
            System.out.println("Check-out Date :  " + reservation.getCheckOutDate());
        } else {
            System.out.println("\nReservation not found.");
        }
    }
}