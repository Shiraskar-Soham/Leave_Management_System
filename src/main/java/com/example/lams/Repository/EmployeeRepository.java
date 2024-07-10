package com.example.lams.Repository;

import com.example.lams.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    @Query(value= "SELECT * FROM employee WHERE emp_Name LIKE %?1%", nativeQuery = true)
    List<Employee> findByNameLike(String empName);

    Employee findByEmpId(String empID);
}
