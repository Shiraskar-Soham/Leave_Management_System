package com.example.lams.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee extends BasicDetails{
    @Id
    @Column(name="empId")
    private String empId;
    @Column(name="empName")
    private String empName;
    @Column(name="emailId")
    private String emailId;
    @Column(name="managerId")
    private String managerId;

    public Employee() {
    }

    public Employee(Long dateCreated, String creatorId, Long dateModified, String modifierId, Boolean isDeleted, String empId, String empName, String emailId, String managerId) {
        super(dateCreated, creatorId, dateModified, modifierId, isDeleted);
        this.empId = empId;
        this.empName = empName;
        this.emailId = emailId;
        this.managerId = managerId;
    }

    public Employee(String empId, String empName, String emailId, String managerId) {
        this.empId = empId;
        this.empName = empName;
        this.emailId = emailId;
        this.managerId = managerId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public static interface DateCreatedStep {
        CreatorIdStep withDateCreated(Long dateCreated);
    }

    public static interface CreatorIdStep {
        DateModifiedStep withCreatorId(String creatorId);
    }

    public static interface DateModifiedStep {
        ModifierIdStep withDateModified(Long dateModified);
    }

    public static interface ModifierIdStep {
        IsDeletedStep withModifierId(String modifierId);
    }

    public static interface IsDeletedStep {
        EmpIdStep withIsDeleted(Boolean isDeleted);
    }

    public static interface EmpIdStep {
        EmpNameStep withEmpId(String empId);
    }

    public static interface EmpNameStep {
        EmailIdStep withEmpName(String empName);
    }

    public static interface EmailIdStep {
        ManagerIdStep withEmailId(String emailId);
    }

    public static interface ManagerIdStep {
        BuildStep withManagerId(String managerId);
    }

    public static interface BuildStep {
        Employee build();
    }


    public static class Builder implements DateCreatedStep, CreatorIdStep, DateModifiedStep, ModifierIdStep, IsDeletedStep, EmpIdStep, EmpNameStep, EmailIdStep, ManagerIdStep, BuildStep {
        private Long dateCreated;
        private String creatorId;
        private Long dateModified;
        private String modifierId;
        private Boolean isDeleted;
        private String empId;
        private String empName;
        private String emailId;
        private String managerId;

        private Builder() {
        }

        public static DateCreatedStep employee() {
            return new Builder();
        }

        @Override
        public CreatorIdStep withDateCreated(Long dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        @Override
        public DateModifiedStep withCreatorId(String creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        @Override
        public ModifierIdStep withDateModified(Long dateModified) {
            this.dateModified = dateModified;
            return this;
        }

        @Override
        public IsDeletedStep withModifierId(String modifierId) {
            this.modifierId = modifierId;
            return this;
        }

        @Override
        public EmpIdStep withIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        @Override
        public EmpNameStep withEmpId(String empId) {
            this.empId = empId;
            return this;
        }

        @Override
        public EmailIdStep withEmpName(String empName) {
            this.empName = empName;
            return this;
        }

        @Override
        public ManagerIdStep withEmailId(String emailId) {
            this.emailId = emailId;
            return this;
        }

        @Override
        public BuildStep withManagerId(String managerId) {
            this.managerId = managerId;
            return this;
        }

        @Override
        public Employee build() {
            return new Employee(
                    this.dateCreated,
                    this.creatorId,
                    this.dateModified,
                    this.modifierId,
                    this.isDeleted,
                    this.empId,
                    this.empName,
                    this.emailId,
                    this.managerId
            );
        }
    }
}
