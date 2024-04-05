package org.bigcompany.util;

import org.bigcompany.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeesHierarchyTreeUtil {

    /**
     * Build Hierarchy, by populating the subordinates for each employee
     *
     * @param manager       the current manager used to populate the subordinates
     * @param employeesMap  the map with all employees
     */
    public void buildEmployeesHierarchy(Employee manager, Map<Long, Employee> employeesMap) {
        List<Employee> managerSubordinates = getManagerSubordinates(manager.getId(), employeesMap);
        manager.setSubordinates(managerSubordinates);
        // recursively populate subordinates for each manager
        for (Employee employee : managerSubordinates) {
            buildEmployeesHierarchy(employee, employeesMap);
        }
    }

    /**
     * Return all employees which have the given managerId
     *
     * @param managerId     the manager id
     * @param employeesMap  the map with all employees
     * @return              the list of employees associated with manager
     */
    public List<Employee> getManagerSubordinates(Long managerId, Map<Long, Employee> employeesMap) {
        List<Employee> managerSubordinates = new ArrayList<>();
        for (Map.Entry<Long, Employee> mapEntry : employeesMap.entrySet()) {
            if (mapEntry.getValue().getManagerId() != null && mapEntry.getValue().getManagerId().equals(managerId)) {
                managerSubordinates.add(mapEntry.getValue());
            }
        }
        return managerSubordinates;
    }

    /**
     * Return a map with each managerId and the amount less than subordinates average + 20%
     *
     * @param employeesMap  the map with all employees
     * @return              a map with managerId and salary difference
     */
    public Map<Long, Long> findManagersWithLowSalary(Map<Long, Employee> employeesMap) {
        Map<Long, Long> managerSalaryDifference = new HashMap<>();
        for (Map.Entry<Long, Employee> employeeEntry : employeesMap.entrySet()) {
            List<Employee> subordinates = getManagerSubordinates(employeeEntry.getKey(), employeesMap);
            // can use also CollectionUtils.isEmpty()
            if (subordinates != null && subordinates.size() > 0) {
                Double averageSalary = subordinates.stream().collect(Collectors.averagingLong(Employee::getSalary));
                Long minimumManagerSalary = Long.valueOf((long) (averageSalary * 1.2));
                if (employeeEntry.getValue().getSalary().compareTo(minimumManagerSalary) < 0) {
                    managerSalaryDifference.put(employeeEntry.getKey(), minimumManagerSalary - employeeEntry.getValue().getSalary());
                }
            }
        }
        return managerSalaryDifference;
    }

    /**
     * Return a map with each managerId and the amount greater than subordinates average + 50%
     *
     * @param employeesMap  the map with all employees
     * @return              a map with managerId and salary difference
     */
    public Map<Long, Long> findManagersWithHighSalary(Map<Long, Employee> employeesMap) {
        Map<Long, Long> managerSalaryDifference = new HashMap<>();
        for (Map.Entry<Long, Employee> employeeEntry : employeesMap.entrySet()) {
            List<Employee> subordinates = getManagerSubordinates(employeeEntry.getKey(), employeesMap);
            // can use also CollectionUtils.isEmpty()
            if (subordinates != null && subordinates.size() > 0) {
                Double averageSalary = subordinates.stream().collect(Collectors.averagingLong(Employee::getSalary));
                Long maximumManagerSalary = Long.valueOf((long) (averageSalary * 1.5));
                if (employeeEntry.getValue().getSalary().compareTo(maximumManagerSalary) > 0) {
                    managerSalaryDifference.put(employeeEntry.getKey(), employeeEntry.getValue().getSalary() - maximumManagerSalary);
                }
            }
        }
        return managerSalaryDifference;
    }

    /**
     * Return a map with each employee and the number of reporting lines
     *
     * @param employeesHierarchy    the employees hierarchy
     * @param employeesMap          the map with all employees
     * @return                      a map with managerId and salary difference
     */
    public Map<Long, Integer> findEmployeesWithLongReportingLine(Employee employeesHierarchy, Map<Long, Employee> employeesMap) {
        Map<Long, Integer> employeesWithLongReportingLine = new HashMap<>();

        // for each employee, find the depth in the hierarchy
        // starting with depth -1 because we count only the managers between employee and CEO, without taking in consideration CEO
        for (Map.Entry<Long, Employee> employeeEntry : employeesMap.entrySet()) {
            Integer reportingLineDepth = findEmployeeReportingLineDepth(employeesHierarchy, employeeEntry.getKey(), -1);
            if (reportingLineDepth > 4) {
                employeesWithLongReportingLine.put(employeeEntry.getKey(), reportingLineDepth);
            }
        }

        return employeesWithLongReportingLine;
    }

    /**
     *  Find the depth of an employee in the employee hierarchy
     *
     * @param currentEmployee       the current employee that we are at
     * @param searchedEmployeeId    the employee id to be found
     * @param depth                 the reporting line
     *
     * @return                      the reporting line depth
     */
    private int findEmployeeReportingLineDepth(Employee currentEmployee, Long searchedEmployeeId, int depth) {
        if (currentEmployee == null) {
            return -1;
        }
        if (currentEmployee.getId().equals(searchedEmployeeId)) {
            return depth;
        }
        for (Employee subordinate : currentEmployee.getSubordinates()) {
            int childDepth = findEmployeeReportingLineDepth(subordinate, searchedEmployeeId, depth + 1);
            if (childDepth != -1) {
                return childDepth;
            }
        }
        return -1;
    }
}
