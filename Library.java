import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Library {

    private List<Material> materials;
    private static final String FILE_NAME = "library.txt"; 

    public Library() {
        this.materials = new ArrayList<>();
        loadMaterialsFromFile();
    }

    public void addMaterial(Material material) {
        if(findMaterial(material.getMaterialID()) != null) {
            System.out.println("Material with ID: " + material.getMaterialID() + " is already added.");
            return;
        }
        this.materials.add(material);
        saveToFile();
        System.out.println("Success: '" + material.getTitle() + "' has been added to the library.");
    }

    public void listAllMaterials() {
        if (materials.isEmpty()) {
            System.out.println("The library is currently empty.");
            return;
        }

        System.out.println("--- All Materials in Library ---");
        int count = 1;
        for (Material mat : materials) {
            System.out.println(count + ". " + mat.toString());
            count++;
        }
        System.out.println("----------------------------------");
    }

    public void listByType(String type) {
        System.out.println("--- Listing All " + type + "s ---");
        int count = 0;
        
        for (Material mat : materials) {
            if (type.equals("Book") && mat instanceof Book) {
                System.out.println("• " + mat.toString());
                count++;
            } else if (type.equals("Magazine") && mat instanceof Magazine) {
                System.out.println("• " + mat.toString());
                count++;
            } else if (type.equals("DVD") && mat instanceof DVD) {
                System.out.println("• " + mat.toString());
                count++;
            }
        }
        
        if (count == 0) {
            System.out.println("No materials of type '" + type + "' found.");
        }
        System.out.println("---------------------------------");
    }

    public Material findMaterial(String materialID) {
        for (Material mat : materials) {
            if (mat.getMaterialID().equals(materialID)) {
                return mat;
            }
        }
        return null;
    }

    public void borrowMaterial(String materialID) {
        Material mat = findMaterial(materialID);

        if (mat == null) {
            System.out.println("Error: No material found with ID: " + materialID);
            return;
        }

        if (mat instanceof Borrowable) {
            Borrowable item = (Borrowable) mat;
            item.borrow(); 
            saveToFile();
        } else {
            System.out.println("Error: This item (" + mat.getTitle() + ") cannot be borrowed.");
        }
    }

    public void returnMaterial(String materialID) {
        Material mat = findMaterial(materialID);

        if (mat == null) {
            System.out.println("Error: No material found with ID: " + materialID);
            return;
        }

        if (mat instanceof Borrowable) {
            Borrowable item = (Borrowable) mat;
            item.returnItem();
            saveToFile();
        } else {
            System.out.println("Error: This item (" + mat.getTitle() + ") cannot be returned as it cannot be borrowed.");
        }
    }

    public void reserveMaterial(String materialID, String holderName) {
        Material mat = findMaterial(materialID);

        if (mat == null) {
            System.out.println("Error: No material found with ID: " + materialID);
            return;
        }

        if (mat instanceof Reservable) {
            Reservable item = (Reservable) mat;
            item.reserve(holderName); 
            saveToFile();
        } else {
            System.out.println("Error: This item (" + mat.getTitle() + ") cannot be reserved.");
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Material mat : materials) {
                writer.write(mat.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error: Could not save data to file: " + e.getMessage());
        }
    }

    private void loadMaterialsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Info: 'library.txt' not found. A new file will be created.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 1) continue;

                String type = parts[0];

                try {
                    switch (type) {
                        case "BOOK":
                            if (parts.length == 8) {
                                String id = parts[1];
                                String title = parts[2];
                                int year = Integer.parseInt(parts[3]);
                                String author = parts[4];
                                boolean isBorrowed = Boolean.parseBoolean(parts[5]);
                                boolean isReserved = Boolean.parseBoolean(parts[6]);
                                String holder = parts[7].equals("null") ? null : parts[7];

                                Book book = new Book(id, title, year, author);
                                book.setBorrowed(isBorrowed);
                                book.setReserved(isReserved, holder);
                                this.materials.add(book);
                            }
                            break;
                            
                        case "MAGAZINE":
                             if (parts.length == 6) {
                                String id = parts[1];
                                String title = parts[2];
                                int year = Integer.parseInt(parts[3]);
                                int issue = Integer.parseInt(parts[4]);
                                boolean isBorrowed = Boolean.parseBoolean(parts[5]);

                                Magazine mag = new Magazine(id, title, year, issue);
                                mag.setBorrowed(isBorrowed);
                                this.materials.add(mag);
                            }
                            break;
                            
                        case "DVD":
                            if (parts.length == 5) {
                                String id = parts[1];
                                String title = parts[2];
                                int year = Integer.parseInt(parts[3]);
                                String director = parts[4];
                                
                                DVD dvd = new DVD(id, title, year, director);
                                this.materials.add(dvd);
                            }
                            break;
                            
                        default:
                            System.err.println("Warning: Unknown data format in file, skipping line: " + line);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Warning: Corrupted number format in file, skipping line: " + line);
                } catch (Exception e) {
                     System.err.println("Warning: Corrupted data in file, skipping line: " + line);
                }
            }
            System.out.println("Info: Successfully loaded " + materials.size() + " materials from file.");

        } catch (IOException e) {
            System.err.println("Error: Could not load data from file: " + e.getMessage());
        }
    }
}