package org.example.remind.dto.response.v1;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PaintingResponseExtendDtoV1 {
  private Long id;

  private String name;
  private String type;
  private LocalDate date;

  private ArtistResponseBaseDtoV1 artist;
}
