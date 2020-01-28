package hu.wob.wobrest.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ListingStatus implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -9015542537499301703L;
    @Id
    @NotNull
    private Long id;
    private String statusName;
    @OneToMany(mappedBy = "listingStatus")
    @JsonManagedReference(value = "listing_status")
    private List<Listing> listings = new ArrayList<>();

    public void addListing(Listing l) {
        this.listings.add(l);
        l.setListingStatus(this);
    }
 
    public void removeListing(Listing l) {
        this.listings.remove(l);
        l.setListingStatus(null);
    }
}