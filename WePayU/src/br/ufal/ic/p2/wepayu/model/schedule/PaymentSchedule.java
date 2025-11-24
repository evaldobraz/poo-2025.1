package br.ufal.ic.p2.wepayu.model.schedule;

import br.ufal.ic.p2.wepayu.model.Employee;

import java.time.LocalDate;

public interface PaymentSchedule {
    boolean isPayDate(LocalDate date, Employee employee);
    String toString(); // Para retornar a string original ("mensal $", etc)
}
