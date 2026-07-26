package org.example.remind.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "artist")
public class Artist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NonNull
  private Long id;

  private String name;
  private LocalDate birthDate;
  private String era;

  @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
  private List<Painting> paintings = new ArrayList<>();
}
