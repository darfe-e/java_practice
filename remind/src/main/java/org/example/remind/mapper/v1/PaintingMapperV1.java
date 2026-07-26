package org.example.remind.mapper.v1;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.remind.dto.request.v1.PaintingRequestDtoV1;
import org.example.remind.dto.response.v1.ArtistResponseBaseDtoV1;
import org.example.remind.dto.response.v1.PaintingResponseBaseDtoV1;
import org.example.remind.dto.response.v1.PaintingResponseExtendDtoV1;
import org.example.remind.entity.Artist;
import org.example.remind.entity.Painting;
import org.example.remind.service.command.PaintingCreateCommand;
import org.example.remind.service.command.PaintingUpdateCommand;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PaintingMapperV1 {
  private final ArtistMapperV1 artistMapperV1;

  public PaintingResponseBaseDtoV1 paintingToBaseDto(Painting painting){
    if (painting == null){
      return null;
    }
    Artist artist = painting.getArtist();
    ArtistResponseBaseDtoV1 artistDto = artistMapperV1.artistToBaseDto(artist);

    return new PaintingResponseBaseDtoV1(painting.getName(), artistDto);
  }

  public PaintingResponseExtendDtoV1 paintingToExtendedDto(Painting painting){
    if (painting == null){
      return null;
    }
    return new PaintingResponseExtendDtoV1(
        painting.getId(),
        painting.getName(),
        painting.getType(),
        painting.getDate(),
        artistMapperV1.artistToBaseDto(painting.getArtist())
    );
  }

  public PaintingCreateCommand dtoToCreateCommand (
      PaintingRequestDtoV1 dto){
    if (dto == null){
      return null;
    }
    return new PaintingCreateCommand(
        dto.getName(),
        dto.getType(),
        dto.getDate(),
        artistMapperV1.dtoForPaintingToCreateCommand(dto.getArtist())
    );
  }

  public PaintingUpdateCommand dtoToUpdateCommand (
      PaintingRequestDtoV1 dto, Long id, Long artistId){
    return new PaintingUpdateCommand(
        id,
        dto.getName(),
        dto.getType(),
        dto.getDate(),
        artistMapperV1.dtoForPaintingToUpdateCommand(dto.getArtist(), artistId)
    );
  }

}
