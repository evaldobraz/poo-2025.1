package br.ufal.ic.p2.wepayu.service;

import br.ufal.ic.p2.wepayu.model.Hourly;
import br.ufal.ic.p2.wepayu.model.Timesheet;

import java.time.LocalDate;

public class TimesheetService {

    public static boolean hasWorkedInPeriod(Hourly hourly, LocalDate lastPayDay, LocalDate payDay) {

        LocalDate periodStartDate = lastPayDay.plusDays(1);

        for (Timesheet timesheet : hourly.getTimesheets()) {
            LocalDate timesheetDate = timesheet.getDate();

            if (!timesheetDate.isBefore(periodStartDate) && !timesheetDate.isAfter(payDay)) {
                return true;
            }
        }
        return false;
    }
}
