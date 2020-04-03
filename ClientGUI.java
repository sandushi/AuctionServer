import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public final class ClientGUI extends JFrame {
    private final StoredHistory storedHistory;
    private DefaultTableModel defaultTableModel;
    private JTable table;

    private final String symbol;
    private final String[] columns = {"Client", "Bid Value", "Date"};


    public ClientGUI(StoredHistory storedHistory, String symbol){
        this.storedHistory=storedHistory;
        this.symbol=symbol;
        setPreferredSize(new Dimension(600, 400));
        setSize(new Dimension(600, 400));
        setResizable(false);
        init();

    }

    public void init(){

        defaultTableModel=new DefaultTableModel(columns,0);
        table=new JTable(defaultTableModel);
        table.setBounds(0, 0, 800, 400);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        setTitle("Bid-Client View "+symbol.toUpperCase() );
        addItems();
        revalidate();
        setVisible(true);
        Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                addItems();
            }
        }, 0, 500);

    }

    public void addItems(){

        defaultTableModel.getDataVector().removeAllElements();
        ArrayList dataList=storedHistory.getSymbolInfo(symbol);
       if(dataList!=null) {
           for (int i = 0; i < dataList.size(); i++) {
               BidData bidData = (BidData) dataList.get(i);
               String[] values = {bidData.getClientName(), String.valueOf(bidData.getBidValue()), bidData.getDate().toString()};
               defaultTableModel.addRow(values);
           }
       }
    }


}
