package Dashboard;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.stream.Collectors;

/**
 * Created by rafi on 3/29/2016.
 */
public class Stock {

    private Date date;
    private String name;
    private String symbl;
    private double currentPrice;
    private double prevClose;
    private double open;
    private double daysHigh;
    private double daysLow;
    private double close;
    private double bid;
    private double ask;
    private int volume;
    private double adjClose;

    private Stock(){}

    private Stock(String name, String symbol, double ask, double bid, double daysHigh, double daysLow){
        this.name = name;
        this.symbl = symbol;
        this.ask = ask;
        this.bid = bid;
        this.daysHigh = daysHigh;
        this.daysLow = daysLow;
    }


    //Setter Methods

    //Getter Methods


    public String getName() {
        return name;
    }

    public String getSymbl() {
        return symbl;
    }

    public static List<Stock> createStockObject(){
        List<Stock> list = new ArrayList<Stock>();

        String name;
        String symbl;
        double ask;
        double bid;
        double daysHigh;
        double daysLow;

        String yql = "SELECT * FROM yahoo.finance.quotes WHERE symbol in (\"msft\", \"aapl\", \"yhoo\")";

        try {

            String http = "http://query.yahooapis.com/v1/public/yql?q=" + URLEncoder.encode(yql, "UTF-8") + "&format=json&diagnostics=true&env=http%3A%2F%2Fdatatables.org%2Falltables.env";

            URL url = new URL(http);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String result = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));

            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(result);
            JSONObject query = (JSONObject) jsonObject.get("query");
            JSONObject results = (JSONObject) query.get("results");

            JSONArray items = (JSONArray) results.get("quote");

            Iterator iterator = items.iterator();
            while (iterator.hasNext()){
                JSONObject quote = (JSONObject) iterator.next();
                name = (String)quote.get("Name");
                symbl = (String)quote.get("symbol");
                ask = Double.parseDouble((String)quote.get("Ask"));
                bid = Double.parseDouble((String)quote.get("Bid"));
                daysHigh = Double.parseDouble((String)quote.get("DaysHigh"));
                daysLow = Double.parseDouble((String)quote.get("DaysLow"));
                list.add(new Stock(name, symbl, ask, bid, daysHigh, daysLow));
                //System.out.printf("Name: %s\nSymbol: %s\nBid: %.2f\nAsk: %.2f\nDays High: %.2f\n" +
                 //       "Days Low: %.2f\n", name, symbl, ask, bid, daysHigh, daysLow);
            }



        }catch (Exception e){
            e.printStackTrace();
        }


        return list;
    }
}
