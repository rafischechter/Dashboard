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
public class Stock implements StockInterface{

    private Date date;
    private String name;
    private String symbl;
    private String lastTrade;
    private String currentPrice;
    private String prevClose;
    private String open;
    private String daysHigh;
    private String daysLow;
    private String close;
    private String bid;
    private String ask;
    private String volume;
    private String avgVolume;
    private String adjClose;
    private String marketCap;
    private String percentChange;

    private Stock(){}

    private Stock(String name, String symbol, String lastTrade, String ask, String bid, String daysHigh, String daysLow){
        this.name = name;
        this.symbl = symbol;
        this.lastTrade = lastTrade;
        this.ask = ask;
        this.bid = bid;
        this.daysHigh = daysHigh;
        this.daysLow = daysLow;
    }

    private Stock(String name, String symbol, String lastTrade, String marketCap, String percentChange,
                  String daysHigh, String daysLow, String bid, String ask, String prevClose, String open,
                  String volume, String avgVolume){
        this.name = name;
        this.symbl = symbol;
        this.lastTrade = lastTrade;
        this.marketCap = marketCap;
        this.percentChange = percentChange;
        this.daysHigh = daysHigh;
        this.daysLow = daysLow;
        this.bid = bid;
        this.ask = ask;
        this.prevClose = prevClose;
        this.open = open;
        this.volume = volume;
        this.avgVolume = avgVolume;
    }


    //Setter Methods


    //Getter Methods

    public String getName() {
        return name;
    }

    public String getSymbl() {
        return symbl;
    }

    public String getLastTrade(){
        return lastTrade;
    }

    public String getMarketCap(){
        return marketCap;
    }

    public String getPercentChange(){
        return percentChange;
    }

    public String getDaysHigh() {
        return daysHigh;
    }

    public String getDaysLow() {
        return daysLow;
    }

    public String getBid() {
        return bid;
    }

    public String getAsk() {
        return ask;
    }

    public String getPrevClose() {
        return prevClose;
    }

    public String getOpen() {
        return open;
    }

    public String getVolume() {
        return volume;
    }

    public String getAvgVolume() {
        return avgVolume;
    }

    // Static method to create stock objects
    public static List<Stock> createStockObject(List<String> stockSymbols){
        List<Stock> list = new ArrayList<Stock>();

        String name;
        String symbl;
        String lastTrade;
        String ask;
        String bid;
        String daysHigh;
        String daysLow;
        String marketCap;
        String percentChange;
        String prevClose;
        String open;
        String volume;
        String avgVolume;


        StringBuilder listOfStocks = new StringBuilder();

        listOfStocks.append("\"").append("msft").append("\"").append(",");
        listOfStocks.append("\"").append("aapl").append("\"").append(",");

        stockSymbols.stream().forEach(e -> {
            listOfStocks.append("\"").append(e).append("\"").append(",");
        });
        listOfStocks.deleteCharAt(listOfStocks.length() - 1);


        //String yql = "SELECT * FROM yahoo.finance.quotes WHERE symbol in (\"msft\", \"aapl\", \"yhoo\")";
        String yql = "SELECT * FROM yahoo.finance.quotes WHERE symbol in (" + listOfStocks + ")";

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
                lastTrade = (String)quote.get("LastTradePriceOnly");
                marketCap = (String)quote.get("MarketCapitalization");
                percentChange = (String)quote.get("PercentChange");
                ask = (String)quote.get("Ask");
                bid = (String)quote.get("Bid");
                daysHigh = (String)quote.get("DaysHigh");
                daysLow = (String)quote.get("DaysLow");
                prevClose = (String)quote.get("PreviousClose");
                open = (String)quote.get("Open");
                volume = (String)quote.get("Volume");
                avgVolume = (String)quote.get("AverageDailyVolume");

                list.add(new Stock(name, symbl, lastTrade, marketCap, percentChange, daysHigh, daysLow, bid, ask, prevClose, open, volume, avgVolume));

                //System.out.printf("Name: %s\nSymbol: %s\nBid: %.2f\nAsk: %.2f\nDays High: %.2f\n" +
                //       "Days Low: %.2f\n", name, symbl, ask, bid, daysHigh, daysLow);
            }



        }catch (Exception e){
            e.printStackTrace();
        }


        return list;

        //TODO delete stock quote
    }
}
