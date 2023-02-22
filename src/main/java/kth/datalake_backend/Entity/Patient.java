package kth.datalake_backend.Entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotBlank;

@Node
public class Patient {


  @Id
  @GeneratedValue
  private Long id;
  @NotBlank
  private int age;

  @NotBlank
  private Gender gender;

  @NotBlank
  private String ethnicity;

  @NotBlank
  private int subjectId;

  public int getAge() {
    return age;
  }

  public Gender getGender() {
    return gender;
  }

  public String getEthnicity() {
    return ethnicity;
  }

  public int getSubjectId() {
    return subjectId;
  }

  @Override
  public String toString() {
    return "Patient{" +
      "age=" + age +
      ", gender=" + gender +
      ", ethnicity='" + ethnicity + '\'' +
      ", subjectId=" + subjectId +
      '}';
  }
}
