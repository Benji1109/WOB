package hu.wob.wobrest.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ListingDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 4449288512230219158L;
    private UUID id;
    private String title;
    private String description;
    private UUID locationId;
    private BigDecimal listingPrice;
    private String currency;
    private Integer quantity;
    private Integer listingStatus;
    private Integer marketplace;
    @JsonFormat(pattern="MM/dd/yyyy")
    private Date uploadTime;
    private String ownerEmailAddress;
}