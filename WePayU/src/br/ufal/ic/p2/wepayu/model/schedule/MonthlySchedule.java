package br.ufal.ic.p2.wepayu.model.schedule;

import br.ufal.ic.p2.wepayu.model.Employee;

import java.io.Serializable;
import java.time.LocalDate;

public class MonthlySchedule implements PaymentSchedule {
    private String day; // Pode ser "1", "15" ou "$"

    public MonthlySchedule() {}

    public MonthlySchedule(String day) {
        this.day = day;
    }

    @Override
    public boolean isPayDate(LocalDate date, Employee employee) {
        if (day.equals("$")) {
            // Verifica se é o último dia do mês
            return date.lengthOfMonth() == date.getDayOfMonth();
        } else {
            // Verifica se o dia bate (ex: dia 5)
            int targetDay = Integer.parseInt(day);
            return date.getDayOfMonth() == targetDay;
        }
    }

    @Override
    public String toString() {
        return "mensal " + this.day;
    }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }
}
