package Dashboard;

import java.util.Date;

/**
 * Created by rafi on 3/29/2016.
 */
public class Stock {

    private Date date;
    private String StockName;
    private String symbl;
    private double currentPrice;
    private double prevClose;
    private double open;
    private double high;
    private double low;
    private double close;
    private double bid;
    private double ask;
    private int volume;
    private double adjClose;



    //Setter Methods
    public void setStockName(String stockName) {
        StockName = stockName;
    }

    public void setSymbl(String symbl) {
        this.symbl = symbl;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setPrevClose(double prevClose) {
        this.prevClose = prevClose;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setAdjClose(double adjClose) {
        this.adjClose = adjClose;
    }

    //Getter Methods
    public String getStockName() {
        return StockName;
    }

    public String getSymbl() {
        return symbl;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getPrevClose() {
        return prevClose;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getBid() {
        return bid;
    }

    public double getAsk() {
        return ask;
    }

    public int getVolume() {
        return volume;
    }

    public double getAdjClose() {
        return adjClose;
    }
}
