package com.stocks.processor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stocks.processor.constant.Constants;
import com.stocks.processor.model.*;
import com.stocks.processor.producer.TopGainerLooserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TopGainerLooserServiceImpl implements TopGainerLooserService{

    List<ValuesModel> stocksData = new ArrayList<>();

    MetaModel metaModel = null;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private TopGainerLooserProducer producer;

    /**
     * collect data from consumer and store it in stocksList
     * @param rootModel
     */
    public void collectStocksDetails(RootModel rootModel){

        if(Objects.isNull(metaModel)){
            metaModel = rootModel.getMeta();
        }
        stocksData.add(rootModel.getValues());
    }

    /**
     * read data from list and calculate top gainer and looser
     * @return
     */
    public TopGainerLooserData getTopGainerLooserStocks(){

        if(stocksData.size() == 0){
            return new TopGainerLooserData();
        }

        ValuesModel maxValue = stocksData.stream().
                max(Comparator.comparing(ValuesModel::getClose))
                .get();

        ValuesModel minValue = stocksData.stream().
                min(Comparator.comparing(ValuesModel::getClose))
                .get();

        List<StockValueData> stockValueDataList = new ArrayList<>();

        StockValueData gainerValueData = new StockValueData(maxValue);
        gainerValueData.setProfitStatus(Constants.GAINER);
        stockValueDataList.add(gainerValueData);

        StockValueData looserValueData = new StockValueData(minValue);
        looserValueData.setProfitStatus(Constants.LOOSER);
        stockValueDataList.add(looserValueData);

        TopGainerLooserData topGainerLooserData = new TopGainerLooserData();
        topGainerLooserData.setMetaModel(metaModel);
        topGainerLooserData.setTopGainerLooserDataList(stockValueDataList);

        producer.produceTopGainerLooser(topGainerLooserData);
        clearStocksData();

        return topGainerLooserData;
    }

    /**
     * clear data from stocks values list
     */
    private void clearStocksData(){
        stocksData.clear();
        metaModel = null;
    }

}
