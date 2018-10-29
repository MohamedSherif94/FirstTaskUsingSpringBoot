package com.example.mohamedsherif.firsttaskusingspringboot;

public class Employee {

    private Long id;
    private String name;
    private String job_id;

    public Employee() {
    }

    public Employee(Long id, String name, String job_id) {
        this.id = id;
        this.name = name;
        this.job_id = job_id;
    }

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

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }
}
