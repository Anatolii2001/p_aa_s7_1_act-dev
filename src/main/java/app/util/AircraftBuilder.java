package app.util;

import app.entities.Aircraft;
import app.entities.CategoryType;
import app.entities.Seat;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Aircraft builder allows to fluent setting up any field of {@link Aircraft} including {@code Set<Seat>} by configuring
 * each section of interior separately by {@link CategoryType}.<br><br>
 *
 * The builder allows to configure:<br>
 * - id, board number, model, brand, production year and flying range of Aircraft.
 * - fare of seat's category<br>
 * - number of seats in each row<br>
 * - letters of each seat in their row.<br><br>
 *
 * All of these features based on the following abstraction:<br>
 * - A row of seats is {@code String}<br>
 * - String like "12ABCD" named "numbered word mask" and describe 12th row with 4 {@link  Seat} with letters
 * "A", "B", "C", "D".<br>
 * - String "ABCDEF" named "word mask" and applied only with additional {@code int} parameters describing
 * row's number.<br><br>
 *
 * For example, we can create an {@link Aircraft} with custom interior by following pseudocode:<br><br>
 * {@code Aircraft a1 = AircraftBuilder}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .newBuilder()}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .id(0L)}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .number("RA-12345")}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .brand("Airbus")}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .model("A310")}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .year(2008)}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .range(5600)}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .interior(categoryService.getByName("BUSINESS"))}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .headRows(2, "ABCD")}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .nextRow("3ABCDE")}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .fare(500)}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .and()}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .interior(categoryService.getByName("ECONOMY"))}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .rows(4, 6, "ABCDEF")}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .fare(150)}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .and()}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{@code .build();}<br><br>
 *
 * Custom interior will created:<br><br>
 *
 * Business-|-Economy<br>
 * |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;E | G G G<br>
 * |&nbsp;&nbsp;D D D | F F F<br>
 * |&nbsp;&nbsp;C C C | D D D<br>
 * |<br>
 * |&nbsp;&nbsp;B B B | C C C<br>
 * |&nbsp;&nbsp;A A A | B B B<br>
 * |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| A A A<br>
 * |_1&nbsp;&nbsp;2&nbsp;&nbsp;3&nbsp;&nbsp;4&nbsp;5&nbsp;6 <- row number.
 */
public class AircraftBuilder {
    private static Map<CategoryType, List<String>> interior;
    private InteriorBuilder interiorBuilder;
    private static Aircraft aircraft;

    public static AircraftBuilder newBuilder() {
        return newBuilder(null, null, null, null, null, null);
    }

    public static AircraftBuilder newBuilder(Long id, String boardNumber, String brand, String model,
                                             Integer productionYear, Integer flyingRange) {
        aircraft = new Aircraft(id, boardNumber, brand, model,
                productionYear, flyingRange, null);
        interior = new HashMap<>();
        return new AircraftBuilder();
    }

    public AircraftBuilder id(Long id) {
        aircraft.setId(id);
        return this;
    }

    public AircraftBuilder number(String boardNumber) {
        aircraft.setBoardNumber(boardNumber);
        return this;
    }

    public AircraftBuilder brand(String brand) {
        aircraft.setBrand(brand);
        return this;
    }

    public AircraftBuilder model(String model) {
        aircraft.setModel(model);
        return this;
    }

    public AircraftBuilder year(Integer productionYear) {
        aircraft.setProductionYear(productionYear);
        return this;
    }

    public AircraftBuilder range(Integer flyingRange) {
        aircraft.setFlyingRange(flyingRange);
        return this;
    }

    public InteriorBuilder interior(CategoryType category) {
        interiorBuilder = new InteriorBuilder(category);
        List<String> rows = new ArrayList<>();
        rows.add("fare0");
        interior.put(category, rows);
        return interiorBuilder;
    }

