package hu.wob.wobrest.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import hu.wob.wobrest.deseralize.ListingJsonDeserializer;
import hu.wob.wobrest.model.ErrorMesseage;
import hu.wob.wobrest.model.dto.ListingDTO;
import hu.wob.wobrest.model.entity.Listing;
import hu.wob.wobrest.model.entity.ListingStatus;
import hu.wob.wobrest.model.entity.Location;
import hu.wob.wobrest.model.entity.Marketplace;
import hu.wob.wobrest.repository.ListingRepository;
import hu.wob.wobrest.repository.ListingStatusRepository;
import hu.wob.wobrest.repository.LocationRepository;
import hu.wob.wobrest.repository.MarketplaceRepository;
import hu.wob.wobrest.service.consume.impl.ListingDTOConsumeService;
import hu.wob.wobrest.service.consume.impl.ListingStatusDTOConsumeService;
import hu.wob.wobrest.service.consume.impl.LocationDTOConsumeService;
import hu.wob.wobrest.service.consume.impl.MarketplaceDTOConsumeService;
import hu.wob.wobrest.service.file.FileService;


@Component
@Transactional
public class MapDTOsToEntity {
    Logger logger = LoggerFactory.getLogger(MapDTOsToEntity.class);

    @Autowired
    private ListingDTOConsumeService listingDTOConsumeService;
    @Autowired
    private ListingStatusDTOConsumeService listingStatusDTOConsumeService;
    @Autowired
    private MarketplaceDTOConsumeService marketplaceDTOConsumeService;
    @Autowired
    private LocationDTOConsumeService locationDTOConsumeService;

    @Autowired
    private MarketplaceRepository marketplaceRepository;
    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ListingStatusRepository listingStatusRepository;

    public void init() throws InterruptedException {
        this.locationMapJsonToEntity();
        this.marketplaceMapJsonToEntity();
        this.listingStatusMapJsonToEntity();
        this.listingMapJsonToEntity();
    }

    private void listingMapJsonToEntity() {
        List<Listing> dtoApplications = new ArrayList<>();
        List<ErrorMesseage> errors = new ArrayList<>();
        try {
            ObjectMapper mapper = this.listingDTOMapJsonToEntity();
            ListingDTO[] body = this.listingDTOConsumeService.getListingDTO().getBody();
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            for (ListingDTO listingDTO : body) {
                try {
                    Listing listing = mapper.convertValue(listingDTO, new TypeReference<Listing>() {
                    });
                    dtoApplications.add(listing);
                    Optional<ListingStatus> findById3 = this.listingStatusRepository
                            .findById(listing.getListingStatus().getId());
                    Optional<Location> findById = this.locationRepository.findById(listing.getLocation().getId());
                    Optional<Marketplace> findById2 = this.marketplaceRepository
                            .findById(listing.getMarketplace().getId());

                    Set<ConstraintViolation<Listing>> violations = validator.validate(listing);

                    validateListing(dtoApplications, errors, listing, findById3, findById, findById2, violations);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            FileService.writeObjectToFile(errors);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private ObjectMapper listingDTOMapJsonToEntity() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Listing.class, new ListingJsonDeserializer());
        mapper.registerModule(module);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        return mapper;
    }

    private void validateListing(List<Listing> dtoApplications, List<ErrorMesseage> errors, Listing listing,
            Optional<ListingStatus> findById3, Optional<Location> findById, Optional<Marketplace> findById2,
            Set<ConstraintViolation<Listing>> violations) {
        if (findById.isPresent() && findById2.isPresent() && findById3.isPresent() && violations.isEmpty()) {
            this.saveAndFlush(listing, findById3, findById, findById2);
        } else {
            errors.add(new ErrorMesseage(listing.getId(), findById2.get().getMarketplaceName(),
                    violations.stream().map(e -> e.getPropertyPath().toString()).collect(Collectors.joining(","))));
            dtoApplications.remove(listing);
        }
    }

    private void saveAndFlush(Listing listing, Optional<ListingStatus> findById3, Optional<Location> findById,
            Optional<Marketplace> findById2) {
        findById.get().addListing(listing);
        findById2.get().addListing(listing);
        findById3.get().addListing(listing);
        this.listingRepository.saveAndFlush(listing);
        this.marketplaceRepository.saveAndFlush(findById2.get());
        this.locationRepository.saveAndFlush(findById.get());
        this.listingStatusRepository.saveAndFlush(findById3.get());
    }

    private void listingStatusMapJsonToEntity() {
        List<ListingStatus> dtoApplications = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            dtoApplications = mapper.convertValue(this.listingStatusDTOConsumeService.getListingStatusDTO().getBody(),
                    new TypeReference<List<ListingStatus>>() {
                    });
            dtoApplications.forEach(e -> this.listingStatusRepository.saveAndFlush(e));

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void marketplaceMapJsonToEntity() {
        List<Marketplace> dtoApplications = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            dtoApplications = mapper.convertValue(this.marketplaceDTOConsumeService.getMarketplaceDTO().getBody(),
                    new TypeReference<List<Marketplace>>() {
                    });
            dtoApplications.forEach(e -> this.marketplaceRepository.saveAndFlush(e));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void locationMapJsonToEntity() {
        List<Location> dtoApplications = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
            dtoApplications = mapper.convertValue(this.locationDTOConsumeService.getLocationDTO().getBody(),
                    new TypeReference<List<Location>>() {
                    });
            dtoApplications.forEach(e -> this.locationRepository.saveAndFlush(e));
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

}