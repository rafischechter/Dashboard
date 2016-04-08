package Dashboard;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rafi on 3/29/2016.
 */
public class Stock {

    private Date date;
    private String name;
    private String symbl;
    private BigDecimal lastTrade;
    private BigDecimal currentPrice;
    private BigDecimal prevClose;
    private BigDecimal open;
    private BigDecimal daysHigh;
    private BigDecimal daysLow;
    private BigDecimal close;
    private BigDecimal bid;
    private BigDecimal ask;
    private int volume;
    private BigDecimal adjClose;

    private Stock(){}

    private Stock(String name, String symbol, BigDecimal lastTrade, BigDecimal ask, BigDecimal bid, BigDecimal daysHigh, BigDecimal daysLow){
        this.name = name;
        this.symbl = symbol;
        this.lastTrade = lastTrade;
        this.ask = ask;
        this.bid = bid;
        this.daysHigh = daysHigh;
        this.daysLow = daysLow;
    }

    private Stock(String name, String symbol, BigDecimal lastTrade){
        this.name = name;
        this.symbl = symbol;
        this.lastTrade = lastTrade;
    }


    //Setter Methods

    //Getter Methods


    public String getName() {
        return name;
    }

    public String getSymbl() {
        return symbl;
    }

    public BigDecimal getLastTrade(){
        return lastTrade;
    }

    public static List<Stock> createStockObject(){
        List<Stock> list = new ArrayList<Stock>();

        String name;
        String symbl;
        BigDecimal lastTrade;
        BigDecimal ask;
        BigDecimal bid;
        BigDecimal daysHigh;
        BigDecimal daysLow;

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
                lastTrade = new BigDecimal((String)quote.get("LastTradePriceOnly"));

                //ask = Double.parseDouble((String)quote.get("Ask"));
                //bid = Double.parseDouble((String)quote.get("Bid"));
                //daysHigh = Double.parseDouble((String)quote.get("DaysHigh"));
                //daysLow = Double.parseDouble((String)quote.get("DaysLow"));
                list.add(new Stock(name, symbl, lastTrade));

                //System.out.printf("Name: %s\nSymbol: %s\nBid: %.2f\nAsk: %.2f\nDays High: %.2f\n" +
                 //       "Days Low: %.2f\n", name, symbl, ask, bid, daysHigh, daysLow);
            }



        }catch (Exception e){
            e.printStackTrace();
        }


        return list;
    }
}
