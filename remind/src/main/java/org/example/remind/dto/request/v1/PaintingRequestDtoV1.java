package org.example.remind.dto.request.v1;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PaintingRequestDtoV1 {
  private String name;
  private String type;
  private LocalDate date;

  private ArtistForPaintingRequestDtoV1 artist;
}
