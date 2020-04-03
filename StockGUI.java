import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.TimerTask;


public final class StockGUI extends JFrame {
    private final Stocks stocks;
    private final int updateInterval = 500; // Milliseconds
    private final String[] columns = {"Symbol", "Security Name", "Price"};
    private final String[] specialSymbols = {"FB", "VRTU", "MSFT", "GOOGL", "YHOO", "XLNX", "TSLA", "TXN"};
    private JTable table;
    private DefaultTableModel defaultTableModel;
	private final StoredHistory storedHistory;



    public StockGUI(Stocks stocks,StoredHistory storedHistory){
        setTitle("Stock-Server");
       this.storedHistory=storedHistory;
        this.stocks=stocks;
        setPreferredSize(new Dimension(800, 400));
        setSize(new Dimension(800, 400));

        init();

        }

  public void init() {
      defaultTableModel = new DefaultTableModel(columns, 0);
      table = new JTable(defaultTableModel);
      table.setBounds(0, 0, 800, 400);
      table.setEnabled(false);
      JScrollPane jScrollPane = new JScrollPane(table);
      ListSelectionModel listSelectionModel = table.getSelectionModel();
      listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      add(jScrollPane, BorderLayout.CENTER);

      setLocationRelativeTo(null);
      setVisible(true);
      revalidate();
      // end of info panel

      Timer timer = new Timer();
      timer.scheduleAtFixedRate(new TimerTask() {
          @Override
          public void run() {
             additems();
          }
      }, 0, 500);

}


      public void additems(){
      defaultTableModel.getDataVector().removeAllElements();
        for (String specialSymbols1 : specialSymbols) {
            String[] values = {specialSymbols1, stocks.findSecurityName(specialSymbols1), String.valueOf(stocks.findPrice(specialSymbols1))};
            defaultTableModel.addRow(values);
        }

      }
  }
