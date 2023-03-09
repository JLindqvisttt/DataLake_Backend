package kth.datalake_backend.Entity.Nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Patient Node
 */
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

@NotBlank
  private String dataset;

  @Relationship(type="Treatment", direction = Relationship.Direction.OUTGOING)
  private Treatment treatment;

  @Relationship(type = "CauseOfDeath", direction = Relationship.Direction.OUTGOING)
  private CauseOfDeath causeOfDeath;

  @Relationship(type = "NewMalignancy", direction = Relationship.Direction.OUTGOING)
  private NewMalignancy newMalignancy;

  @Relationship(type = "OverallSurvivalStatus", direction = Relationship.Direction.OUTGOING)
  private OverAllSurvivalStatus overAllSurvivalStatus;

  @Relationship(type="Symptoms", direction = Relationship.Direction.OUTGOING)
  private List<Symptoms> symptoms;



  public Patient() {}

  public Patient(Long id, int age, Gender gender, String ethnicity, int subjectId, String relapse, double survivalTime, double relapseTime, String failureFreeSurvivalStatus, double failureFreeSurvivalTime, String dataset) {
    this.id = id;
    this.age = age;
    this.gender = gender;
    this.ethnicity = ethnicity;
    this.subjectId = subjectId;
    this.relapse = relapse;
    this.survivalTime = survivalTime;
    this.relapseTime = relapseTime;
    this.failureFreeSurvivalStatus = failureFreeSurvivalStatus;
    this.failureFreeSurvivalTime = failureFreeSurvivalTime;
    this.dataset = dataset;
  }

  /**
   * Get patients dataset
   * @return
   */
  public String getDataset() {return dataset;}

  /**
   * Get patients age
   * @return age of the patient
   */
  public int getAge() {
    return age;
  }

  /**
   * Get patients gender
   * @return gender of patient
   */
  public Gender getGender() {
    return gender;
  }

  /**
   * Get patients ethnicity
   * @return ethnicity of patient
   */
  public String getEthnicity() {
    return ethnicity;
  }

  /**
   * Get patients subjectID
   * @return subjectID of patient
   */
  public int getSubjectId() {
    return subjectId;
  }

  /**
   * Get patients relapse
   * @return current relapse
   */
  public String getRelapse() {
    return relapse;
  }

  /**
   * Get patients survival time
   * @return time of survival
   */
  public double getSurvivalTime() {
    return survivalTime;
  }

  /**
   * Get patients failure free survival status
   * @return status of failure free survival
   */
  public String getFailureFreeSurvivalStatus() {
    return failureFreeSurvivalStatus;
  }

  /**
   * Get patients relapse time
   * @return relapse time of patient
   */
  public double getRelapseTime() {
    return relapseTime;
  }

  /**
   * Get patients failure free survival time
   * @return time of failure free survival
   */
  public double getFailureFreeSurvivalTime() {
    return failureFreeSurvivalTime;
  }

  /**
   * Get patients treatments
   * @return treatments patients has gotten
   * @see Treatment
   */
  public Treatment getTreatment() {return treatment;}

  /**
   * Get patients cause of death
   * @return cause of death
   * @see CauseOfDeath
   */
  public CauseOfDeath getCauseOfDeath() {return causeOfDeath;}

  /**
   * Get new malignancy
   * @return new malignancy
   * @see NewMalignancy
   */
  public NewMalignancy getNewMalignancy() {return newMalignancy;}

  /**
   * Get over all survival status of patient
   * @return over all survival status
   * @see OverAllSurvivalStatus
   */
  public OverAllSurvivalStatus getOverAllSurvivalStatus() {return overAllSurvivalStatus;}

  /**
   * Get the symptoms patient has showed
   * @return a list of symptoms
   */
  public List<Symptoms> getSymptoms() {
    return symptoms;
  }

  /**
   * Set dataset of patient
   * @param dataset the dataset the patient is loaded from
   */
  public void setDataset(String dataset) {this.dataset = dataset;}

  /**
   * Set age of patient
   * @param age the age to set
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * Set patient gender
   * @param gender
   */
  public void setGender(String gender) {
      switch (gender) {
        case "1.0","1", "Male" -> this.gender = Gender.MALE;
        case "2.0","2", "Female" -> this.gender = Gender.FEMALE;
        default -> this.gender = Gender.UNKNOWN;
      }
  }

  /**
   * Set patient ethnicity
   * @param ethnicity
   */
  public void setEthnicity(String ethnicity) {
    switch (ethnicity) {
      case "1.0","1", "White" -> this.ethnicity = "White";
      case "2.0","2" -> this.ethnicity = "Hispanic";
      case "3.0","3" -> this.ethnicity = "Black";
      case "4.0","4" -> this.ethnicity = "Oriental";
      case "5.0","5" -> this.ethnicity = "Native Hawaiian";
      case "6.0","6" -> this.ethnicity = "Native American";
      case "7.0","7" -> this.ethnicity = "Indian";
      case "8.0","8" -> this.ethnicity = "Filipino";
      case "9.0","9" -> this.ethnicity = "Other";
      case "10.0","10" -> this.ethnicity = "Patient refusal";
      default -> this.ethnicity = "Unknown";
    }
  }

  /**
   * Set patients subjectID
   * @param subjectId
   */
  public void setSubjectId(int subjectId) {
    this.subjectId = subjectId;
  }

  /**
   * Set relapse depending on input value
   * @param relapse string value of 1-2, unknown if not one of these
   */
  public void setRelapse(String relapse) {
    switch (relapse) {
      case "1.0","1" -> this.relapse = "No";
      case "2.0","2" -> this.relapse = "Yes";
      default -> this.relapse = "Unknown";
    }
  }

  /**
   * Set survival time
   * @param survivalTime the time to set
   */
  public void setSurvivalTime(double survivalTime) {
    this.survivalTime = survivalTime;
  }

  /**
   * Set relapse time
   * @param relapseTime the time to set
   */
  public void setRelapseTime(double relapseTime) {
    this.relapseTime = relapseTime;
  }

  /**
   * Set failure free survival status depending on input value
   * @param failureFreeSurvivalStatus string value of 1-2, unknown if not one of these
   */
  public void setFailureFreeSurvivalStatus(String failureFreeSurvivalStatus) {
    switch (failureFreeSurvivalStatus) {
      case "1.0","1" -> this.failureFreeSurvivalStatus = "Survived";
      case "2.0","2" -> this.failureFreeSurvivalStatus = "Deceased";
      default -> this.failureFreeSurvivalStatus = "Unknown";
    }
  }

  /**
   * Set failure free survival time
   * @param failureFreeSurvivalTime time to set
   */
  public void setFailureFreeSurvivalTime(double failureFreeSurvivalTime) {
    this.failureFreeSurvivalTime = failureFreeSurvivalTime;
  }

  /**
   * Set treatment
   * @param treatment treatment to set
   */
  public void setTreatment(Treatment treatment){this.treatment = treatment;}

  /**
   * Set cause of death
   * @param causeOfDeath cause of death to set
   */
  public void setCauseOfDeath(CauseOfDeath causeOfDeath){this.causeOfDeath = causeOfDeath;}

  /**
   * Set new malignancy
   * @param newMalignancy the new malignancy to set
   */
  public void setNewMalignancy(NewMalignancy newMalignancy){this.newMalignancy = newMalignancy;}

  /**
   * Set over all survival status
   * @param overAllSurvivalStatus the status to set
   */
  public void setOverAllSurvivalStatus(OverAllSurvivalStatus overAllSurvivalStatus){this.overAllSurvivalStatus = overAllSurvivalStatus;}

  /**
   * Set the symptoms, if no previous symptoms create new list otherwise add to the list
   * @param symptoms the symptoms to set
   */
  public void setSymptoms(Symptoms symptoms) {
    if(this.symptoms == null) this.symptoms = new ArrayList<>();
    this.symptoms.add(symptoms);
  }

  @Override
  public String toString() {
    return "Patient{" +
            "id=" + id +
            ", age=" + age +
            ", gender=" + gender +
            ", ethnicity='" + ethnicity + '\'' +
            ", subjectId=" + subjectId +
            ", relapse='" + relapse + '\'' +
            ", survivalTime=" + survivalTime +
            ", relapseTime=" + relapseTime +
            ", failureFreeSurvivalStatus='" + failureFreeSurvivalStatus + '\'' +
            ", failureFreeSurvivalTime=" + failureFreeSurvivalTime +
            ", treatment=" + treatment +
            ", causeOfDeath=" + causeOfDeath +
            ", newMalignancy=" + newMalignancy +
            ", overAllSurvivalStatus=" + overAllSurvivalStatus +
            ", symptoms=" + symptoms +
            '}';
  }
}
