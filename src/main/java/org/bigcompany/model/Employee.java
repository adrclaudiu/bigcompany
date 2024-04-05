package org.bigcompany.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class Employee {

    private final Long id;

    private final String firstName;

    private final String lastName;

    private final Long salary;

    private final Long managerId;

    private List<Employee> subordinates;
}
