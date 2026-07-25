package org.example.remind.dto.response.v1;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaintingResponseBaseDtoV1 {
  private String name;
  private ArtistResponseBaseDtoV1 artist;
}
