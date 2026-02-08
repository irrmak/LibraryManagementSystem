public abstract class Material {
    private String materialID;
    private String title;
    private int publicationYear;

    public Material(String materialID, String title, int publicationYear) {
        this.materialID = materialID;
        this.title = title;
        this.publicationYear = publicationYear;
    }

    public String getMaterialID() {
        return materialID;
    }

    public String getTitle() {
        return title;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public abstract String toFileFormat();

    public abstract String getDisplayDetails();


    @Override
    public String toString() {
        String baseInfo = "ID: " + materialID + 
                          " | Title: " + title + 
                          " | Year: " + publicationYear;
        
        String specificDetails = getDisplayDetails();
        
        return baseInfo + specificDetails;
    }
}