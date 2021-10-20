package org.api.service.model;

public class KafkaRootModel {

    private MetaModel meta;

    private ValuesModel value;

    private String status;

    public KafkaRootModel() {

    }

    public KafkaRootModel(MetaModel meta, ValuesModel value, String status) {
        this.meta = meta;
        this.value = value;
        this.status = status;
    }

    public MetaModel getMeta() {
        return meta;
    }

    public void setMeta(MetaModel meta) {
        this.meta = meta;
    }

    public ValuesModel getValue() {
        return value;
    }

    public void setValue(ValuesModel value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  "{" +
                "meta:" + meta +
                ", value:" + value +
                ", status:" + status + '\'' +"}";
    }
}
