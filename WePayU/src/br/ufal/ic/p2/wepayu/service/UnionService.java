package br.ufal.ic.p2.wepayu.service;

import br.ufal.ic.p2.wepayu.model.Employee;

import java.time.LocalDate;
import java.util.List;

public class UnionService {

    public static boolean unionIdAlreadyExists(String unionId, List<Employee> employeeList) {

        for (Object o : employeeList) {
            if (o instanceof Employee employee) {
                if (employee.getUnionId() != null && !employee.getUnionId().equalsIgnoreCase("") && employee.getUnionId().equals(unionId) ) {
                    return true;
                }
            }
        }

        return false;
    }

    public static double getFees(List<Employee> employeeList, String employeeId, String startDate, String endDate) throws Exception {

        Employee foundEmployee = EmployeeService.getEmployeeById(employeeList, employeeId);

        if (!foundEmployee.isUnion()) {
            throw new Exception("Empregado nao eh sindicalizado.");
        }

        return CalculatorService.calculateValue(employeeList, employeeId, startDate, endDate, Employee.class, "fee");
    }

    public static void submitFee(List<Employee> employeeList, String employeeUnionId, String date, String value) throws Exception {

        Employee foundEmployee = EmployeeService.getEmployeeByUnionId(employeeList, employeeUnionId);

        foundEmployee.addFee(date, value);
    }

    public static void clearPaidFees(Employee employee ) {
        employee.getFees().clear();
    }
}
