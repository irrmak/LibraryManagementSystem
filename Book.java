import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Book extends Material implements Borrowable, Reservable {

    private String author;
    private int borrowForDays = 30;
    private LocalDate borrowedOn = null;

    private boolean isBorrowed;
    private boolean isReserved;
    private String reservationHolder;

    public Book(String materialID, String title, int publicationYear, String author) {
        super(materialID, title, publicationYear);
        this.author = author;

        this.isBorrowed = false;
        this.isReserved = false;
        this.reservationHolder = null;
    }

    public String getAuthor() {
        return author;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public void setReserved(boolean reserved, String holderName) {
        isReserved = reserved;
        this.reservationHolder = holderName;
    }

    public int getRemainingDays() {
        if(!isBorrowed || borrowedOn == null) return borrowForDays;
        long days = borrowForDays - ChronoUnit.DAYS.between(borrowedOn, LocalDate.now());
        return Math.max(0,days);
    }

    @Override
    public void borrow() {
        if (isReserved) {
            System.out.println("Error: Book is currently reserved by " + reservationHolder + ".");
            return;
        }
        if (isBorrowed) {
            System.out.println("Error: Book is already borrowed.");
        } else {
            this.isBorrowed = true;
            this.borrowedOn = LocalDate.now();
            System.out.println("Success: '" + getTitle() + "' has been borrowed.");
        }
    }

    @Override
    public void returnItem() {
        if (!isBorrowed) {
            System.out.println("Error: This book is already in the library.");
        } else {
            this.isBorrowed = false;
            this.borrowedOn = null;
            System.out.println("Success: '" + getTitle() + "' has been returned.");
        }
    }

    @Override
    public boolean isAvailableToBorrow() {
        return !isBorrowed && !isReserved;
    }

    @Override
    public int getRemainingDays() {
        return 0;
    }

    @Override
    public void reserve(String holderName) {
        if (isReserved) {
            System.out.println("Error: Book is already reserved by " + this.reservationHolder + ".");
        } else {
            this.isReserved = true;
            this.reservationHolder = holderName;
            System.out.println("Success: '" + getTitle() + "' has been reserved for " + holderName + ".");
        }
    }

    @Override
    public void cancelReservation() {
        if (!isReserved) {
            System.out.println("Error: Book is not currently reserved.");
        } else {
            System.out.println("Success: Reservation for '" + getTitle() + "' by " + this.reservationHolder + " has been cancelled.");
            this.isReserved = false;
            this.reservationHolder = null;
        }
    }

    @Override
    public boolean isReserved() {
        return isReserved;
    }

    @Override
    public String getReservationHolder() {
        return reservationHolder;
    }

    @Override
    public String getDisplayDetails() {
        String status;
        if (isBorrowed) {
            status = "Borrowed";
        } else if (isReserved) {
            status = "Reserved by " + reservationHolder;
        } else {
            status = "Available";
        }
        return " | Author: " + author + " | Status: " + status;
    }

    @Override
    public String toFileFormat() {
        return "BOOK," + getMaterialID() + "," + getTitle() + "," + getPublicationYear() +
                "," + author + "," + isBorrowed + "," + isReserved + "," +
                (reservationHolder != null ? reservationHolder : "null");
    }
}