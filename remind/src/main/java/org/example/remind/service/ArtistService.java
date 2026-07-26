package org.example.remind.service;

import lombok.AllArgsConstructor;
import org.example.remind.entity.Artist;
import org.example.remind.repository.ArtistRepository;
import org.example.remind.service.command.ArtistCreateCommand;
import org.example.remind.service.command.ArtistUpdateCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@AllArgsConstructor
public class ArtistService {
  private ArtistRepository artistRepository;

  public Page<Artist> getAll (Pageable pageable){
    return artistRepository.findAll(pageable);
  }

  public Artist getById (Long id){
    return artistRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "The artist not found"));
  }

  public Artist getByName (String name){
    return artistRepository.findByNameIgnoreCase(name)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "The artist not found"));
  }

  public Artist create (ArtistCreateCommand artistCommand){
    Artist artist = new Artist();
    artist.setEra(artistCommand.getEra());
    artist.setName(artistCommand.getName());
    artist.setBirthDate(artistCommand.getBirthDate());

    return artistRepository.save(artist);
  }

  public Artist update (ArtistUpdateCommand artistCommand){
    Artist artist = artistRepository.findById(artistCommand.getId())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Required author not found"));

    artist.setName(artistCommand.getName());
    artist.setEra(artistCommand.getEra());
    artist.setBirthDate(artistCommand.getBirthDate());

    return artistRepository.save(artist);
  }

  public void delete (Long id){
    Artist artist = artistRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Required author not found"));
    artistRepository.delete(artist);
  }

}
