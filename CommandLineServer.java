
import java.io.IOException;

public class CommandLineServer {


  static   StoredHistory storedHistory;

    public static void main(String [] args) throws IOException{
	Stocks stocks = new Stocks("stocks.csv","Symbol","Security Name","Price");
	MainServer mainServer = new MainServer(MainServer.BASE_PORT,stocks,storedHistory);
	mainServer.server_loop();
    }
}
