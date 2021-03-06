package Data;

import java.util.Date;
import java.util.Random;

public class OrderData {
    private int id = new Random().nextInt();
    private int price;
    private String deadline;
    private String link;
    private StatusEnum status;

    public int getId() {
        return id;
    }

    public String getDeadline() {
        return deadline;
    }

    public StatusEnum getStatus(){
        return status;
    }

    public int getPrice() {
        return price;
    }

    public String getLink() {
        return link;
    }

    public OrderData(int price, String  deadline, String link, StatusEnum status){
        this.price = price;
        this.deadline = deadline;
        this.link = link;
        this.status = status;
    }
}
