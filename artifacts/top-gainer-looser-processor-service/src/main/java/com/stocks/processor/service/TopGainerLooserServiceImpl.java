package com.stocks.processor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stocks.processor.constant.Constants;
import com.stocks.processor.data.InMemoryData;
import com.stocks.processor.model.*;
import com.stocks.processor.producer.TopGainerLooserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TopGainerLooserServiceImpl implements TopGainerLooserService{


    @Autowired
    InMemoryData inMemoryData;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private TopGainerLooserProducer producer;

    /**
     * collect data from consumer and store it in stocksList
     * @param rootModel
     */
    public void collectStocksDetails(RootModel rootModel){

        if(Objects.isNull(inMemoryData.getMetaModel())){
            inMemoryData.setMetaModel(rootModel.getMeta());
        }
        inMemoryData.getStocksData().add(rootModel.getValue());
    }

    /**
     * read data from list and calculate top gainer and looser
     * @return
     */
    public TopGainerLooserData getTopGainerLooserStocks(){

        if(inMemoryData.getStocksData().size() == 0){
            return new TopGainerLooserData();
        }

        TopGainerLooserData topGainerData = new TopGainerLooserData();
            producer.produceTopGainerLooser(getTopGainer());
            producer.produceTopGainerLooser(getTopLooser());

        clearStocksData();
        return topGainerData;
    }

    /**
     * get to gainer stock details
     * @return
     */
    private TopGainerLooserData getTopGainer(){
        try {
            TopGainerLooserData topGainerData = new TopGainerLooserData();
            ValuesModel maxValue = inMemoryData.getStocksData().stream().
                    max(Comparator.comparing(ValuesModel::getClose))
                    .get();

            topGainerData.setSymbol(inMemoryData.getMetaModel().getSymbol());
            topGainerData.setStock_name(inMemoryData.getMetaModel().getSymbol());
            topGainerData.setDate(maxValue.getDatetime());
            topGainerData.setChange_price(String.valueOf(
                    Float.valueOf(maxValue.getClose()) - Float.valueOf(maxValue.getOpen())));
            topGainerData.setClosing_price(maxValue.getClose());
            topGainerData.setCurrency(inMemoryData.getMetaModel().getCurrency());
            topGainerData.setStatus(Constants.GAINER);
            return topGainerData;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * get top looser stock details
     * @return
     */
    private TopGainerLooserData getTopLooser(){
        try {
            ValuesModel minValue = inMemoryData.getStocksData().stream().
                    min(Comparator.comparing(ValuesModel::getClose))
                    .get();
            TopGainerLooserData topLooserData = new TopGainerLooserData();
            topLooserData.setSymbol(inMemoryData.getMetaModel().getSymbol());
            topLooserData.setStock_name(inMemoryData.getMetaModel().getSymbol());
            topLooserData.setDate(minValue.getDatetime());
            topLooserData.setChange_price(String.valueOf(
                    Float.valueOf(minValue.getClose()) - Float.valueOf(minValue.getOpen())));
            topLooserData.setClosing_price(minValue.getClose());
            topLooserData.setCurrency(inMemoryData.getMetaModel().getCurrency());
            topLooserData.setStatus(Constants.LOOSER);
            return topLooserData;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * clear data from stocks values list
     */
    private void clearStocksData(){
        inMemoryData.getStocksData().clear();
        inMemoryData.setMetaModel(null);
    }

}
