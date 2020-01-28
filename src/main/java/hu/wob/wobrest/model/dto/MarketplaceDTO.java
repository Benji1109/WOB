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
public class MarketplaceDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 4009464854618947130L;
    private Long id;
    private String marketplaceName;
}