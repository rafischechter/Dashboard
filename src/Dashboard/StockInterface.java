package Dashboard;

import java.util.List;

/**
 * Created by rafi on 4/25/2016.
 */
public interface StockInterface {

    public String getSymbl();

    public String getLastTrade();

    public String getMarketCap();

    public String getPercentChange();

    public String getDaysHigh();

    public String getDaysLow();

    public String getBid();

    public String getAsk();

    public String getPrevClose();

    public String getOpen();
}
