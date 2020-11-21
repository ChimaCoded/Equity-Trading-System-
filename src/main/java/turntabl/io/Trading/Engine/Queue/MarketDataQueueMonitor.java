package turntabl.io.Trading.Engine.Queue;

import redis.clients.jedis.Jedis;
import turntabl.io.Trading.Engine.MarketData.MarketData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarketDataQueueMonitor {

    public List<MarketData[]> repository = new ArrayList<>();

    public MarketDataQueueMonitor() {
    }

    public List<MarketData[]> getFromQueue(){
        new Thread(new Runnable() {
            Jedis jedis = new Jedis();
            @Override
            public void run() {
                while (true){

                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {

                    }

                    String data = jedis.rpop("Market Data");
                    if(data == null) continue;
                    MarketData[] marketData = Ultility.convertToObject(data, MarketData[].class);
                    repository.add(marketData);
                    System.out.println(Arrays.stream(marketData).filter(
                            ticker -> ticker.getTicker().equals("TSLA")));
                }
            }
    }).start();

        return repository;
}

}
