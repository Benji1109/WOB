package hu.wob.wobrest.repository;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.wob.wobrest.model.entity.Location;

@Repository
@Transactional
public interface LocationRepository extends JpaRepository<Location, UUID> {}