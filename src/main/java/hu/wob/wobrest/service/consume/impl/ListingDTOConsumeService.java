package hu.wob.wobrest.service.consume.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hu.wob.wobrest.model.dto.ListingDTO;
import hu.wob.wobrest.service.consume.ConsumeService;

@Service
public class ListingDTOConsumeService extends ConsumeService<ListingDTO[]> {

    @Autowired
    private Environment env;

    @Autowired
    public ListingDTOConsumeService() {
        super(ListingDTO[].class);
    }

    public ResponseEntity<ListingDTO[]> getListingDTO() throws Exception {
        return super.getResponse(env.getProperty("wob.rest.api.url.listing"));
    }
}
