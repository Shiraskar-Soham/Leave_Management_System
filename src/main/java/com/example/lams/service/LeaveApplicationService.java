package com.example.lams.service;

import com.example.lams.Repository.LeaveApplicationIndexRepository;
import com.example.lams.Repository.LeaveApplicationRepository;
import com.example.lams.converter.EsToMysql;
import com.example.lams.domain.Employee;
import com.example.lams.domain.LeaveApplication;
import com.example.lams.domain.LeaveApplicationIndex;
import com.example.lams.dtos.LeaveApplicationUpdateDto;
import com.example.lams.enums.LeaveStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class LeaveApplicationService {

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LeaveApplicationIndexRepository leaveApplicationIndexRepository;
    @Autowired
    private EsToMysql convertToES;


    public boolean createLeaveApplication(LeaveApplication leaveApplication) throws Exception {
        if (ObjectUtils.isEmpty(leaveApplication)) {
            throw new Exception("Leave Application cannot be empty");
        }
        Employee e = employeeService.getEmployeeByEmpId(leaveApplication.getEmpId());
        leaveApplication.setManagerId(e.getManagerId());
        leaveApplication.setEmpName(e.getEmpName());
        leaveApplication.setDateCreated(System.currentTimeMillis());
        leaveApplication.setDateModified(System.currentTimeMillis());
        leaveApplication.setStatus(LeaveStatus.PENDING);
        leaveApplication.setIsDeleted(false);
        leaveApplicationRepository.save(leaveApplication);
        leaveApplicationIndexRepository.save(convertToES.converterIndex(leaveApplication));
        return true;
    }

    public List<LeaveApplication> getLeavesOfAnEmployee(String empId) throws Exception {
        if (ObjectUtils.isEmpty(empId)) {
            throw new Exception("empId cannot be empty");
        }
        return leaveApplicationRepository.findByEmpId(empId);
    }

    public LeaveApplication getLeaveApplication(String leaveId) throws Exception {
        if (ObjectUtils.isEmpty(leaveId)) {
            throw new Exception("leaveId cannot be empty");
        }
        return leaveApplicationRepository.findByLeaveId(leaveId);
    }

    public void deleteLeave(String leaveId) throws Exception {
        if (ObjectUtils.isEmpty(leaveId)) {
            throw new Exception("leaveId cannot be empty");
        }
        LeaveApplication l = leaveApplicationRepository.findByLeaveId(leaveId);
        if (ObjectUtils.isEmpty(leaveId)) {
            throw new Exception("No leave found for leaveId = " + leaveId);
        }
        l.setIsDeleted(true);
        l.setDateModified(System.currentTimeMillis());
        leaveApplicationRepository.save(l);
    }

    public LeaveApplicationUpdateDto updateLeaveApplication(LeaveApplicationUpdateDto leaveApplicationUpdateDto) throws Exception {
        if (ObjectUtils.isEmpty(leaveApplicationUpdateDto)) {
            throw new Exception("leaveApplicationDto cannot be empty");
        }
        LeaveApplication l = leaveApplicationRepository.findByLeaveId(leaveApplicationUpdateDto.getLeaveId());
        if (ObjectUtils.isEmpty(leaveApplicationUpdateDto)) {
            throw new Exception("No leave found for the leaveApplicationDto");
        }
        if (l.getStatus() != LeaveStatus.PENDING) {
            throw new Exception("Leave is already " + l.getStatus() + ".");
        }
        l.setDateModified(System.currentTimeMillis());
        l.setReason(leaveApplicationUpdateDto.getReason());
        l.setStartDate(leaveApplicationUpdateDto.getStartDate());
        l.setEndDate(leaveApplicationUpdateDto.getEndDate());
        leaveApplicationRepository.save(l);
        return leaveApplicationUpdateDto;
    }

    public LeaveApplication approvalAndRejectionByManager(String leaveId, LeaveStatus status, String loggedInAccountId) throws Exception {
        if (leaveId == null) {
            throw new Exception("Leave ID cannot be blank");
        }
        LeaveApplication leaveApplication = leaveApplicationRepository.findByLeaveId(leaveId);
        if (ObjectUtils.isEmpty(leaveApplication)) {
            throw new Exception("Leave not found for leave Id: " + leaveId);
        }
        if (!Objects.equals(loggedInAccountId, leaveApplication.getManagerId())) {
            throw new Exception("Leave " + leaveId + " can only be approved by employee's manager with manager empCode: " + leaveApplication.getManagerId());
        }
        if (leaveApplication.getStatus() != LeaveStatus.PENDING) {
            throw new Exception("Leave is already marked approved or rejected");
        }
        leaveApplication.setStatus(status);
        leaveApplication.setDateModified(System.currentTimeMillis());
        return leaveApplicationRepository.save(leaveApplication);
    }

    public List<LeaveApplication> getByManagerId(String managerId) throws Exception {
        if (ObjectUtils.isEmpty(managerId)) {
            throw new Exception("Manager Id cannot be null.");
        }
        return leaveApplicationRepository.findByManagerId(managerId);
    }

    public List<LeaveApplicationIndex> getAllLeaves() {
        return leaveApplicationIndexRepository.getAllLeaves();
    }
}