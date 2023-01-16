package app.entities;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * EnumClass for opportunity to get airports names by country and city
 * @author Andrey Mitukov
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AirportName {
        El_Arish_International_Airport("Egypt/Cairo"),
        Merzbrück_Airport("Germany/Berlin"),
        Albacete_Airport("Spain/Madrid"),
        Aberdeen_Regional_Airport("United States/Chicago"),
        Ajaccio_Napoleon_Bonaparte_Airport("France/Paris"),
        Akjoujt_Airport("Japan/Tokyo"),
        Stockholm_Arlanda_Airport("Sweden/Stockholm"),
        LaGuardia_Airport("United States/New York"),
        Moscow_Domodedovo_Airport("Russian Federation/Moscow");



        @JsonValue
        @Override
        public String toString() {
                return name().replace("_", " ");
        }

        String countryAndCity;

}
