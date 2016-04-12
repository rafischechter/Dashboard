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
    private String marketCap;
    private String percentChange;

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

    private Stock(String name, String symbol, BigDecimal lastTrade, String marketCap, String percentChange){
        this.name = name;
        this.symbl = symbol;
        this.lastTrade = lastTrade;
        this.marketCap = marketCap;
        this.percentChange = percentChange;
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

    public String getMarketCap(){
        return marketCap;
    }

    public String getPercentChange(){
        return percentChange;
    }


    // Static method to create stock objects
    public static List<Stock> createStockObject(){
        List<Stock> list = new ArrayList<Stock>();

        String name;
        String symbl;
        BigDecimal lastTrade;
        BigDecimal ask;
        BigDecimal bid;
        BigDecimal daysHigh;
        BigDecimal daysLow;
        String marketCap;
        String percentChange;

        StringBuilder listOfStocks = new StringBuilder();
        listOfStocks.append("\"");
        listOfStocks.append("msft");
        listOfStocks.append("\"");
        listOfStocks.append(", ");
        listOfStocks.append("\"");
        listOfStocks.append("aapl");
        listOfStocks.append("\"");listOfStocks.append(", ");
        listOfStocks.append("\"");
        listOfStocks.append("yhoo");
        listOfStocks.append("\"");


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
                lastTrade = new BigDecimal((String)quote.get("LastTradePriceOnly"));
                marketCap = (String)quote.get("MarketCapitalization");
                percentChange = (String)quote.get("PercentChange");

                //ask = Double.parseDouble((String)quote.get("Ask"));
                //bid = Double.parseDouble((String)quote.get("Bid"));
                //daysHigh = Double.parseDouble((String)quote.get("DaysHigh"));
                //daysLow = Double.parseDouble((String)quote.get("DaysLow"));
                list.add(new Stock(name, symbl, lastTrade, marketCap, percentChange));

                //System.out.printf("Name: %s\nSymbol: %s\nBid: %.2f\nAsk: %.2f\nDays High: %.2f\n" +
                 //       "Days Low: %.2f\n", name, symbl, ask, bid, daysHigh, daysLow);
            }



        }catch (Exception e){
            e.printStackTrace();
        }


        return list;
    }

    // Static method to create stock objects
    public static List<Stock> createStockObject(List<String> stockSymbols){
        List<Stock> list = new ArrayList<Stock>();

        String name;
        String symbl;
        BigDecimal lastTrade;
        BigDecimal ask;
        BigDecimal bid;
        BigDecimal daysHigh;
        BigDecimal daysLow;
        String marketCap;
        String percentChange;


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
                lastTrade = new BigDecimal((String)quote.get("LastTradePriceOnly"));
                marketCap = (String)quote.get("MarketCapitalization");
                percentChange = (String)quote.get("PercentChange");

                lastTrade = lastTrade.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                //ask = Double.parseDouble((String)quote.get("Ask"));
                //bid = Double.parseDouble((String)quote.get("Bid"));
                //daysHigh = Double.parseDouble((String)quote.get("DaysHigh"));
                //daysLow = Double.parseDouble((String)quote.get("DaysLow"));
                list.add(new Stock(name, symbl, lastTrade, marketCap, percentChange));

                //System.out.printf("Name: %s\nSymbol: %s\nBid: %.2f\nAsk: %.2f\nDays High: %.2f\n" +
                //       "Days Low: %.2f\n", name, symbl, ask, bid, daysHigh, daysLow);
            }



        }catch (Exception e){
            e.printStackTrace();
        }


        return list;
    }
}
