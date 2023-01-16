package app.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * EnumClass for opportunity to get airports codes by country and city
 * @author Andrey Mitukov
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AirportCode {
    AAC("Egypt/Cairo"),
    AAH("Germany/Berlin"),
    ABC("Spain/Madrid"),
    ABR("United States/Chicago"),
    AJA("France/Paris"),
    AKJ("Japan/Tokyo"),
    ANE("Sweden/Stockholm"),
    LGA("United States/New York"),
    DME("Russian Federation/Moscow");

    String countryAndCity;

}
