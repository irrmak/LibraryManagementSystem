import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static Library library;
    private static Scanner scanner;

    public static void main(String[] args) {
        library = new Library();
        scanner = new Scanner(System.in);
        runMenu();
    }

    private static void runMenu() {
        boolean running = true;
        
        while (running) {
            printMenu();
            int choice = -1;

            try {
                String input = scanner.nextLine();
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter only numbers from the menu.");
                continue;
            }
            
            System.out.println();

            switch (choice) {
                case 1:
                    addBookProcess();
                    break;
                case 2:
                    addMagazineProcess();
                    break;
                case 3:
                    addDVDProcess();
                    break;
                case 4:
                    library.listAllMaterials();
                    break;
                case 5:
                    listByTypeProcess();
                    break;
                case 6:
                    borrowMaterialProcess();
                    break;
                case 7:
                    returnMaterialProcess();
                    break;
                case 8:
                    reserveMaterialProcess();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting the Library Management System. Goodbye!");
                    break;
                default:
                    if(choice != -1) {
                        System.out.println("Error: Invalid choice. Please enter a number between 0-8.");
                    }
                    break;
            }
            
            if(running) {
                 System.out.println("\n(Press Enter to return to the main menu...)");
                 scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("========== LIBRARY MANAGEMENT SYSTEM ==========");
        System.out.println("1. Add New Book");
        System.out.println("2. Add New Magazine");
        System.out.println("3. Add New DVD");
        System.out.println("4. List All Materials");
        System.out.println("5. List Materials by Type");
        System.out.println("6. Borrow Material");
        System.out.println("7. Return Material");
        System.out.println("8. Reserve Material");
        System.out.println("0. Exit");
        System.out.println("=========================================================");
        System.out.print("Please enter your choice (0-8): ");
    }

    private static void addBookProcess() {
        System.out.println("--- Add New Book ---");
        try {
            System.out.print("Enter ISBN (ID): ");
            String id = scanner.nextLine();
            System.out.print("Enter Title: ");
            String title = scanner.nextLine();
            System.out.print("Enter Author: ");
            String author = scanner.nextLine();
            System.out.print("Enter Publication Year: ");
            int year;
            while (true) {
                try {
                    year = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a valid year: ");
                }
            }
            
            Book newBook = new Book(id, title, year, author);
            library.addMaterial(newBook);

        } catch (InputMismatchException e) {
            System.err.println("Error: Publication year must be a number. Process cancelled.");
        }
    }

    private static void addMagazineProcess() {
         System.out.println("--- Add New Magazine ---");
        try {
            System.out.print("Enter Material ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Title: ");
            String title = scanner.nextLine();
            System.out.print("Enter Issue Number: ");
            int issue;
            while (true) {
                try {
                    issue = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a valid issue number: ");
                }
            }
            System.out.print("Enter Publication Year: ");
            int year;
            while (true) {
                try {
                    year = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a valid year: ");
                }
            }

            Magazine newMagazine = new Magazine(id, title, year, issue);
            library.addMaterial(newMagazine);

        } catch (InputMismatchException e) {
            System.err.println("Error: Year and Issue must be numbers. Process cancelled.");
        }
    }

    private static void addDVDProcess() {
        System.out.println("--- Add New DVD ---");
        try {
            System.out.print("Enter Material ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Title: ");
            String title = scanner.nextLine();
            System.out.print("Enter Director: ");
            String director = scanner.nextLine();
            System.out.print("Enter Publication Year: ");
            int year;
            while (true) {
                try {
                    year = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a valid year: ");
                }
            }

            DVD newDVD = new DVD(id, title, year, director);
            library.addMaterial(newDVD);

        } catch (InputMismatchException e) {
            System.err.println("Error: Publication year must be a number. Process cancelled.");
        }
    }

    private static void listByTypeProcess() {
        System.out.println("--- List Materials by Type ---");
        System.out.println("1. List Books");
        System.out.println("2. List Magazines");
        System.out.println("3. List DVDs");
        System.out.print("Enter type (1-3): ");
        
        try {
            int typeChoice = Integer.parseInt(scanner.nextLine());

            switch (typeChoice) {
                case 1:
                    library.listByType("Book");
                    break;
                case 2:
                    library.listByType("Magazine");
                    break;
                case 3:
                    library.listByType("DVD");
                    break;
                default:
                    System.out.println("Error: Invalid type choice.");
            }
        } catch (InputMismatchException e) {
            System.err.println("Error: Choice must be a number.");
        }
    }

    private static void borrowMaterialProcess() {
        System.out.println("--- Borrow Material ---");
        System.out.print("Enter Material ID to borrow: ");
        String id = scanner.nextLine();
        library.borrowMaterial(id);
    }

    private static void returnMaterialProcess() {
        System.out.println("--- Return Material ---");
        System.out.print("Enter Material ID to return: ");
        String id = scanner.nextLine();
        library.returnMaterial(id);
    }
    
    private static void reserveMaterialProcess() {
        System.out.println("--- Reserve Material ---");
        System.out.print("Enter Material ID to reserve: ");
        String id = scanner.nextLine();
        System.out.print("Enter your name for the reservation: ");
        String name = scanner.nextLine();
        library.reserveMaterial(id, name);
    }
}