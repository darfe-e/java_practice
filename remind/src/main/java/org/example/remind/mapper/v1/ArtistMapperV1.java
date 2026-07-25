package org.example.remind.mapper.v1;

import lombok.NoArgsConstructor;
import org.example.remind.dto.request.v1.ArtistForPaintingRequestDtoV1;
import org.example.remind.dto.request.v1.ArtistRequestDtoV1;
import org.example.remind.dto.response.v1.ArtistResponseBaseDtoV1;
import org.example.remind.dto.response.v1.ArtistResponseExtendDtoV1;
import org.example.remind.entity.Artist;
import org.example.remind.service.command.ArtistCreateCommand;
import org.example.remind.service.command.ArtistUpdateCommand;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class ArtistMapperV1 {
  private PaintingMapperV1 paintingMapperV1;

  public ArtistResponseBaseDtoV1 artistToBaseDto(Artist artist){
    return new ArtistResponseBaseDtoV1(artist.getName());
  }

  public ArtistResponseExtendDtoV1 artistToExtendedDto(Artist artist){
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
    return new ArtistCreateCommand(
        dto.getName(),
        dto.getBirthDate(),
        dto.getEra()
    );
  }

  public ArtistUpdateCommand dtoToCreateCommand (ArtistRequestDtoV1 dto, Long id){
    return new ArtistUpdateCommand(
        id,
        dto.getName(),
        dto.getBirthDate(),
        dto.getEra()
    );
  }

  public ArtistCreateCommand dtoForPaintingToCreateCommand (
      ArtistForPaintingRequestDtoV1 dto){
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
