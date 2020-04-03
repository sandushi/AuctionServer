import java.util.ArrayList;
import java.util.HashMap;

//bidHistory
public class StoredHistory {


    private HashMap bidMap;
    private BidList bidList;

    public StoredHistory(){
        bidMap =new HashMap<String,BidList>();
}


    public void setbidHistory(String symbol, Client client, double bidValue){
        if(bidMap.containsKey(symbol)){
            bidList=(BidList) bidMap.get(symbol);
            bidList.setHistoryList(client,bidValue);
            bidMap.put(symbol,bidList);


        }else{
            bidList=new BidList();
            bidList.setHistoryList(client,bidValue);
            bidMap.put(symbol,bidList);
        }
    }

    public ArrayList getSymbolInfo(String symbol){
        BidList bidList;

        if(bidMap.containsKey(symbol)){
           bidList=(BidList) bidMap.get(symbol);
            return  bidList.getArraylist();
        }
        else return null;
    }
}