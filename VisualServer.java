import java.util.*;

class VisualServer extends MainServer {
  private static LinkedList<String> msgs;
	private StoredHistory storedHistory;

    public VisualServer(int socket, Stocks stocks,StoredHistory storedHistory) {
       	super(socket, stocks,storedHistory);
	      this.storedHistory=storedHistory;
	      msgs = new LinkedList<String>(); 
    }

    @Override
    public synchronized void postMSG(String str) {
	// I can override and make function synchronized
      msgs.add(str);
    }

    public String getMSG() {
	if(!msgs.isEmpty()) return msgs.remove();
	return null;
    }
}
