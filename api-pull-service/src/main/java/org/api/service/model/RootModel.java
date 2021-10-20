package org.api.service.model;

import java.util.List;

public class RootModel {

    private MetaModel meta;

    private List<ValuesModel> values;

    private String status;

    public RootModel() {
    }

    public RootModel(MetaModel meta, List<ValuesModel> values, String status) {
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

    public List<ValuesModel> getValues() {
        return values;
    }

    public void setValues(List<ValuesModel> values) {
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
        return "{" +
                "meta=" + meta +
                ", values=" + values +
                ", status='" + status + '\'' +
                '}';
    }
}
