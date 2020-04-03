import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class MainServer {

    /* Some constants */
    public static final int BASE_PORT = 2000;  // do not change

    /* local data for the server
     * Every main server is defined in terms of the port it
     * listens and the database of allowed users
     */
    private ServerSocket serverSocket=null;  // server Socket for main server
    private Stocks stocks=null;  	// who are allowed to chat
    private StoredHistory storedHistory;

    public MainServer(int socket, Stocks stocks,StoredHistory storedHistory) {
	       this.stocks = stocks;
		   this.storedHistory = storedHistory;

	try {
	    this.serverSocket = new ServerSocket(socket);
	} catch (IOException e) {
	    System.out.println(e);
	}
    }

    /* each server will provide the following functions to
     * the public. Note that these are non-static
     */
    public boolean isAuthorized(String symbol) {
	       return this.stocks.findSecurityName(symbol) != null;
    }

    public String getPrice(String symbol) {
	// should these be synchronized?
	     return this.stocks.findPrice(symbol);
    }

    /* server will define how the messages should be posted
     * this will be used by the connection servers
     */

    public void postMSG(String msg) {
	// all threads print to same screen
	System.out.println(msg);
    }

    public String authorizedOnce(String a) {
	// need to implement this.
	return null;
    }

    public void server_loop() {
	try {
	    while(true) {
		Socket socket = this.serverSocket.accept();
		this.storedHistory=storedHistory;
		this.stocks=stocks;
		ConnectionServer worker = new ConnectionServer(this);
		worker.handleConnection(socket,stocks,storedHistory);
	    }
	} catch(IOException e) {
	    System.out.println(e);
	}
    }// end server_loop
}
