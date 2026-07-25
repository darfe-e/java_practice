package org.example.remind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "painting")
public class Painting {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NonNull
  private Long id;

  private String name;
  private String type;
  private LocalDate date;

  @ManyToOne
  @JoinColumn(name = "artist_id")
  private Artist artist;
}
