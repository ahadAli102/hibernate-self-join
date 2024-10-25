package org.ahad.hibernate.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    // ManyToOne relationship to define the supervisor
    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;

    // OneToMany relationship to define the subordinates
    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> subordinates = new HashSet<>();

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public Set<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    // Add a subordinate
    public void addSubordinate(Employee subordinate) {
        subordinates.add(subordinate);
        subordinate.setSupervisor(this);
    }

    // Remove a subordinate
    public void removeSubordinate(Employee subordinate) {
        subordinates.remove(subordinate);
        subordinate.setSupervisor(null);
    }
}