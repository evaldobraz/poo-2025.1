package br.ufal.ic.p2.wepayu.service;

import br.ufal.ic.p2.wepayu.model.Employee;

import java.util.List;

public class ResetSystemService {
    public static void reset(List<Employee> employeesList)
    {
        if (!employeesList.isEmpty()) {
            employeesList.clear();
            Employee.resetNextId();
        }
    }
}
