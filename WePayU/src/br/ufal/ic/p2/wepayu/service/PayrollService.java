package br.ufal.ic.p2.wepayu.service;

import br.ufal.ic.p2.wepayu.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//# 1 - empregados horistas sao contratados no primeiro dia em que lancarem um cartao (nao necessariamente 1/1/2005)
//# 2 - empregados assalariados (e comissionados) sao contratados sempre no dia 1/1/2005

public class PayrollService {

    public static String totalPayroll(String date, List<Employee> employeesList) throws Exception {
        LocalDate payDay = DateService.stringToLocalDate(date);
        List<HourlyPaycheck> hourlyPaychecks = new ArrayList<>();
        List<SalariedPaycheck> salariedPaychecks = new ArrayList<>();
        List<CommissionedPaycheck> commissionedPaychecks = new ArrayList<>();

        double totalPayroll = 0;

        for (Employee employee : employeesList) {
            if (employee.isPayday(payDay)) {
                Paycheck newPaycheck = employee.simulatePaycheck(payDay);
                totalPayroll += newPaycheck.getGross();

                switch (newPaycheck) { //verifica qual o tipo de contracheque foi gerado pra adicionar na lista correta
                    case HourlyPaycheck hourlyPaycheck -> hourlyPaychecks.add(hourlyPaycheck);
                    case SalariedPaycheck salariedPaycheck -> salariedPaychecks.add(salariedPaycheck);
                    case CommissionedPaycheck commissionedPaycheck -> commissionedPaychecks.add(commissionedPaycheck);
                    default -> throw new Exception("Invalid Paycheck"); //esse erro não está nos testes
                }
            }
        }
        hourlyPaychecks.clear();
        salariedPaychecks.clear();
        commissionedPaychecks.clear();
        return NumberService.formatAsCurrency(totalPayroll);
    }

    public static void runPayroll(String date, String outputFileName, List<Employee> employeesList) throws Exception {

        LocalDate payDay = DateService.stringToLocalDate(date);
        List<HourlyPaycheck> hourlyPaychecks = new ArrayList<>();
        List<SalariedPaycheck> salariedPaychecks = new ArrayList<>();
        List<CommissionedPaycheck> commissionedPaychecks = new ArrayList<>();

        double totalPayroll = 0;

        for(Employee employee : employeesList) {
            if(employee.isPayday(payDay)) {
                Paycheck newPaycheck = employee.calculatePaycheck(payDay);
                totalPayroll += newPaycheck.getGross();

                switch (newPaycheck) { //verifica qual o tipo de contracheque foi gerado pra adicionar na lista correta
                    case HourlyPaycheck hourlyPaycheck -> hourlyPaychecks.add(hourlyPaycheck);
                    case SalariedPaycheck salariedPaycheck -> salariedPaychecks.add(salariedPaycheck);
                    case CommissionedPaycheck commissionedPaycheck -> commissionedPaychecks.add(commissionedPaycheck);
                    default -> throw new Exception("Invalid Paycheck"); //esse erro não está nos testes
                }
            }
        }
        hourlyPaychecks.sort(Comparator.comparing(HourlyPaycheck::getName));
        salariedPaychecks.sort(Comparator.comparing(SalariedPaycheck::getName));
        commissionedPaychecks.sort(Comparator.comparing(CommissionedPaycheck::getName));
        FileService.generatePayrollFile(outputFileName, hourlyPaychecks, salariedPaychecks, commissionedPaychecks, payDay, totalPayroll);
        hourlyPaychecks.clear();
        salariedPaychecks.clear();
        commissionedPaychecks.clear();
    }
}
