package org.example.remind.controller.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.remind.dto.request.v1.PaintingRequestDtoV1;
import org.example.remind.dto.response.v1.PaintingResponseBaseDtoV1;
import org.example.remind.dto.response.v1.PaintingResponseExtendDtoV1;
import org.example.remind.mapper.v1.PaintingMapperV1;
import org.example.remind.service.PaintingService;
import org.example.remind.service.command.PaintingCreateCommand;
import org.example.remind.service.command.PaintingUpdateCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/api/v1/paintings")
public class PaintingControllerV1 {
  private final PaintingService paintingService;
  private PaintingMapperV1 paintingMapper;

  @GetMapping("/{id}")
  public ResponseEntity<PaintingResponseExtendDtoV1> getById (
      @PathVariable Long id){
    PaintingResponseExtendDtoV1 result = paintingMapper.paintingToExtendedDto(
        paintingService.getPaintingById(id));
    return ResponseEntity.ok(result);
  }

  @PostMapping
  public ResponseEntity<PaintingResponseExtendDtoV1> createArtist(
      @RequestBody PaintingRequestDtoV1 paintingDto){
    PaintingCreateCommand artistCommand = paintingMapper
        .dtoToCreateCommand(paintingDto);
    PaintingResponseExtendDtoV1 result = paintingMapper.paintingToExtendedDto(
        paintingService.createPainting(artistCommand)
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @PutMapping("/{authorId}/{id}")
  public ResponseEntity<PaintingResponseExtendDtoV1> updateArtist(
      @RequestBody PaintingRequestDtoV1 paintingDto,
      @PathVariable Long id,
      @PathVariable Long authorId){
    PaintingUpdateCommand paintingCommand = paintingMapper
        .dtoToUpdateCommand(paintingDto, id, authorId);
    PaintingResponseExtendDtoV1 result = paintingMapper.paintingToExtendedDto(
        paintingService.updatePainting(paintingCommand)
    );
    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteArtist (@PathVariable Long id){
    paintingService.deletePainting(id);
    return ResponseEntity.noContent().build();
  }
}
