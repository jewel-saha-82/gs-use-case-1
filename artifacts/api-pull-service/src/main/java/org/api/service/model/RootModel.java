package org.api.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RootModel {

    private MetaModel meta;

    private List<ValuesModel> values;

    private String status;
}
