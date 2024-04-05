package org.bigcompany.util;

import org.bigcompany.model.Employee;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CsvReader {

    /**
     * This method returns a Map of employee id and employee details
     *
     * @param filePath  the file path
     * @return          the map of employees
     */
    public Map<Long, Employee> readCsv(String filePath) throws FileNotFoundException {
        Map<Long, Employee> employeesMap = new HashMap<>();

        InputStream inputStream = CsvReader.class.getClassLoader().getResourceAsStream(filePath);

        if (inputStream == null) {
            System.out.println("File " + filePath + " does not exist.");
            throw new FileNotFoundException(filePath);
        }

        try (Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                Long id = Long.parseLong(parts[0]);
                String firstName = parts[1];
                String lastName = parts[2];
                Long salary = Long.parseLong(parts[3]);
                Long managerId = null;
                if (parts.length > 4) {
                    managerId = Long.parseLong(parts[4]);
                }

                Employee employee = new Employee(id, firstName, lastName, salary, managerId);
                employeesMap.put(id, employee);
            }
        }

        return employeesMap;
    }
}
