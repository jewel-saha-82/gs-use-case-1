package org.chart.data.processing.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartData {
	private String symbol;
	private String stockName;
	private String date;
	private BigDecimal closingPrice;
	private String currency;
}
