package hu.wob.wobrest.deseralize;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import hu.wob.wobrest.model.entity.Listing;
import hu.wob.wobrest.model.entity.ListingStatus;
import hu.wob.wobrest.model.entity.Location;
import hu.wob.wobrest.model.entity.Marketplace;

public class ListingJsonDeserializer extends JsonDeserializer<Listing> {
    @Override
    public Listing deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        Listing listing = new Listing();
        JsonNode node = jp.readValueAsTree();
        listing.setId(UUID.fromString(node.get("id").asText()));
        listing.setCurrency(node.get("currency").asText());
        listing.setDescription(node.get("description").asText());
        listing.setListingPrice(BigDecimal.valueOf(node.get("listing_price").asLong()));

        ListingStatus listingStatus = new ListingStatus();
        listingStatus.setId((node.get("listing_status").asLong()));
        listing.setListingStatus(listingStatus);

        Location location = new Location();
        location.setId(UUID.fromString(node.get("location_id").asText()));
        listing.setLocation(location);

        Marketplace marketplace = new Marketplace();
        marketplace.setId(node.get("marketplace").asLong());
        listing.setMarketplace(marketplace);

        listing.setOwnerEmailAddress(node.get("owner_email_address").asText());
        listing.setQuantity(node.get("quantity").asInt());
        listing.setTitle(node.get("title").asText());
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = simpleDateFormat.parse(node.get("upload_time").asText());
        } catch (ParseException e) {
            date = null;
        }
        listing.setUploadTime(date);

        return listing;
    }
}
