//package app.util;
//
//import app.entities.Flight;
//import app.entities.SearchResult;
//import app.entities.Seat;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class SearchResultConverter {
//
//    private Long id;
//
//    private String aircraft;
//
//    private String from;
//
//    private String to;
//
//    private String date;
//
//    private int fare;
//
//    private int seats;
//
//    public List<List<SearchResultConverter>> convert(SearchResult searchResult) {
//        List<List<SearchResultConverter>> returnedList = new ArrayList<>();
//        List<SearchResultConverter> searchResultConverters = new ArrayList<>();
//        for (List<Flight> flightList : searchResult.getListSearch()) {
//            for (Flight f : flightList) {
//                SearchResultConverter searchResultConverter = new SearchResultConverter();
//                searchResultConverter.setId(f.getId());
//                searchResultConverter.setAircraft(f.getAircraft().getBrand() + " " + f.getAircraft().getModel());
//                searchResultConverter.setFrom(f.getFrom().getCity());
//                searchResultConverter.setTo(f.getTo().getCity());
//                searchResultConverter.setFare((f.getAircraft().getSeats().stream()
//                        .min(Comparator.comparingInt(Seat::getFare))).get().getFare());
//                searchResultConverter.setDate(f.getDepartureDateTime().toLocalTime().toString());
//                searchResultConverter.setSeats((int) f.getAircraft().getSeats().stream()
//                        .filter(seat -> seat.getIsSold().equals(false)).count());
//                searchResultConverters.add(searchResultConverter);
//            }
//            returnedList.add(searchResultConverters);
//        }
//        return returnedList;
//    }
//
//
//}
