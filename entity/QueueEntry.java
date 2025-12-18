package rw.auca.muyoboke.smarthealthclinic.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "queue_entries")
public class QueueEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false)
    private int queueNumber;

    @Column(nullable = false)
    private String status; // WAITING, SKIPPED, CALLED, COMPLETED

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean emergency;

    public QueueEntry() {
        this.createdAt = LocalDateTime.now();
        this.status = "WAITING";
    }

    // Getters and setters...
}
