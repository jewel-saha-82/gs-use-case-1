package com.stocks.processor.model;

import lombok.Data;

import java.util.List;

@Data
public class TopGainerLooserData {

    private MetaModel metaModel;

    private List<StockValueData> topGainerLooserDataList;

}
