package hu.wob.wobrest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.wob.wobrest.model.entity.Marketplace;


@Repository
@Transactional
public interface MarketplaceRepository extends JpaRepository<Marketplace, Long> {}