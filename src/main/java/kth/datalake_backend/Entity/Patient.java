package kth.datalake_backend.Entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

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

  @NotBlank
  private String relapse;

  @NotBlank
  private double survivalTime;

  @NotBlank
  private double relapseTime;

  @NotBlank
  private String failureFreeSurvivalStatus;

  @NotBlank
  private double failureFreeSurvivalTime;

  @Relationship(type="Treatment", direction = Relationship.Direction.OUTGOING)
  private Set<Treatment> Treatment = new HashSet<>();


  public Patient() {}

  public Patient(int age, int subjectId) {
    this.age = age;
    this.subjectId = subjectId;
  }


  public void setTreatment(Treatment treatment){
    this.Treatment.add(treatment);
  }

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

  public String getRelapse() {
    return relapse;
  }

  public double getSurvivalTime() {
    return survivalTime;
  }

  public String getFailureFreeSurvivalStatus() {
    return failureFreeSurvivalStatus;
  }

  public double getRelapseTime() {
    return relapseTime;
  }

  public double getFailureFreeSurvivalTime() {
    return failureFreeSurvivalTime;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setGender(String gender) {
      switch (gender) {
        case "1" -> this.gender = Gender.MALE;
        case "2" -> this.gender = Gender.FEMALE;
        default -> this.gender = Gender.UNKNOWN;
      }
  }

  public void setEthnicity(String ethnicity) {
    switch (ethnicity) {
      case "1": this.ethnicity = "White";
      break;
      case "2": this.ethnicity ="Hispanic";
        break;
      case "3": this.ethnicity = "Black";
        break;
      case "4": this.ethnicity = "Oriental";
        break;
      case "5": this.ethnicity = "Native Hawaiian";
        break;
      case "6": this.ethnicity = "Native American";
        break;
      case "7": this.ethnicity = "Indian";
        break;
      case "8": this.ethnicity = "Filipino";
        break;
      case "9": this.ethnicity = "Other";
        break;
      case "10": this.ethnicity = "Patient refusal";
        break;
      default: this.ethnicity = "Unknown";
    }
  }

  public void setSubjectId(int subjectId) {
    this.subjectId = subjectId;
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

  public void setRelapse(String relapse) {
    switch (relapse) {
      case "1": this.relapse = "No";
        break;
      case "2": this.relapse ="Yes";
        break;
      default: this.relapse = "Unknown";
    }
  }

  public void setSurvivalTime(double survivalTime) {
    this.survivalTime = survivalTime;
  }

  public void setRelapseTime(double relapseTime) {
    this.relapseTime = relapseTime;
  }

  public void setFailureFreeSurvivalStatus(String failureFreeSurvivalStatus) {
    switch (failureFreeSurvivalStatus) {
      case "1": this.failureFreeSurvivalStatus = "Survived";
        break;
      case "2": this.failureFreeSurvivalStatus ="Deceased";
        break;
      default: this.failureFreeSurvivalStatus = "Unknown";
    }
  }

  public void setFailureFreeSurvivalTime(double failureFreeSurvivalTime) {
    this.failureFreeSurvivalTime = failureFreeSurvivalTime;
  }
}
