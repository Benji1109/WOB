package hu.wob.wobrest.service.consume.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hu.wob.wobrest.model.dto.ListingStatusDTO;
import hu.wob.wobrest.service.consume.ConsumeService;

@Service
public class ListingStatusDTOConsumeService extends ConsumeService<ListingStatusDTO[]> {

    @Autowired
    private Environment env;

    @Autowired
    public ListingStatusDTOConsumeService() {
        super(ListingStatusDTO[].class);
    }

    public ResponseEntity<ListingStatusDTO[]> getListingStatusDTO() throws Exception {
        return super.getResponse(env.getProperty("wob.rest.api.url.listing_status"));
    }
}
