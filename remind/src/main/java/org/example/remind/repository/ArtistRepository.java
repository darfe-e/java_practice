package org.example.remind.repository;

import org.example.remind.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

  Optional<Artist> findByNameIgnoreCase (String name);
}
