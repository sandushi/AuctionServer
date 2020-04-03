
import java.util.ArrayList;

public class BidList {
    private final ArrayList bidlist;

    public BidList(){
        bidlist=new ArrayList();
    }

    public void setHistoryList(Client client,double bidvalue){

        bidlist.add(new BidData(client,bidvalue));
    }

    public ArrayList getArraylist(){

        return bidlist;
    }

}
