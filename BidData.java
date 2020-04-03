
import java.util.Date;

public class BidData {
    private final Client client;
    private final double bid;
    private final Date date;

    public BidData(Client client,double bid){
        this.client=client;
        this.bid=bid;
        this.date=new Date();
    }

    public String getClientName() {
        return client.getClientname();
    }

    public double getBidValue() {
        return bid;
    }

    public Date getDate() {
        return date;
    }
}
