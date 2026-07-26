package org.example.remind.dto.response.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistResponseExtendDtoV1 {
  private Long id;

  private String name;
  private LocalDate birthDate;
  private String era;

  private List<PaintingResponseBaseDtoV1> paintings;
}
