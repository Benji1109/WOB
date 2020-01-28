package hu.wob.wobrest.service.consume.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hu.wob.wobrest.model.dto.LocationDTO;
import hu.wob.wobrest.service.consume.ConsumeService;

@Service
public class LocationDTOConsumeService extends ConsumeService<LocationDTO[]> {

    @Autowired
    private Environment env;

    @Autowired
    public LocationDTOConsumeService() {
        super(LocationDTO[].class);
    }

    public ResponseEntity<LocationDTO[]> getLocationDTO() throws Exception {
        return super.getResponse(env.getProperty("wob.rest.api.url.location"));
    }
}
