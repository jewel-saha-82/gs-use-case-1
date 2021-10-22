package com.stocks.processor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stocks.processor.constant.Constants;
import com.stocks.processor.model.*;
import com.stocks.processor.producer.TopGainerLooserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        stocksData.add(rootModel.getValue());
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
        TopGainerLooserData topGainerData = new TopGainerLooserData();
        topGainerData.setSymbol(metaModel.getSymbol());
        topGainerData.setStock_name(metaModel.getSymbol());
        topGainerData.setDate(maxValue.getDatetime());
        topGainerData.setChange_price(String.valueOf(
                Float.valueOf(maxValue.getClose()) - Float.valueOf(maxValue.getOpen())));
        topGainerData.setClosing_price(maxValue.getClose());
        topGainerData.setCurrency(metaModel.getCurrency());
        topGainerData.setStatus(Constants.GAINER);
        producer.produceTopGainerLooser(topGainerData);


        ValuesModel minValue = stocksData.stream().
                min(Comparator.comparing(ValuesModel::getClose))
                .get();
        TopGainerLooserData topLooserData = new TopGainerLooserData();
        topLooserData.setSymbol(metaModel.getSymbol());
        topLooserData.setStock_name(metaModel.getSymbol());
        topLooserData.setDate(minValue.getDatetime());
        topLooserData.setChange_price(String.valueOf(
                Float.valueOf(minValue.getClose()) - Float.valueOf(minValue.getOpen())));
        topLooserData.setClosing_price(minValue.getClose());
        topLooserData.setCurrency(metaModel.getCurrency());
        topLooserData.setStatus(Constants.LOOSER);
        producer.produceTopGainerLooser(topLooserData);

        clearStocksData();
        return topGainerData;
    }

    /**
     * clear data from stocks values list
     */
    private void clearStocksData(){
        stocksData.clear();
        metaModel = null;
    }

}
