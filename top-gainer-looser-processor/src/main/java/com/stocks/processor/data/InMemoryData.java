package com.stocks.processor.data;

import com.stocks.processor.model.MetaModel;
import com.stocks.processor.model.ValuesModel;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class InMemoryData {

    List<ValuesModel> stocksData = new ArrayList<>();

    MetaModel metaModel = null;
}
