package br.ufal.ic.p2.wepayu.service;

import br.ufal.ic.p2.wepayu.exception.WrongEmployeeTypeException;
import br.ufal.ic.p2.wepayu.model.Employee;
import br.ufal.ic.p2.wepayu.model.Hourly;

import java.util.List;

public class HourlyService {

    public static void submitTimesheet(List<Employee> employeeList, String employeeId, String date, double workedHours) throws Exception {

        Employee employee = EmployeeService.getEmployeeById(employeeList, employeeId);

        if(!(employee instanceof Hourly hourlyEmployee)) {
            throw new WrongEmployeeTypeException("Empregado nao eh horista.");
        }

        if(hourlyEmployee.getTimesheets().isEmpty()) {
            hourlyEmployee.setContractDate(DateService.stringToLocalDate(date));
            hourlyEmployee.setLastPayDay(DateService.stringToLocalDate(date).minusDays(1));
        }

        hourlyEmployee.addTimesheet(DateService.stringToLocalDate(date), workedHours);
    }


    public static int getWorkedNormalHours(List<Employee> employeeList, String employeeId, String startDate, String endDate) throws RuntimeException, Exception{

        return (int)CalculatorService.calculateValue(employeeList, employeeId, startDate, endDate, Hourly.class, "normalHours");

    }


    public static String getWorkedExtraHours(List<Employee> employeeList, String employeeId, String startDate, String endDate) throws RuntimeException, Exception {

        return NumberService.formatWithComma(CalculatorService.calculateValue(employeeList, employeeId, startDate, endDate, Hourly.class, "extraHours"));

    }


}