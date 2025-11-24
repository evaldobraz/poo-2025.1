package br.ufal.ic.p2.wepayu.model.schedule;

import br.ufal.ic.p2.wepayu.model.Employee;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;

public class WeeklySchedule implements PaymentSchedule {
    private int interval; // 1 = toda semana, 2 = a cada 2 semanas
    private int dayOfWeek; // 1 = Seg, 5 = Sex, 7 = Dom (Confira o padrão do Java Time)

    public WeeklySchedule() {}

    public WeeklySchedule(int interval, int dayOfWeek) {
        this.interval = interval;
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public boolean isPayDate(LocalDate date, Employee employee) {
        if (date.getDayOfWeek().getValue() != dayOfWeek) {
            return false;
        }

        if (interval == 1) {
            return true;
        }

        int weekOfYear = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

        if (weekOfYear % interval == 0) {
            return true;
        }

        LocalDate lastPayDay = employee.getLastPayDay();
        if (lastPayDay != null) {
            long weeks = ChronoUnit.WEEKS.between(lastPayDay, date);
            return weeks == interval;
        }

        return false;
    }

    @Override
    public String toString() {
        if (interval == 1) {
            return "semanal " + dayOfWeek;
        }
        return "semanal " + interval + " " + dayOfWeek;
    }

    public int getInterval() { return interval; }
    public void setInterval(int interval) { this.interval = interval; }

    public int getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(int dayOfWeek) { this.dayOfWeek = dayOfWeek; }
}