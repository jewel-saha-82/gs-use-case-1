package org.chart.data.processing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RootModel {

	private MetaModel meta;

	private ValuesModel value;

	private String status;

}
