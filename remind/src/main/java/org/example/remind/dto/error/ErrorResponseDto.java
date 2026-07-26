package org.example.remind.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd', time: 'HH:mm:ss")
  private LocalDateTime timestamp;
  private int status;
  private String error;
  private String message;
  private String path;
}
