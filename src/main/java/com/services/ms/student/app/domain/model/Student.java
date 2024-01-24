package com.services.ms.student.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

  private Long id;
  private String firstname;
  private String lastname;
  private Integer age;
  private String address;

}
