package hu.wob.wobrest.model.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReportDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 4449288512230219158L;
    private Integer totalListingCount;
    private StatisticDTO ebay;
    private StatisticDTO amazon;
    private String bestListerEmailAdress;
    private Map<Integer, MonthlyReportDTO> monthlyReports = new HashMap<>();

}