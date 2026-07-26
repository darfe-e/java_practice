package org.example.remind.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.remind.entity.Artist;
import org.example.remind.entity.Painting;
import org.example.remind.repository.ArtistRepository;
import org.example.remind.repository.PaintingRepository;
import org.example.remind.service.command.ArtistCreateCommand;
import org.example.remind.service.command.ArtistUpdateCommand;
import org.example.remind.service.command.PaintingCreateCommand;
import org.example.remind.service.command.PaintingUpdateCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Data
@AllArgsConstructor
@Service
@Transactional
public class PaintingService {
  private PaintingRepository paintingRepository;
  private ArtistRepository artistRepository;

  public Page<Painting> getAllPaintingsOfAuthor (Long authorId, Pageable pageable){

    return paintingRepository.findAllByAuthorId(authorId, pageable);
  }

  public Painting getPaintingById (Long id){
    return paintingRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "The painting not found"));
  }

  public Painting createPainting (PaintingCreateCommand paintingCreateCommand){
    Painting painting = new Painting();
    Artist artistForPainting = artistRepository
        .findByNameIgnoreCase(paintingCreateCommand.getArtist().getName())
            .orElseGet(() -> {
              ArtistCreateCommand artistCommand = paintingCreateCommand.getArtist();
              Artist newArtist = new Artist();
              newArtist.setName(artistCommand.getName());
              newArtist.setBirthDate(artistCommand.getBirthDate());
              newArtist.setEra(artistCommand.getEra());

              return artistRepository.save(newArtist);
            });
    artistForPainting.getPaintings().add(painting);
    painting.setArtist(artistForPainting);
    painting.setName(paintingCreateCommand.getName());
    painting.setDate(paintingCreateCommand.getDate());
    painting.setType(paintingCreateCommand.getType());
    artistForPainting.getPaintings().add(painting);
    artistRepository.save(artistForPainting);

    return paintingRepository.save(painting);
  }

  public Painting updatePainting (PaintingUpdateCommand paintingCommand){
    Painting painting = paintingRepository.findById(
        paintingCommand.getId()
    ).orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "The painting for change not found"));

    Artist artistForPainting = artistRepository
        .findByNameIgnoreCase(paintingCommand.getArtist().getName())
        .orElseGet(() -> {
          ArtistUpdateCommand artistCommand = paintingCommand.getArtist();
          Artist newArtist = new Artist();
          newArtist.setName(artistCommand.getName());
          newArtist.setBirthDate(artistCommand.getBirthDate());
          newArtist.setEra(artistCommand.getEra());

          return artistRepository.save(newArtist);
        });

    painting.setArtist(artistForPainting);
    painting.setName(paintingCommand.getName());
    painting.setDate(paintingCommand.getDate());
    painting.setType(paintingCommand.getType());

    return paintingRepository.save(painting);
  }

  public void deletePainting (Long id){
    Painting painting = paintingRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
        HttpStatus.NOT_FOUND, "The painting for change not found"));
    paintingRepository.delete(painting);
  }
}
