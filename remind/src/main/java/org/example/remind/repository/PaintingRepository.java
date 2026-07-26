package org.example.remind.repository;

import org.example.remind.entity.Painting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
  @Query("SELECT p FROM Painting p "
      + "WHERE p.artist.id = :artistId")
  Page<Painting> findAllByAuthorId (@Param("artistId")Long artistId, Pageable pageable);
}
