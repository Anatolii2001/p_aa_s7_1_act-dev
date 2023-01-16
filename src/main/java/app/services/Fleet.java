//package app.services;
//
//import app.entities.Aircraft;
//import app.entities.Seat;
//import app.util.Category;
//import org.springframework.stereotype.Service;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Class Fleet with factory-method to create Aircraft
// * with Seats and seats Category
// *
// * @author Eugene Kolyshev
// */
//@Service
//public class Fleet {
//
//    Aircraft aircraft = new Aircraft();
//
//
//    /**
//     * Create Aircraft Boeing737
//     */
//
//    public Aircraft createBoeing737() {
//        String[] seatCharacters = {"A", "B", "C", "D", "E", "F"};
//        return createAircraft("Boeing", "00001AA", "737-800", 2005,
//                5765, 840, 30, seatCharacters, 2);
//    }
//
//    /**
//     * Create Aircraft AirbusA321nx
//     */
//    public Aircraft createAirbusA321() {
//        String[] seatCharacters = {"A", "B", "C", "D", "E", "F"};
//        return createAircraft("Airbus", "00002AA", "A321nx", 2010,
//                6100, 845, 36, seatCharacters, 2);
//    }
//
//    /**
//     * Create Aircraft Embraer170
//     */
//    public Aircraft createEmbraer170() {
//        String[] seatCharacters = {"A", "C", "D", "F"};
//        return createAircraft("Embraer", "00003AA", "170",
//                1999, 3800, 830, 20, seatCharacters, 0);
//    }
//
//    /**
//     * Method to create an Aircraft
//     */
//    public Aircraft createAircraft(String brand, String name, String model,
//                                   Integer year, Integer distance, Integer speed,
//                                   Integer maxRange, String[] seatCharacters, Integer businessRange) {
//        aircraft.setBrand(brand);
//        aircraft.setName(name);
//        aircraft.setModel(model);
//        aircraft.setYearOfProduction(year);
//        aircraft.setFlightDistance(distance);
//        aircraft.setSpeed(speed);
//        Set<Seat> seatsAircraft = new HashSet<>();
//        for (int i = 1; i <= maxRange; i++) {
//            for (String s : seatCharacters) {
//                Seat seat = new Seat();
//                seat.setSeatNumber((i < 10) ? ("0" + i + s) : (i + s));
//                seat.setCategory((i < businessRange) ? (Category.BUSINESS) : (Category.ECONOMY));
//                seatsAircraft.add(seat);
//            }
//        }
//        aircraft.setSeats(seatsAircraft);
//        return aircraft;
//    }
//
//}
