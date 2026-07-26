package org.example.remind.mapper.v1;

import org.example.remind.dto.request.v1.ArtistForPaintingRequestDtoV1;
import org.example.remind.dto.request.v1.ArtistRequestDtoV1;
import org.example.remind.dto.response.v1.ArtistResponseBaseDtoV1;
import org.example.remind.dto.response.v1.ArtistResponseExtendDtoV1;
import org.example.remind.entity.Artist;
import org.example.remind.service.command.ArtistCreateCommand;
import org.example.remind.service.command.ArtistUpdateCommand;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapperV1 {
  private final PaintingMapperV1 paintingMapperV1;

  @Lazy
  public ArtistMapperV1(PaintingMapperV1 paintingMapperV1) {
    this.paintingMapperV1 = paintingMapperV1;
  }

  public ArtistResponseBaseDtoV1 artistToBaseDto(Artist artist){
    if (artist == null){
      return null;
    }
    return new ArtistResponseBaseDtoV1(artist.getName());
  }

  public ArtistResponseExtendDtoV1 artistToExtendedDto(Artist artist){
    if (artist == null){
      return null;
    }
    return new ArtistResponseExtendDtoV1(
        artist.getId(),
        artist.getName(),
        artist.getBirthDate(),
        artist.getEra(),
        artist.getPaintings()
            .stream()
            .map(paintingMapperV1::paintingToBaseDto)
            .toList()
    );
  }

  public ArtistCreateCommand dtoToCreateCommand (ArtistRequestDtoV1 dto){
    if (dto == null){
      return null;
    }
    return new ArtistCreateCommand(
        dto.getName(),
        dto.getBirthDate(),
        dto.getEra()
    );
  }

  public ArtistUpdateCommand dtoToUpdateCommand (ArtistRequestDtoV1 dto, Long id){
        return new ArtistUpdateCommand(
        id,
        dto.getName(),
        dto.getBirthDate(),
        dto.getEra()
    );
  }

  public ArtistCreateCommand dtoForPaintingToCreateCommand (
      ArtistForPaintingRequestDtoV1 dto){
    if (dto == null){
      return null;
    }
    return new ArtistCreateCommand(
        dto.getName(),
        null,
        null);
  }

  public ArtistUpdateCommand dtoForPaintingToUpdateCommand (
      ArtistForPaintingRequestDtoV1 dto, Long id){
     return new ArtistUpdateCommand(
        id,
        dto.getName(),
        null,
        null);
  }
}
