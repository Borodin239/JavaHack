package Data;

import java.util.Date;
import java.util.Random;

public class OrderData {
    private Date deadline = new Date();
    private int id = new Random().nextInt();
    private int price;
    private String link;

    public int getId() {
        return id;
    }

    public Date getDeadline() {
        return deadline;
    }

    public int getPrice() {
        return price;
    }

    public String getLink() {
        return link;
    }
}
