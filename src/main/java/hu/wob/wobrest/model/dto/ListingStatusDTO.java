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
public class ListingStatusDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7133674561346641873L;
    private Long id;
    private String statusName;
}