package hu.wob.wobrest.model;

import java.util.UUID;

import lombok.Data;

@Data
public class ErrorMesseage {

    private UUID listingId;
    private String marketplaceName;
    private String invalidField;

    public ErrorMesseage(UUID listingId, String marketplaceName, String invalidField) {
        super();
        this.listingId = listingId;
        this.marketplaceName = marketplaceName;
        this.invalidField = invalidField;
    }

}