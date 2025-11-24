package br.ufal.ic.p2.wepayu.service;

import br.ufal.ic.p2.wepayu.exception.DataInvalidaException;
import br.ufal.ic.p2.wepayu.exception.WrongEmployeeTypeException;
import br.ufal.ic.p2.wepayu.model.*;
import br.ufal.ic.p2.wepayu.model.schedule.MonthlySchedule;
import br.ufal.ic.p2.wepayu.model.schedule.WeeklySchedule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CalculatorService {


    public static double calculateValue(List<Employee> employeeList, String employeeId, String startDate, String endDate, Class<?> expectedType, String valueType) throws Exception {

        Employee foundEmployee = EmployeeService.getEmployeeById(employeeList, employeeId);

        LocalDate initialDate, finalDate;
        try {
            initialDate = DateService.stringToLocalDate(startDate);
        } catch (DataInvalidaException e) {
            throw new DataInvalidaException(" inicial ");
        }

        try {
            finalDate = DateService.stringToLocalDate(endDate);
        } catch (DataInvalidaException e) {
            throw new DataInvalidaException(" final ");
        }

        if (initialDate.isAfter(finalDate)) {
            throw new Exception("Data inicial nao pode ser posterior aa data final.");
        }

        if(valueType.equals("fee")){ //Cálculo das taxas no período independe do tipo do funcionário, por isso ignora o resto abaixo caso verdadeiro

            List<Fee>  listOfValues = foundEmployee.getFees();
            return calculateResult(listOfValues, initialDate, finalDate, valueType);
        }

        if ((!expectedType.isInstance(foundEmployee))) {

            if (expectedType == Commissioned.class) {
                throw new WrongEmployeeTypeException("Empregado nao eh comissionado.");
            }
            if (expectedType == Hourly.class) {
                throw new WrongEmployeeTypeException("Empregado nao eh horista.");
            }
        }

        if (expectedType == Hourly.class) {

            Hourly hourly = (Hourly) foundEmployee;
            List<Timesheet> listOfValues = hourly.getTimesheets();

            return calculateResult(listOfValues, initialDate, finalDate, valueType);
        } else if (expectedType == Commissioned.class) {

            Commissioned commissioned = (Commissioned) foundEmployee;
            List<Sale> listOfValues = commissioned.getSales();

            return calculateResult(listOfValues, initialDate, finalDate, valueType);
        }

        return 0;
    }

    public static double calculateResult(List<?> listOfValues, LocalDate initialDate, LocalDate finalDate, String valueType) {
        double value = 0;

        if (!listOfValues.isEmpty()) {
            for (Object obj : listOfValues) {
                if (obj instanceof Timesheet timesheet) {
                    if (!timesheet.getDate().isBefore(initialDate) && timesheet.getDate().isBefore(finalDate)) {
                        value += switch (valueType) {
                            case "normalHours" -> timesheet.getNormalHours();
                            case "extraHours" -> timesheet.getExtraHours();
                            default -> 0;
                        };
                    }
                }
                if (obj instanceof Sale sale) {
                    if (!sale.getDate().isBefore(initialDate) && sale.getDate().isBefore(finalDate)) {
                        value += sale.getSaleValue();
                    }
                }

                if(obj instanceof Fee fee) {
                    if (!fee.getDate().isBefore(initialDate) && fee.getDate().isBefore(finalDate)) {
                        value += fee.getFee();
                    }
                }
            }
        }
        return value;
    }



/*    public static HourlyPaycheck calculateHourlyPaycheck(Hourly hourly, LocalDate payDay) {
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

    public static String calculateTotalHourlyPaycheck(List<HourlyPaycheck> hourlyPaycheck) {
        double totalNormalHours = 0;
        double totalExtraHours = 0;
        double totalGrossPayment = 0;
        double totalDeductions = 0;
        double totalNetAmount = 0;

        for (HourlyPaycheck paycheck : hourlyPaycheck) {
            totalNormalHours += paycheck.getNormalHours();
            totalExtraHours += paycheck.getExtraHours();
            totalGrossPayment += paycheck.getGross();
            totalDeductions += paycheck.getDeductions();
            totalNetAmount += paycheck.getNetAmount();
        }
        final String TOTAL_FORMAT = "%-36s %5d %5d %13s %9s %15s";

        return String.format(
                TOTAL_FORMAT,
                "TOTAL HORISTAS",
                (int)totalNormalHours,
                (int)totalExtraHours,
                NumberService.formatAsCurrency(totalGrossPayment),
                NumberService.formatAsCurrency(totalDeductions),
                NumberService.formatAsCurrency(totalNetAmount)
        );
    }

   /* public static SalariedPaycheck calculateSalariedPaycheck(Salaried salaried, LocalDate payDay) throws Exception {
        if(DateService.isLastBussinessDayOfMonth(payDay)) {
            LocalDate lastPayDay = salaried.getLastPayDay();
            double[] grossResults = calculateGrossPayment(salaried, lastPayDay, payDay);
            double grossPayment = grossResults[0];
            double deductions = calculateDeductions(salaried, lastPayDay, payDay);
            double netAmount = grossPayment - deductions;


            salaried.resetUnionDebt();

            if(netAmount < 0){
                salaried.setUnionDebt(netAmount*-1);
                netAmount = 0;
            }

            salaried.setLastPayDay(payDay);
            return new SalariedPaycheck(salaried, grossPayment, deductions, netAmount);

        }
        return null;
    }*/

    public static String calculateTotalSalariedPaycheck(List<SalariedPaycheck> salariedPaycheck) {

        double totalGrossPayment = 0;
        double totalDeductions = 0;
        double totalNetAmount = 0;

        for (SalariedPaycheck paycheck : salariedPaycheck) {
            totalGrossPayment += paycheck.getGross();
            totalDeductions += paycheck.getDeductions();
            totalNetAmount += paycheck.getNetAmount();
        }
        final String TOTAL_FORMAT = "%-48s %13s %9s %15s";

        return String.format(
                TOTAL_FORMAT,
                "TOTAL ASSALARIADOS",
                NumberService.formatAsCurrency(totalGrossPayment),
                NumberService.formatAsCurrency(totalDeductions),
                NumberService.formatAsCurrency(totalNetAmount)
        );
    }

    public static String calculateTotalCommissionedPaycheck(List<CommissionedPaycheck> commissionedPaycheck) {

        double totalGrossPayment = 0;
        double totalDeductions = 0;
        double totalNetAmount = 0;
        double totalFixed = 0;
        double totalSales = 0;
        double totalCommission = 0;

        for (CommissionedPaycheck paycheck : commissionedPaycheck) {
            totalGrossPayment += paycheck.getGross();
            totalDeductions += paycheck.getDeductions();
            totalNetAmount += paycheck.getNetAmount();
            totalFixed += paycheck.getFixed();
            totalSales += paycheck.getSales();
            totalCommission += paycheck.getCommission();
        }
        final String TOTAL_FORMAT = "%-21s %8s %8s %8s %13s %9s %15s";

        return String.format(
                TOTAL_FORMAT,
                "TOTAL COMISSIONADOS",
                NumberService.formatAsCurrency(totalFixed),
                NumberService.formatAsCurrency(totalSales),
                NumberService.formatAsCurrency(totalCommission),
                NumberService.formatAsCurrency(totalGrossPayment),
                NumberService.formatAsCurrency(totalDeductions),
                NumberService.formatAsCurrency(totalNetAmount)
        );
    }

    /*public static CommissionedPaycheck calculateSalariedPaycheck(Salaried salaried, LocalDate payDay) {
        LocalDate lastPayDay = salaried.getLastPayDay();

        if(payDay.isEqual(lastPayDay.plusDays(14))){
            double grossPayment = 0;



            return new SalariedPaycheck()
        }

        return null;
    }*/


    public static double[] calculateGrossPayment(Employee employee, LocalDate lastPayDay, LocalDate payDay) {
        double salaryMultiplier = 1.0;

        LocalDate startDate = lastPayDay;

        if (employee.getPaymentSchedule() instanceof WeeklySchedule weekly) {
            int interval = weekly.getInterval();
            salaryMultiplier = (12.0 / 52.0) * interval;

            startDate = payDay.minusWeeks(interval);
        }

        if (lastPayDay.isEqual(payDay) && employee.getPreviousPayDay() != null) {
            startDate = employee.getPreviousPayDay();
        }

        switch (employee) {
            case Hourly hourly -> {
                List<Timesheet> timesheets = hourly.getTimesheets();
                double grossPayment = 0;
                double normalHours = 0;
                double extraHours = 0;

                for (Timesheet timesheet : timesheets) {
                    if (timesheet.getDate().isAfter(startDate) && !timesheet.getDate().isAfter(payDay)) {
                        grossPayment += (timesheet.getNormalHours() * hourly.getSalary()) + (timesheet.getExtraHours() * hourly.getSalary() * 1.5);
                        normalHours += timesheet.getNormalHours();
                        extraHours += timesheet.getExtraHours();
                    }
                }
                return new double[]{NumberService.roundToTwoDecimals(grossPayment), normalHours, extraHours};
            }

            case Salaried salaried -> {
                double grossPayment = NumberService.roundToTwoDecimals(salaried.getSalary() * salaryMultiplier);
                return new double[]{grossPayment, 0, 0};
            }

            case Commissioned commissioned -> {
                double fixedSalary = NumberService.roundToTwoDecimals(commissioned.getSalary() * salaryMultiplier);

                double salesResult = 0;
                for (Sale sale : commissioned.getSales()) {
                    if (sale.getDate().isAfter(startDate) && !sale.getDate().isAfter(payDay)) {
                        salesResult += sale.getSaleValue();
                    }
                }

                double commission = NumberService.roundToTwoDecimals(commissioned.getCommission() * salesResult);

                BigDecimal bFixed = BigDecimal.valueOf(fixedSalary);
                BigDecimal bComm = BigDecimal.valueOf(commission);
                double gross = bFixed.add(bComm).doubleValue();

                return new double[]{gross, fixedSalary, salesResult, commission};
            }
            default -> {
            }
        }
        return null;
    }


    public static double calculateDeductions(Employee employee, LocalDate lastPayDay, LocalDate payDay) {
        LocalDate periodStartDate;


        periodStartDate = lastPayDay;
        if (lastPayDay.isEqual(payDay) && employee.getPreviousPayDay() != null) {
            periodStartDate = employee.getPreviousPayDay().plusDays(1);
        } else {
            periodStartDate = lastPayDay.plusDays(1);
        }


        long days = ChronoUnit.DAYS.between(periodStartDate, payDay) + 1;

        double deductions = employee.getUnionFee() * days;
        deductions += employee.getUnionDebt();

        List<Fee> feeList = employee.getFees();

        for (Fee fee : feeList) {
            if (!fee.getDate().isBefore(periodStartDate) && !fee.getDate().isAfter(payDay)) {
                deductions += fee.getFee();
            }
        }

        return deductions;
    }

}