    /**
     *
     * @return an aircraft instance.
     * @throws IllegalArgumentException if a row naming mistake has been made: numbered mask word out of the format.
     */
    public Aircraft build() {
        Set<Seat> seats = new HashSet<>();
        Integer[] fare = {null};
        interior.forEach((type, list) -> list.forEach(mask -> {
            Pattern d = Pattern.compile("\\d+");
            Pattern w = Pattern.compile("\\D+");
            Matcher md = d.matcher(mask);
            Matcher mw = w.matcher(mask);
            if (!md.find() || !mw.find()) throw new IllegalArgumentException("Illegal argument: numbered mask word" +
                    " must have number and word-mask like \"12ABCDEF\" instead of \"" + mask + "\"");
            int rowNumber = Integer.parseInt(md.group());
            String maskWord = mw.group();
            if (maskWord.equals("fare")) {
                if (rowNumber != 0) fare[0] = rowNumber;
            } else {
                for (int i = 0; i < maskWord.length(); i++) {
                    seats.add(new Seat(
                            0L,
                            rowNumber + maskWord.substring(i, i + 1),
                            fare[0],
                            false,
                            false,
                            type,
                            aircraft));
                }
            }
        }));
        aircraft.setSeats(seats);
        return aircraft;
    }


    public class InteriorBuilder {

        private final CategoryType category;


        public InteriorBuilder(CategoryType category) {
            this.category = category;
        }

        public InteriorBuilder fare(int fare) {
            List<String> rows = interior.get(category);
            rows.set(0, "fare" + fare);
            return interiorBuilder;
        }

        /**
         * Adding a word mask string to certain row = rowNumber, for example for 12th row {@code row(12, "ABCDEF");}
         *
         * @param rowNumber is a number of seats in the row
         * @param wordMask  is a word is getting by subtraction all seat's letters. For example, we have 6 seats
         *                  on 12th row with letters from "A" to "F", word mask will be then "ABCDEF".
         */
        public InteriorBuilder row(int rowNumber, String wordMask) {
            List<String> rows = interior.get(category);
            rows.add(rowNumber + wordMask);
            return interiorBuilder;
        }

        /**
         * Adding a numbered word mask string, for example for 12th row
         * we should use {@code nextRow("12ABCD");}
         *
         * @param numberedWordMask is a word is getting by subtraction row number and all seat's letters. For example, we have 6 seats
         *                         on 12th row with letters from "A" to "F", numbered word mask will be then "12ABCDEF".
         */
        public InteriorBuilder nextRow(String numberedWordMask) {
            List<String> rows = interior.get(category);
            rows.add(numberedWordMask);
            return interiorBuilder;
        }

        /**
         * Adding a word mask to certain amount of head rows from 1st to {@code endHead} included.<br><br>
         * For example: for describing aircraft's business interior with six rows amount with the same
         * word mask each, we can use {@code rows(6, "ABCD");}
         *
         * @param endHead  the index of the last row to describe
         * @param wordMask is a word is getting by subtraction all seat's letters. For example, we have 6 seats
         *                 *                 on 12th row with letters from "A" to "F", word mask will be then "ABCDEF".
         */
        public InteriorBuilder headRows(int endHead, String wordMask) {
            List<String> rows = interior.get(category);
            for (int i = 1; i <= endHead; i++) {
                rows.add(i + wordMask);
            }
            return interiorBuilder;
        }


        /**
         * Adding a word mask to a range of rows from start index to end index both included.<br><br>
         * For example: for describe aircraft's economy interior has 22 rows from 7th to 28th with the same
         * word mask each, we can use {@code rows(7, 28, "ABCDEF");}
         *
         * @param startIndex is the index of the first row to describe {@code startIndex > 0}
         * @param endIndex   is the index of the last row to describe
         * @param wordMask   is a word is getting by subtraction all seat's letters. For example, we have 6 seats
         *                   on 12th row with letters from "A" to "F", word mask will be then "ABCDEF".
         * @throws IllegalArgumentException if {@code startIndex < 1}
         */
        public InteriorBuilder rows(int startIndex, int endIndex, String wordMask) {
            if (startIndex < 1) {
                throw new IllegalArgumentException("Start index of minimal row in an aircraft" +
                        " must be > 0 instead of " + startIndex);
            }
            List<String> rows = interior.get(category);
            for (int i = startIndex; i <= endIndex; i++) {
                rows.add(i + wordMask);
            }
            return interiorBuilder;
        }

        public AircraftBuilder and() {
            return AircraftBuilder.this;
        }
    }
}