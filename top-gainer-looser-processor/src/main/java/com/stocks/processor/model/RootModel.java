package com.stocks.processor.model;

import java.util.List;

public class RootModel {

    private MetaModel meta;

    private ValuesModel values;

    private String status;

    public RootModel() {
    }

    public RootModel(MetaModel meta, ValuesModel values, String status) {
        this.meta = meta;
        this.values = values;
        this.status = status;
    }

    public MetaModel getMeta() {
        return meta;
    }

    public void setMeta(MetaModel meta) {
        this.meta = meta;
    }

    public ValuesModel getValues() {
        return values;
    }

    public void setValues(ValuesModel values) {
        this.values = values;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MetaDao{" +
                "meta='" + meta + '\'' +
                ", values=" + values +
                ", status='" + status + '\'' +
                '}';
    }
}
