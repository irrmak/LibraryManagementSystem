public interface Borrowable {
    void borrow();
    void returnItem();
    boolean isAvailableToBorrow();
    int getRemainingDays();
}