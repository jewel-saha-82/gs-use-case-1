package com.stocks.processor.service;

import com.stocks.processor.model.RootModel;
import com.stocks.processor.model.TopGainerLooserData;

public interface TopGainerLooserService {

    void collectStocksDetails(RootModel rootModel);

    TopGainerLooserData getTopGainerLooserStocks();

}
