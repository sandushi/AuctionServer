

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.TimerTask;

class ConnectionServer implements Runnable {
    // some constants
    public static final int WAIT_AUTH = 0;
    public static final int AUTH_DONE = 2;
	  public static final int AUTH_SYMBOL=1;

    public static final String WAIT_AUTH_MSG = "Enter your name:";
    public static final String AUTH_DONE_MSG = "Enter the bid:";
    public static final String AUTH_SYMBOL_MSG="Enter the symbol:";


    // per connection variables
    private Socket mySocket; // connection socket per thread
    private int currentState;
    private String clientName;
    private MainServer mainServer;
    private Double bidCost;
    private String symbol="No";
    private static Stocks stocks;
    private Client client;
    private StockGUI stockGUI;
	  private StoredHistory storedHistory;
	  private boolean loopval=false;



  public ConnectionServer(MainServer mainServer) {
	   this.mySocket = null; // we will set this later
	   this.clientName = null;
	    this.mainServer = mainServer;
	// who created me. He should give some interface
    }

  public boolean handleConnection(Socket socket,Stocks stocks,StoredHistory storedHistory) {
	this.mySocket = socket;
  this.stocks=stocks;
  client =new Client("NoClient");
  this.storedHistory=storedHistory;
  this.currentState=WAIT_AUTH;

	Thread newThread = new Thread(this);
	newThread.start();
	stockGUI=new StockGUI(stocks,storedHistory);

	return true;
    }


    public void run() { // can not use "throws .." interface is different
	BufferedReader in=null;
	PrintWriter out=null;
	try {
	    in = new
		BufferedReader(new InputStreamReader(mySocket.getInputStream()));
	    out = new
		PrintWriter(new OutputStreamWriter(mySocket.getOutputStream()));

	    String line=null;

    while(loopval!=true){

		switch(currentState) {
		case WAIT_AUTH:

		    if (client.getClientname().equals("NoClient")) {
          out.write(WAIT_AUTH_MSG);
          out.flush();
				  line=in.readLine();
				if(line.equals("quit")){
          loopval=true;
          break;}
				clientName = line;
				System.out.println(clientName+" Registered");
			  client = new Client(clientName);
		    currentState = AUTH_SYMBOL;
         }
		    break;


	   case AUTH_SYMBOL:
         if (symbol.equals("No")) {
			        out.write(AUTH_SYMBOL_MSG);
              out.flush();
              line=in.readLine();
              if(line.equals("quit")){
                loopval=true;
                break;}
              symbol = line.toUpperCase();

          }else if(!symbol.isEmpty()){
		           if(mainServer.isAuthorized(symbol)){
                  out.write("current price of "+ symbol +":"+stocks.findPrice(symbol)+"\n");
				          out.flush();
			            out.write(AUTH_DONE_MSG);
				          out.flush();
				          line=in.readLine();
                  if(line.equals("quit")){
                      loopval=true;
                      break;}
                bidCost = Double.parseDouble(line);
			        	if(Double.compare( bidCost,Double.valueOf(stocks.findPrice(symbol)) )> 0){
                    stocks.setPrice(symbol,bidCost);}
                    storedHistory.setbidHistory(symbol,client,bidCost);
                    stockGUI.additems();

				            new ClientGUI(storedHistory,symbol);


			  }else{
				  out.print("-1\n");
                  out.flush();
                  this.mySocket.close();
                  break;
			  }


		}
		break;


      default:
		      System.out.println("Undefined state");
		  return;
		}





  } // while

	    // close everything
	    out.close();
	    in.close();
	    this.mySocket.close();
	} // try
	catch (IOException e) {
	    System.out.println(e);
	}
    }



    }
