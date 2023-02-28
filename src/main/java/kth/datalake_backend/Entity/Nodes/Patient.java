package kth.datalake_backend.Entity.Nodes;

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
  private String dataset;

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

  @Relationship(type="Symptoms", direction = Relationship.Direction.OUTGOING)
  private Set<Symptoms> symptoms = new HashSet<>();

  @Relationship(type="Treatment", direction = Relationship.Direction.OUTGOING)
  private Set<Treatment> treatment = new HashSet<>();

  @Relationship(type = "Death", direction = Relationship.Direction.OUTGOING)
  private Set<CauseOfDeath> causeOfDeath = new HashSet<>();

  @Relationship(type = "newMalignancy", direction = Relationship.Direction.OUTGOING)
  private Set<NewMalignancy> newMalignancy = new HashSet<>();

  @Relationship(type = "OverallSurvivalStatus", direction = Relationship.Direction.OUTGOING)
  private Set<OverAllSurvivalStatus> overAllSurvivalStatus = new HashSet<>();

  public Patient() {}

  public Patient(int age, int subjectId) {
    this.age = age;
    this.subjectId = subjectId;
  }


  public String getDataset() {
    return dataset;
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

  public Set<Treatment> getTreatment() {return treatment;}

  public Set<CauseOfDeath> getCauseOfDeath() {return causeOfDeath;}

  public Set<NewMalignancy> getNewMalignancy() {return newMalignancy;}

  public Set<OverAllSurvivalStatus> getOverAllSurvivalStatus() {return overAllSurvivalStatus;}

  public void setDataset(String dataset) {
    this.dataset = dataset;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setGender(String gender) {
      switch (gender) {
        case "1.0", "Male" -> this.gender = Gender.MALE;
        case "2.0", "Female" -> this.gender = Gender.FEMALE;
        default -> this.gender = Gender.UNKNOWN;
      }
  }

  public void setEthnicity(String ethnicity) {
    switch (ethnicity) {
      case "1.0", "White" -> this.ethnicity = "White";
      case "2.0" -> this.ethnicity = "Hispanic";
      case "3.0" -> this.ethnicity = "Black";
      case "4.0" -> this.ethnicity = "Oriental";
      case "5.0" -> this.ethnicity = "Native Hawaiian";
      case "6.0" -> this.ethnicity = "Native American";
      case "7.0" -> this.ethnicity = "Indian";
      case "8.0" -> this.ethnicity = "Filipino";
      case "9.0" -> this.ethnicity = "Other";
      case "10.0" -> this.ethnicity = "Patient refusal";
      default -> this.ethnicity = "Unknown";
    }
  }

  public void setSubjectId(int subjectId) {
    this.subjectId = subjectId;
  }

  public void setRelapse(String relapse) {
    switch (relapse) {
      case "1.0" -> this.relapse = "No";
      case "2.0" -> this.relapse = "Yes";
      default -> this.relapse = "Unknown";
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
      case "1.0" -> this.failureFreeSurvivalStatus = "Survived";
      case "2.0" -> this.failureFreeSurvivalStatus = "Deceased";
      default -> this.failureFreeSurvivalStatus = "Unknown";
    }
  }

  public void setFailureFreeSurvivalTime(double failureFreeSurvivalTime) {
    this.failureFreeSurvivalTime = failureFreeSurvivalTime;
  }

  public void setTreatment(Treatment treatment){this.treatment.add(treatment);}

  public void setCauseOfDeath(CauseOfDeath causeOfDeath){this.causeOfDeath.add(causeOfDeath);}

  public void setNewMalignancy(NewMalignancy newMalignancy){this.newMalignancy.add(newMalignancy);}

  public void setOverAllSurvivalStatus(OverAllSurvivalStatus overAllSurvivalStatus){this.overAllSurvivalStatus.add(overAllSurvivalStatus);}

  public Set<Symptoms> getSymptoms() {
    return symptoms;
  }

  public void setSymptoms(Symptoms symptoms) {
    this.symptoms.add(symptoms);
  }

  @Override
  public String toString() {
    return "Patient{" +
            "id=" + id +
            ", dataset='" + dataset + '\'' +
            ", age=" + age +
            ", gender=" + gender +
            ", ethnicity='" + ethnicity + '\'' +
            ", subjectId=" + subjectId +
            ", relapse='" + relapse + '\'' +
            ", survivalTime=" + survivalTime +
            ", relapseTime=" + relapseTime +
            ", failureFreeSurvivalStatus='" + failureFreeSurvivalStatus + '\'' +
            ", failureFreeSurvivalTime=" + failureFreeSurvivalTime +
            ", symptoms=" + symptoms +
            ", treatment=" + treatment +
            ", causeOfDeath=" + causeOfDeath +
            ", newMalignancy=" + newMalignancy +
            ", overAllSurvivalStatus=" + overAllSurvivalStatus +
            '}';
  }
}
