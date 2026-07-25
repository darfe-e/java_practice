package org.example.remind.service.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PaintingUpdateCommand {
  private Long id;
  private String name;
  private String type;
  private LocalDate date;

  private ArtistUpdateCommand artist;
}
