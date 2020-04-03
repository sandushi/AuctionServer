import java.awt.*;
import javax.swing.Timer; //for timer

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.IOException;
import java.util.*;

public class Display
    extends JPanel implements ActionListener {
    JTextArea textArea;
    VisualServer server;
    private static StoredHistory storedHistory;

    public Display(VisualServer server) {
        super(new GridBagLayout());

	textArea = new JTextArea(5, 20);
	textArea.setEditable(false);
	JScrollPane scrollPane = new JScrollPane(textArea);

	 //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.HORIZONTAL;

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);

	Timer timer = new Timer(500, this);
	timer.start();

	this.server = server;
    }

    public void actionPerformed(ActionEvent e) {
	String newline = server.getMSG();
	if(newline != null) {
	    textArea.append(newline + "\n");

	    //Make sure the new text is visible, even if there
	    //was a selection in the text area.
	    textArea.setCaretPosition(textArea.getDocument().getLength());
	}

    }

    public static void main(String [] args) throws IOException {
	//Create and set up the window.

       storedHistory=new StoredHistory();
	     Stocks stocks = new Stocks("stocks.csv","Symbol","Security Name","Price");
	     VisualServer server = new VisualServer(MainServer.BASE_PORT,
					      stocks,storedHistory);


    	server.server_loop();



    }
}
