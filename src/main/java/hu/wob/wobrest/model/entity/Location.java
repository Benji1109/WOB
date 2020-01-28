package hu.wob.wobrest.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
public class Location implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 520048653083440765L;
    @Id
    @NotNull
    private UUID id;
    private String managerName;
    private String phone;
    private String addressPrimary;
    private String addressSecondary;
    private String country;
    private String town;
    private String postalCode;
    @OneToMany(mappedBy = "location")
    @JsonManagedReference("location_id")
    private List<Listing> listings = new ArrayList<>();

    public void addListing(Listing l) {
        this.listings.add(l);
        l.setLocation(this);
    }
 
    public void removeListing(Listing l) {
        this.listings.remove(l);
        l.setLocation(null);
    }
}