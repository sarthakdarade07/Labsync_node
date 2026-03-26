package com.labsync.lms.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

/**
 * AlgorithmRun entity — audit log for each Genetic Algorithm execution.
 * Stores fitness scores, generation count, and execution metadata.
 */
@Entity
@Table(name = "algorithm_runs")
public class AlgorithmRun {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private Integer generations;
  @Column(nullable = false)
  private Integer populationSize;
  @Column(nullable = false)
  private Double bestFitnessScore;
  @Column(nullable = false)
  private Double crossoverRate;
  @Column(nullable = false)
  private Double mutationRate;
  /**
   * Status: RUNNING, COMPLETED, FAILED
   */
  @Column(nullable = false, length = 20)
  private String status;
  @Column(nullable = false)
  private boolean scheduleApplied;
  @Column(length = 500)
  private String notes;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "triggered_by")
  private User triggeredBy;
  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime startedAt;
  private LocalDateTime completedAt;

  public boolean isScheduleApplied() {
    return scheduleApplied;
  }

  public double getBestFitnessScore() {
    return bestFitnessScore;
  }

  public Long getId() {
    return id;
  }

  private static String $default$status() {
    return "RUNNING";
  }

  private static boolean $default$scheduleApplied() {
    return false;
  }


  public static class AlgorithmRunBuilder {
    private Long id;
    private Integer generations;
    private Integer populationSize;
    private Double bestFitnessScore;
    private Double crossoverRate;
    private Double mutationRate;
    private boolean status$set;
    private String status$value;
    private boolean scheduleApplied$set;
    private boolean scheduleApplied$value;
    private String notes;
    private User triggeredBy;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    AlgorithmRunBuilder() {
    }

    /**
     * @return {@code this}.
     */
    public AlgorithmRun.AlgorithmRunBuilder id(final Long id) {
      this.id = id;
      return this;
    }

    /**
     * @return {@code this}.
     */
    public AlgorithmRun.AlgorithmRunBuilder generations(final Integer generations) {
      this.generations = generations;
      return this;
    }

    /**
     * @return {@code this}.
     */
    public AlgorithmRun.AlgorithmRunBuilder populationSize(final Integer populationSize) {
      this.populationSize = populationSize;
      return this;
    }

    /**
     * @return {@code this}.
     */
    public AlgorithmRun.AlgorithmRunBuilder bestFitnessScore(final Double bestFitnessScore) {
      this.bestFitnessScore = bestFitnessScore;
      return this;
    }

    /**
     * @return {@code this}.
     */
    public AlgorithmRun.AlgorithmRunBuilder crossoverRate(final Double crossoverRate) {
      this.crossoverRate = crossoverRate;
      return this;
    }

    /**
     * @return {@code this}.
     */
    public AlgorithmRun.AlgorithmRunBuilder mutationRate(final Double mutationRate) {
      this.mutationRate = mutationRate;
      return this;
    }

    /**
     * Status: RUNNING, COMPLETED, FAILED
     * @return {@code this}.
     */
    public AlgorithmRun.AlgorithmRunBuilder status(final String status) {
      this.status$value = status;
      status$set = true;
      return this;
    }

    /**
     * @return {@code this}.
     */
    public AlgorithmRun.AlgorithmRunBuilder scheduleApplied(final boolean scheduleApplied) {
      this.scheduleApplied$value = scheduleApplied;
      scheduleApplied$set = true;
      return this;
    }

    /**
     * @return {@code this}.
     */
    public AlgorithmRun.AlgorithmRunBuilder notes(final String notes) {
      this.notes = notes;
      return this;
    }

    /**
     * @return {@code this}.
     */
    public AlgorithmRun.AlgorithmRunBuilder triggeredBy(final User triggeredBy) {
      this.triggeredBy = triggeredBy;
      return this;
    }

    /**
     * @return {@code this}.
     */
    public AlgorithmRun.AlgorithmRunBuilder startedAt(final LocalDateTime startedAt) {
      this.startedAt = startedAt;
      return this;
    }

    /**
     * @return {@code this}.
     */
    public AlgorithmRun.AlgorithmRunBuilder completedAt(final LocalDateTime completedAt) {
      this.completedAt = completedAt;
      return this;
    }

    public AlgorithmRun build() {
      String status$value = this.status$value;
      if (!this.status$set) status$value = AlgorithmRun.$default$status();
      boolean scheduleApplied$value = this.scheduleApplied$value;
      if (!this.scheduleApplied$set) scheduleApplied$value = AlgorithmRun.$default$scheduleApplied();
      return new AlgorithmRun(this.id, this.generations, this.populationSize, this.bestFitnessScore, this.crossoverRate, this.mutationRate, status$value, scheduleApplied$value, this.notes, this.triggeredBy, this.startedAt, this.completedAt);
    }

    @java.lang.Override
    public java.lang.String toString() {
      return "AlgorithmRun.AlgorithmRunBuilder(id=" + this.id + ", generations=" + this.generations + ", populationSize=" + this.populationSize + ", bestFitnessScore=" + this.bestFitnessScore + ", crossoverRate=" + this.crossoverRate + ", mutationRate=" + this.mutationRate + ", status$value=" + this.status$value + ", scheduleApplied$value=" + this.scheduleApplied$value + ", notes=" + this.notes + ", triggeredBy=" + this.triggeredBy + ", startedAt=" + this.startedAt + ", completedAt=" + this.completedAt + ")";
    }
  }

  public static AlgorithmRun.AlgorithmRunBuilder builder() {
    return new AlgorithmRun.AlgorithmRunBuilder();
  }

  public Integer getGenerations() {
    return this.generations;
  }

  public Integer getPopulationSize() {
    return this.populationSize;
  }

  public Double getCrossoverRate() {
    return this.crossoverRate;
  }

  public Double getMutationRate() {
    return this.mutationRate;
  }

  /**
   * Status: RUNNING, COMPLETED, FAILED
   */
  public String getStatus() {
    return this.status;
  }

  public String getNotes() {
    return this.notes;
  }

  public User getTriggeredBy() {
    return this.triggeredBy;
  }

  public LocalDateTime getStartedAt() {
    return this.startedAt;
  }

  public LocalDateTime getCompletedAt() {
    return this.completedAt;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public void setGenerations(final Integer generations) {
    this.generations = generations;
  }

  public void setPopulationSize(final Integer populationSize) {
    this.populationSize = populationSize;
  }

  public void setBestFitnessScore(final Double bestFitnessScore) {
    this.bestFitnessScore = bestFitnessScore;
  }

  public void setCrossoverRate(final Double crossoverRate) {
    this.crossoverRate = crossoverRate;
  }

  public void setMutationRate(final Double mutationRate) {
    this.mutationRate = mutationRate;
  }

  /**
   * Status: RUNNING, COMPLETED, FAILED
   */
  public void setStatus(final String status) {
    this.status = status;
  }

  public void setScheduleApplied(final boolean scheduleApplied) {
    this.scheduleApplied = scheduleApplied;
  }

  public void setNotes(final String notes) {
    this.notes = notes;
  }

  public void setTriggeredBy(final User triggeredBy) {
    this.triggeredBy = triggeredBy;
  }

  public void setStartedAt(final LocalDateTime startedAt) {
    this.startedAt = startedAt;
  }

  public void setCompletedAt(final LocalDateTime completedAt) {
    this.completedAt = completedAt;
  }

  public AlgorithmRun() {
    this.status = AlgorithmRun.$default$status();
    this.scheduleApplied = AlgorithmRun.$default$scheduleApplied();
  }

  /**
   * Creates a new {@code AlgorithmRun} instance.
   *
   * @param id
   * @param generations
   * @param populationSize
   * @param bestFitnessScore
   * @param crossoverRate
   * @param mutationRate
   * @param status Status: RUNNING, COMPLETED, FAILED
   * @param scheduleApplied
   * @param notes
   * @param triggeredBy
   * @param startedAt
   * @param completedAt
   */
  public AlgorithmRun(final Long id, final Integer generations, final Integer populationSize, final Double bestFitnessScore, final Double crossoverRate, final Double mutationRate, final String status, final boolean scheduleApplied, final String notes, final User triggeredBy, final LocalDateTime startedAt, final LocalDateTime completedAt) {
    this.id = id;
    this.generations = generations;
    this.populationSize = populationSize;
    this.bestFitnessScore = bestFitnessScore;
    this.crossoverRate = crossoverRate;
    this.mutationRate = mutationRate;
    this.status = status;
    this.scheduleApplied = scheduleApplied;
    this.notes = notes;
    this.triggeredBy = triggeredBy;
    this.startedAt = startedAt;
    this.completedAt = completedAt;
  }
}
