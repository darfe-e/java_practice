package org.example.remind.controller.v1;

import lombok.AllArgsConstructor;
import org.example.remind.dto.request.v1.ArtistRequestDtoV1;
import org.example.remind.dto.response.v1.ArtistResponseBaseDtoV1;
import org.example.remind.dto.response.v1.ArtistResponseExtendDtoV1;
import org.example.remind.dto.response.v1.PaintingResponseBaseDtoV1;
import org.example.remind.mapper.v1.ArtistMapperV1;
import org.example.remind.mapper.v1.PaintingMapperV1;
import org.example.remind.service.ArtistService;
import org.example.remind.service.PaintingService;
import org.example.remind.service.command.ArtistCreateCommand;
import org.example.remind.service.command.ArtistUpdateCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/artists")
public class ArtistControllerV1 {
  private final ArtistService artistService;
  private ArtistMapperV1 artistMapper;

  private final PaintingService paintingService;
  private PaintingMapperV1 paintingMapper;

  @GetMapping("/")
  public ResponseEntity<Page<ArtistResponseBaseDtoV1>> getAllArtists(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size){
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<ArtistResponseBaseDtoV1> result= artistService.getAll(pageRequest)
        .map(artistMapper::artistToBaseDto);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ArtistResponseExtendDtoV1> getById (
      @PathVariable Long id){
    ArtistResponseExtendDtoV1 result = artistMapper.artistToExtendedDto(
        artistService.getById(id));
    return ResponseEntity.ok(result);
  }

  @GetMapping
  public ResponseEntity<ArtistResponseExtendDtoV1> getByName (
      @RequestParam String name){
    ArtistResponseExtendDtoV1 result = artistMapper.artistToExtendedDto(
        artistService.getByName(name));
    return ResponseEntity.ok(result);
  }

  @PostMapping
  public ResponseEntity<ArtistResponseExtendDtoV1> createArtist(
      @RequestBody ArtistRequestDtoV1 artistDto){
    ArtistCreateCommand artistCommand = artistMapper
        .dtoToCreateCommand(artistDto);
    ArtistResponseExtendDtoV1 result = artistMapper.artistToExtendedDto(
        artistService.create(artistCommand)
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ArtistResponseExtendDtoV1> updateArtist(
      @RequestBody ArtistRequestDtoV1 artistDto,
      @PathVariable Long id){
    ArtistUpdateCommand artistCommand = artistMapper
        .dtoToUpdateCommand(artistDto, id);
    ArtistResponseExtendDtoV1 result = artistMapper.artistToExtendedDto(
        artistService.update(artistCommand)
    );
    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteArtist (@PathVariable Long id){
    artistService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{authorId}/paintings")
  public ResponseEntity<Page<PaintingResponseBaseDtoV1>> getAllPaintingsOfArtist(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @PathVariable Long authorId){
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<PaintingResponseBaseDtoV1> result = paintingService
        .getAllPaintingsOfAuthor(authorId, pageRequest)
        .map(paintingMapper::paintingToBaseDto);
    return ResponseEntity.ok(result);
  }

}
