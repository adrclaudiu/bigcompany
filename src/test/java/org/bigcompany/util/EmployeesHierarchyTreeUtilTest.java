package org.bigcompany.util;

import org.bigcompany.model.Employee;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeesHierarchyTreeUtilTest {

    @Test
    public void expectCorrectBuildEmployeesHierarchy() {
        EmployeesHierarchyTreeUtil employeesHierarchyTreeUtil = new EmployeesHierarchyTreeUtil();

        Employee ceo = new Employee(1L, "ceo", "ceo", 60000L, null);
        Employee manager = new Employee(2L, "manager", "manager", 50000L, 1L);
        Employee employee1 = new Employee(3L, "employee1", "employee1", 60000L, 2L);
        Employee employee2 = new Employee(4L, "employee2", "employee2", 60000L, 2L);
        Map<Long, Employee> employeesMap = new HashMap<>();
        employeesMap.put(1L, ceo);
        employeesMap.put(2L, manager);
        employeesMap.put(3L, employee1);
        employeesMap.put(4L, employee2);
        employeesHierarchyTreeUtil.buildEmployeesHierarchy(ceo, employeesMap);

        Assert.assertEquals(1, ceo.getSubordinates().size());
        Assert.assertEquals(2, ceo.getSubordinates().get(0).getSubordinates().size());
    }

    @Test
    public void expectCorrectGetManagerSubordinates() {
        EmployeesHierarchyTreeUtil employeesHierarchyTreeUtil = new EmployeesHierarchyTreeUtil();

        Employee ceo = new Employee(1L, "ceo", "ceo", 60000L, null);
        Employee manager = new Employee(2L, "manager", "manager", 50000L, 1L);
        Employee employee1 = new Employee(3L, "employee1", "employee1", 60000L, 2L);
        Employee employee2 = new Employee(4L, "employee2", "employee2", 60000L, 2L);
        Map<Long, Employee> employeesMap = new HashMap<>();
        employeesMap.put(1L, ceo);
        employeesMap.put(2L, manager);
        employeesMap.put(3L, employee1);
        employeesMap.put(4L, employee2);

        List<Employee> managerSubordinates = employeesHierarchyTreeUtil.getManagerSubordinates(2L, employeesMap);

        Assert.assertEquals(2, managerSubordinates.size());
    }

    @Test
    public void expectCorrectFindManagersWithLowSalary() {
        EmployeesHierarchyTreeUtil employeesHierarchyTreeUtil = new EmployeesHierarchyTreeUtil();

        Employee ceo = new Employee(1L, "ceo", "ceo", 60000L, null);
        Employee manager = new Employee(2L, "manager", "manager", 50000L, 1L);
        Employee employee1 = new Employee(3L, "employee1", "employee1", 60000L, 2L);
        Employee employee2 = new Employee(4L, "employee2", "employee2", 60000L, 2L);
        Map<Long, Employee> employeesMap = new HashMap<>();
        employeesMap.put(1L, ceo);
        employeesMap.put(2L, manager);
        employeesMap.put(3L, employee1);
        employeesMap.put(4L, employee2);

        Map<Long, Long> managersWithLowSalary = employeesHierarchyTreeUtil.findManagersWithLowSalary(employeesMap);

        Assert.assertEquals(1, managersWithLowSalary.size());
        Assert.assertEquals(22000L, (long) managersWithLowSalary.get(2L));
    }

    @Test
    public void expectCorrectFindManagersWithHighSalary() {
        EmployeesHierarchyTreeUtil employeesHierarchyTreeUtil = new EmployeesHierarchyTreeUtil();

        Employee ceo = new Employee(1L, "ceo", "ceo", 60000L, null);
        Employee manager = new Employee(2L, "manager", "manager", 91000L, 1L);
        Employee employee1 = new Employee(3L, "employee1", "employee1", 60000L, 2L);
        Employee employee2 = new Employee(4L, "employee2", "employee2", 60000L, 2L);
        Map<Long, Employee> employeesMap = new HashMap<>();
        employeesMap.put(1L, ceo);
        employeesMap.put(2L, manager);
        employeesMap.put(3L, employee1);
        employeesMap.put(4L, employee2);

        Map<Long, Long> managersWithHighSalary = employeesHierarchyTreeUtil.findManagersWithHighSalary(employeesMap);

        Assert.assertEquals(1, managersWithHighSalary.size());
        Assert.assertEquals(1000L, (long) managersWithHighSalary.get(2L));
    }

    @Test
    public void expectCorrectFindEmployeesWithLongReportingLine() {
        EmployeesHierarchyTreeUtil employeesHierarchyTreeUtil = new EmployeesHierarchyTreeUtil();

        Employee ceo = new Employee(1L, "ceo", "ceo", 60000L, null);
        Employee manager1 = new Employee(2L, "manager1", "manager1", 91000L, 1L);
        Employee manager2 = new Employee(3L, "manager2", "manager2", 60000L, 2L);
        Employee manager3 = new Employee(4L, "manager3", "manager3", 60000L, 3L);
        Employee manager4 = new Employee(5L, "manager4", "manager4", 60000L, 4L);
        Employee manager5 = new Employee(6L, "manager5", "manager5", 60000L, 5L);
        Employee employee = new Employee(7L, "employee", "employee", 60000L, 6L);
        Map<Long, Employee> employeesMap = new HashMap<>();
        employeesMap.put(1L, ceo);
        employeesMap.put(2L, manager1);
        employeesMap.put(3L, manager2);
        employeesMap.put(4L, manager3);
        employeesMap.put(5L, manager4);
        employeesMap.put(6L, manager5);
        employeesMap.put(7L, employee);
        employeesHierarchyTreeUtil.buildEmployeesHierarchy(ceo, employeesMap);

        Map<Long, Integer> employeesWithLongReportingLine = employeesHierarchyTreeUtil.findEmployeesWithLongReportingLine(ceo, employeesMap);

        Assert.assertEquals(1, employeesWithLongReportingLine.size());
    }
}