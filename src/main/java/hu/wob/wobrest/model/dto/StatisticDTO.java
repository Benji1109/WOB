package hu.wob.wobrest.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StatisticDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 4449288512230219158L;
    private Integer totalListingCount;
    private Integer totalListingPrice;
    private Float avgListingPrice;

}