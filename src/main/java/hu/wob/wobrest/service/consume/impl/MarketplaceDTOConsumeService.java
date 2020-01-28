package hu.wob.wobrest.service.consume.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import hu.wob.wobrest.model.dto.MarketplaceDTO;
import hu.wob.wobrest.service.consume.ConsumeService;

@Service
public class MarketplaceDTOConsumeService extends ConsumeService<MarketplaceDTO[]> {

    @Autowired
    private Environment env;

    @Autowired
    public MarketplaceDTOConsumeService() {
        super(MarketplaceDTO[].class);
    }

    public ResponseEntity<MarketplaceDTO[]> getMarketplaceDTO() throws Exception {
        return super.getResponse(env.getProperty("wob.rest.api.url.marketplace"));
    }
}
