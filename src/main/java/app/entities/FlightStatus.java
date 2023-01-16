package app.entities;

public enum FlightStatus {
    ACCORDING_TO_PLAN("По плану"),
    DETAINED("Задержан"),
    CANCELLED("Отменен"),
    BOARDING("Посадка"),
    DEPARTED("Улетел"),
    DELAYED("Задерживается");

    private String translation;
    FlightStatus(String translation) {
        this.translation = translation;
    }
    public String getTranslation() {
        return translation;
    }
}
