package br.ufal.ic.p2.wepayu.services;

import br.ufal.ic.p2.wepayu.models.Employee;

import java.util.List;

public class ResetSystemService {
    public static void reset(List<Employee> employee)
    {
        if (!employee.isEmpty()) employee.clear();
    }
}
