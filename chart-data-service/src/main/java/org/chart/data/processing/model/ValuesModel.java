package org.chart.data.processing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ValuesModel {
	private String datetime;
	private String open;
	private String high;
	private String low;
	private String close;
	private String volume;
}
