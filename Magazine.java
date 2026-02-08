import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Magazine extends Material implements Borrowable {

    private int issueNumber;
    private boolean isBorrowed;
    private int borrowForDays = 30;
    private LocalDate borrowedOn = null;

    public Magazine(String materialID, String title, int publicationYear, int issueNumber) {
        super(materialID, title, publicationYear);
        this.issueNumber = issueNumber;
        this.isBorrowed = false;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public int getRemainingDays() {
        if(!isBorrowed || borrowedOn == null) return borrowForDays;
        long days = borrowForDays - ChronoUnit.DAYS.between(borrowedOn, LocalDate.now());
        return Math.toIntExact(Math.max(0, days));
    }
    
    @Override
    public void borrow() {
        if (isBorrowed) {
             System.out.println("Error: Magazine is already borrowed.");
        } else {
             this.isBorrowed = true;
             this.borrowedOn = LocalDate.now();
             System.out.println("Success: '" + getTitle() + "' (Issue " + issueNumber + ") has been borrowed.");
        }
    }

    @Override
    public void returnItem() {
        if (!isBorrowed) {
            System.out.println("Error: This magazine is already in the library.");
        } else {
            this.isBorrowed = false;
            this.borrowedOn = null;
            System.out.println("Success: '" + getTitle() + "' (Issue " + issueNumber + ") has been returned.");
        }
    }

    @Override
    public boolean isAvailableToBorrow() {
        return !isBorrowed;
    }

    @Override
    public String getDisplayDetails() {
        String status = isBorrowed ? "Borrowed" : "Available";
        return " | Issue No: " + issueNumber + " | Status: " + status;
    }

    @Override
    public String toFileFormat() {
        return "MAGAZINE," + getMaterialID() + "," + getTitle() + "," + getPublicationYear() + 
               "," + issueNumber + "," + isBorrowed;
    }
}