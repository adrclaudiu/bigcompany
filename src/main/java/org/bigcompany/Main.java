package org.bigcompany;

import org.bigcompany.exception.CEONotFoundException;
import org.bigcompany.model.Employee;
import org.bigcompany.util.CsvReader;
import org.bigcompany.util.EmployeesHierarchyTreeUtil;

import java.util.Map;
import java.util.Optional;

public class Main {

    public static final String INPUT_EMPLOYEES_CSV = "input/Employees.csv";

    public static void main(String[] args) throws Exception {

        // map of each employee id and the details
        Map<Long, Employee> employeesMap = new CsvReader().readCsv(INPUT_EMPLOYEES_CSV);

        // start building hierarchy from CEO
        EmployeesHierarchyTreeUtil employeesHierarchyTreeUtil = new EmployeesHierarchyTreeUtil();
        Optional<Employee> employeesHierarchy = employeesMap.values().stream().filter(employee -> employee.getManagerId() == null).findFirst();
        if (employeesHierarchy.isPresent()) {
            employeesHierarchyTreeUtil.buildEmployeesHierarchy(employeesHierarchy.get(), employeesMap);
        } else {
            System.out.println("CEO not found. Stopping execution");
            throw new CEONotFoundException("CEO not found");
        }

        // find managers who earn less than they should (less than 20% of the average salary of subordinates)
        Map<Long, Long> managersWithLowSalaries = employeesHierarchyTreeUtil.findManagersWithLowSalary(employeesMap);

        // find managers who earn more than they should (more than 50% of the average salary of subordinates)
        Map<Long, Long> managersWithHighSalaries = employeesHierarchyTreeUtil.findManagersWithHighSalary(employeesMap);

        // find employees with too long reporting line
        Map<Long, Integer> employeesWithLongReportingLine = employeesHierarchyTreeUtil.findEmployeesWithLongReportingLine(employeesHierarchy.get(), employeesMap);

        // print results
        for (Map.Entry<Long, Long> managerWithLowSalary : managersWithLowSalaries.entrySet()) {
            System.out.println("Manager " + managerWithLowSalary.getKey() + " has a low salary. It's expected to be " + managerWithLowSalary.getValue() + " higher.");
        }

        for (Map.Entry<Long, Long> managerWithHighSalaries : managersWithHighSalaries.entrySet()) {
            System.out.println("Manager " + managerWithHighSalaries.getKey() + " has a high salary. It's expected to be " + managerWithHighSalaries.getValue() + " lower.");
        }

        for (Map.Entry<Long, Integer> employeeWithLongReportingLine : employeesWithLongReportingLine.entrySet()) {
            System.out.println("Employee " + employeeWithLongReportingLine.getKey() + " has a long reporting line. It's currently at " + employeeWithLongReportingLine.getValue() + ".");
        }
    }
}

