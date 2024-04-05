package org.bigcompany.util;

import org.bigcompany.model.Employee;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Map;


public class CsvReaderTest {

    @Test
    public void expectExceptionIfFileNotFound() {
        CsvReader csvReader = new CsvReader();
        Assert.assertThrows(FileNotFoundException.class, () -> {
            csvReader.readCsv("invalidFile.csv");
        });

    }

    @Test
    public void expectCorrectResultWhenFileExists() throws FileNotFoundException {
        CsvReader csvReader = new CsvReader();
        Map<Long, Employee> result = csvReader.readCsv("Employees.csv");

        Assert.assertEquals(5, result.size());
    }
}