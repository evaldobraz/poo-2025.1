package br.ufal.ic.p2.wepayu.service;

import br.ufal.ic.p2.wepayu.model.*;

import java.time.LocalDate;
import java.util.List;

public class CommissionedService {
    public static void submitSale(List<Employee> employeeList, String employeeId, String date, String value) throws Exception{

        LocalDate saleDate = DateService.stringToLocalDate(date);

        Employee employee = EmployeeService.getEmployeeById(employeeList,employeeId);

        if(!(employee instanceof Commissioned commissionedEmployee)){
            throw new Exception("Empregado nao eh comissionado.");
        }

        double saleValue = NumberService.formatAsDouble(value);
        double commissionValue = commissionedEmployee.getCommission() * saleValue;

        Sale newSale = new Sale(saleDate, saleValue, commissionValue);

        commissionedEmployee.addSale(newSale);
    }

    public static String getSalesValue(List<Employee> employeeList, String employeeId, String startDate, String endDate) throws Exception {

        return NumberService.formatAsCurrency(CalculatorService.calculateValue(employeeList, employeeId, startDate, endDate, Commissioned.class, "salesValue"));

    }

}
