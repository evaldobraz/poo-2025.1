package br.ufal.ic.p2.wepayu.model;

import br.ufal.ic.p2.wepayu.service.CalculatorService;
import br.ufal.ic.p2.wepayu.service.TimesheetService;
import br.ufal.ic.p2.wepayu.service.UnionService;

import java.sql.SQLOutput;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hourly extends Employee{

    private List<Timesheet> timesheets;

    public Hourly(){}

    public Hourly(String name, String address, String type, double salary) throws Exception {
        super(name, address, type, salary);
        this.setPaymentScheduleFromString("semanal 5");
        timesheets = new ArrayList<>();
    }

    public Hourly(int existingId, String name, String address, String type, double salary) throws Exception {
        super(existingId, name, address, type, salary);
        this.setPaymentScheduleFromString("semanal 5");
        timesheets = new ArrayList<>();
    }


    public void addTimesheet(LocalDate date, double hours) throws Exception {
        if(hours <= 0) throw new Exception("Horas devem ser positivas.");

        Timesheet newTimesheet = new Timesheet(date, hours);

        for (Timesheet timesheet : timesheets) {
            if (timesheet.getDate().equals(newTimesheet.getDate())) {
                return;
            }
        }

        /*if(super.getLastPayDay().isEqualdate.isBefore(super.getLastPayDay())){
            super.setLastPayDay(date);
        }*/
        timesheets.add(newTimesheet);
   }

   public List<Timesheet> getTimesheets() {
        return timesheets;
   }


    @Override
    public Paycheck calculatePaycheck(LocalDate payDay) {
        LocalDate lastPayDay = this.getLastPayDay();
        double[] grossResults = CalculatorService.calculateGrossPayment(this, lastPayDay, payDay);
        double grossPayment = grossResults[0];
        double deductions = CalculatorService.calculateDeductions(this, lastPayDay, payDay);

        double netAmountBeforeZero = grossPayment - deductions;
        double netAmountFinal;

        int normalHours = (int) grossResults[1];
        int extraHours = (int) grossResults[2];

        if (netAmountBeforeZero < 0) {
            this.setUnionDebt(netAmountBeforeZero * -1);
            netAmountFinal = 0;
            deductions = 0;

        } else {
            netAmountFinal = netAmountBeforeZero;
            this.resetUnionDebt();
            UnionService.clearPaidFees(this);
        }
        this.setLastPayDay(payDay);
        return new HourlyPaycheck(this, grossPayment, deductions, netAmountFinal, normalHours, extraHours);
    }

    @Override
    public Paycheck simulatePaycheck(LocalDate payDay) {
        LocalDate lastPayDay = this.getLastPayDay();

        double[] grossResults = CalculatorService.calculateGrossPayment(this, lastPayDay, payDay);
        double grossPayment = grossResults[0];
        double deductions = CalculatorService.calculateDeductions(this, lastPayDay, payDay);
        double netAmount = grossPayment - deductions;

        int normalHours = (int) grossResults[1];
        int extraHours = (int) grossResults[2];


        if(netAmount < 0){
            netAmount = 0;
            deductions = 0;
        }
        return new HourlyPaycheck(this, grossPayment, deductions, netAmount, normalHours, extraHours);
    }

    /*public static HourlyPaycheck calculateHourlyPaycheck(Hourly hourly, LocalDate payDay) {
        LocalDate lastPayDay = hourly.getLastPayDay();
        double[] grossResults = calculateGrossPayment(hourly, lastPayDay, payDay);
        double grossPayment = grossResults[0];
        double deductions = calculateDeductions(hourly, lastPayDay, payDay);
        double netAmount = grossPayment - deductions;

        int normalHours = (int) grossResults[1];
        int extraHours = (int) grossResults[2];

        hourly.resetUnionDebt();

        if(netAmount < 0){
            hourly.setUnionDebt(netAmount*-1);
            netAmount = 0;
        }
//HourlyPaycheck(Employee employee, double gross, double deductions, double netAmount, int normalHours, int extraHours)
        hourly.setLastPayDay(payDay);
        return new HourlyPaycheck(hourly, grossPayment, deductions, netAmount, normalHours, extraHours);
    }*/

//    @Override
//    public boolean isPayday(LocalDate payDay) {
//        return payDay.getDayOfWeek() == DayOfWeek.FRIDAY;
//    }
}
