package org.example.remind.dto.request.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistRequestDtoV1 {

  private String name;
  private LocalDate birthDate;
  private String era;

}
