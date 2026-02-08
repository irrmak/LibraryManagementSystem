public class DVD extends Material {
    private String director;

    public DVD(String materialID, String title, int publicationYear, String director) {
        super(materialID, title, publicationYear);
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

    public void watch() {
        System.out.println("Now watching '" + getTitle() + "' directed by " + director + ".");
    }

    @Override
    public String getDisplayDetails() {
        return " | Director: " + director + " | Status: Available (In-Library use only)";
    }

    @Override
    public String toFileFormat() {
        return "DVD," + getMaterialID() + "," + getTitle() + "," + getPublicationYear() + 
               "," + director;
    }
}