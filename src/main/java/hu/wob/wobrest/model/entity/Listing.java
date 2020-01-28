package hu.wob.wobrest.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Listing implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8531478734194340129L;
    @Id
    @NotNull
    private UUID id;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="location_id")
    @JsonBackReference("location_id")
    private Location location;
    @NotNull
    @Digits(integer = 2, fraction = 0)
    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal listingPrice;
    @NotNull
    @Length(max = 3)
    private String currency;
    @NotNull
    @DecimalMin(value = "0")
    private Integer quantity;
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="listing_status")
    @JsonBackReference("listing_status")
    private ListingStatus listingStatus;
    @NotNull
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="marketplace")
    @JsonBackReference("marketplace")
    private Marketplace marketplace;
    @NotNull
    @Temporal(value=TemporalType.DATE)
    @JsonFormat(pattern="MM/dd/yyyy")
    private Date uploadTime;
    @NotNull
    @Email
    private String ownerEmailAddress;
}