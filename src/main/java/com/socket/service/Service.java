package com.socket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.socket.pojo.CandleStick;
import com.socket.pojo.Data;
import com.socket.pojo.Stock;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Service {

    //This will act as thread safe CACHE for this program to store all ISIN and price history.
    static Map<String, List<Data>> MAP = new ConcurrentHashMap<>();
    private static final int LAST_30 = 30;

    /**
     * For Quote: I will get following text as output from the web socket
     * "data": {
     *     "price": 352.4568,
     *     "isin": "KV8363L66204"
     *   },
     *   "type": "QUOTE"
     *
     * For Instruments, I will get following text as output from the web socket
     * "data": {
     *     "description": "dicta deseruisse vel suas porro ex",
     *     "isin": "KV8363L66204"
     *   },
     *   "type": "ADD"/"DELETE"
     *
     * Based on type, the MAP is populated.
     * @param textData
     * @return Stock
     */
    public static Stock getStock(String textData){
        try {
            //Converting text to object
            Stock stock = new ObjectMapper().readValue(textData, Stock.class);
            if(null!=stock){
                String isin = stock.getData().getIsin();
                stock.getData().setTimeMillis(System.currentTimeMillis());
                //if found Quote, add the data to the list which will be on the next index
                if(stock.getType().equalsIgnoreCase("QUOTE")){
                    if(MAP.containsKey(isin)){
                        List<Data> temp = MAP.get(isin);
                        temp.add(stock.getData());
                        MAP.put(isin, temp);
                    }
                } else {
                    //if found Instruments, then based on ADD or DELETE, it will take action
                    if(stock.getType().equalsIgnoreCase("ADD")){
                        if(!MAP.containsKey(isin)){
                            MAP.put(isin, new ArrayList<Data>());
                        }
                    } else if(stock.getType().equalsIgnoreCase("DELETE")){
                        if(!MAP.containsKey(isin)){
                            MAP.remove(isin);
                        }
                    }
                }
                return stock;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * From the list of data got from the history of given ISIN, it will prepare
     * the candlestick of 1 min and add it to the aggregated list.
     *
     * It will return the last 30 records from the list of Candlestick.
     * @param isin
     * @return List
     */
    public static List<CandleStick> getCandleSticks(String isin){
        //Step1: Prepare CandleStick for given isin
        //Step2: Collaborate these CandleSticks together for 30 min or 30 objects

        List<CandleStick> candleStickList = new ArrayList<>();
        List<Data> history = MAP.get(isin);
        long time = 59999l;

        //In case where the history of the instrument is less than 1 min, then it had to iterate over the entire history
        boolean found = false;
        Data first = history.get(0);
        Data tempForOpenPrice = new Data();
        double high=first.getPrice(), low=first.getPrice();
        for(int i=1; i<history.size(); i++){
            Data current = history.get(i);
            Data previous = history.get(i-1);
            if(time == 59999)
                tempForOpenPrice = current;

            time = time - (current.getTimeMillis() - previous.getTimeMillis());
            high = high<current.getPrice() ? current.getPrice() : high;
            low = low>current.getPrice() ? current.getPrice() : low;
            if(time==0){
                time = 59999;
                found = true;
                CandleStick cs = new CandleStick();
                cs.setOpenPrice(tempForOpenPrice.getPrice());
                cs.setClosePrice(current.getPrice());
                cs.setHighPrice(high);
                cs.setLowPrice(low);
                cs.setOpenTime(Instant.ofEpochMilli(tempForOpenPrice.getTimeMillis())
                        .atZone(ZoneId.systemDefault()).toLocalDateTime());
                cs.setCloseTime(Instant.ofEpochMilli(current.getTimeMillis())
                        .atZone(ZoneId.systemDefault()).toLocalDateTime());

                candleStickList.add(cs);
            }
        }

        if(!found){
            CandleStick cs = new CandleStick();
            cs.setOpenPrice(first.getPrice());
            cs.setOpenTime(Instant.ofEpochMilli(first.getTimeMillis())
                    .atZone(ZoneId.systemDefault()).toLocalDateTime());
            cs.setClosePrice(history.get(history.size()-1).getPrice());
            cs.setCloseTime(Instant.ofEpochMilli(history.get(history.size()-1).getTimeMillis())
                    .atZone(ZoneId.systemDefault()).toLocalDateTime());
            cs.setHighPrice(high);
            cs.setLowPrice(low);
            candleStickList.add(cs);
        }

        if(candleStickList.size()>LAST_30){
            return candleStickList.subList(candleStickList.size()-30, candleStickList.size());
        } else {
            return candleStickList;
        }
    }
}
