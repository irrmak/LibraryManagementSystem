public interface Reservable {
    void reserve(String holderName);
    void cancelReservation();
    boolean isReserved();
    String getReservationHolder();
}