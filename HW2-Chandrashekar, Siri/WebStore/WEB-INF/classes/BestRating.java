import java.io.*;

public class BestRating {
    String productName;
    String rating;


    public BestRating(String productName, String rating) {

        this.productName = productName;
        this.rating = rating;
    }

    public String getProductname() {
        return productName;
    }

    public void setProductname(String productName) {
        this.productName = productName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
