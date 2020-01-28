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
public class Marketplace implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -5705210588573391211L;
    @Id
    @NotNull
    private Long id;
    private String marketplaceName;
    @OneToMany(mappedBy = "marketplace")
    @JsonManagedReference("marketplace")
    private List<Listing> listings = new ArrayList<>();
    
    public void addListing(Listing l) {
        this.listings.add(l);
        l.setMarketplace(this);
    }
 
    public void removeListing(Listing l) {
        this.listings.remove(l);
        l.setMarketplace(null);
    }
}