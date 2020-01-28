package hu.wob.wobrest.repository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.wob.wobrest.model.entity.Listing;


@Repository
@Transactional
public interface ListingRepository extends JpaRepository<Listing, Long> {}