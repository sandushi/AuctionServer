import java.io.*;
import java.util.*;

class Stocks {

    private Map<String, String> stocks1;
    private Map<String, String> stocks2;
    private String [] fields;

    public Stocks(String cvsFile, String key, String val1,String val2)  {
	FileReader fileRd=null;
	BufferedReader reader=null;

	try {
	    fileRd = new FileReader(cvsFile);
	    reader = new BufferedReader(fileRd);

	    /* read the CSV file's first line which has
	     * the names of fields.
	     */
	    String header = reader.readLine();
	    fields = header.split(",");// keep field names

	    // find where the key and the value are
	    int keyIndex = findIndexOf(key);
	    int val_1_Index = findIndexOf(val1);
	    int val_2_Index = findIndexOf(val2);

	    if(keyIndex == -1 || val_1_Index == -1 || val_2_Index== -1 )
		throw new IOException("CVS file does not have data");
	    // note how you can throw a new exception

	    // get a new hash map
	    stocks1 = new HashMap<String, String>();
	    stocks2 = new HashMap<String, String>();

	    /* read each line, getting it split by ,
	     * use the indexes to get the key and value
	     */
	    String [] tokens;
	    for(String line = reader.readLine();
		line != null;
		line = reader.readLine()) {
		tokens = line.split(",");
		stocks1.put(tokens[keyIndex], tokens[val_1_Index]);
        stocks2.put(tokens[keyIndex],tokens[val_2_Index]);
	    }

	    if(fileRd != null) fileRd.close();
	    if(reader != null) reader.close();

	    // I can catch more than one exceptions
	} catch (IOException e) {
	    System.out.println(e);
	    System.exit(-1);
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.out.println("Malformed CSV file");
	    System.out.println(e);
	}
    }

    private int findIndexOf(String key) {
	for(int i=0; i < fields.length; i++)
	    if(fields[i].equals(key)) return i;
	return -1;
    }

    // public interface
    public String findSecurityName(String key) {
	return stocks1.get(key);
    }

 public String findPrice(String key) {
	return stocks2.get(key);
    }

    public void setPrice(String key,Double price){
      stocks2.put(key,Double.toString(price));
    }
}
