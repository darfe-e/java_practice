package org.example.remind.service.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ArtistUpdateCommand {
  private Long id;
  private String name;
  private LocalDate birthDate;
  private String era;
}
