package br.ufal.ic.p2.wepayu.model;


import br.ufal.ic.p2.wepayu.model.schedule.PaymentSchedule;
import br.ufal.ic.p2.wepayu.service.DateService;
import br.ufal.ic.p2.wepayu.service.NumberService;
import br.ufal.ic.p2.wepayu.service.ScheduleService;
import br.ufal.ic.p2.wepayu.service.ValidationService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Employee {
    private int id;
    private static int nextId = 0;

    private String name;
    private String address;
    private double salary;
    private String type;
    // Criar uma classe para lidar com as informações de sindicato do funcionário
    private boolean union;
    private String unionId;
    private double unionFee;
    private double unionDebt;
    private List<Fee> fees;

    private PaymentMethod paymentMethod;
    private LocalDate contractDate;
    private LocalDate lastPayDay;
    private LocalDate previousPayDay;
    private PaymentSchedule paymentSchedule;


    public Employee(){}

    public Employee(String name, String address, String type, double salary) throws Exception {
        if(salary < 0) throw new IllegalArgumentException("Salario deve ser nao-negativo.");
        id = nextId++;
        this.name = name;
        this.address = address;
        this.type = type;
        this.salary = salary;
        this.fees = new ArrayList<>();
        this.union = false;
        this.unionId = "";
        this.unionDebt = 0;
        this.paymentMethod = new InHandsCheck();
        this.contractDate = LocalDate.of(2005,12,1);
        this.lastPayDay = contractDate.minusDays(1);
    }

    public Employee(int existingId, String name, String address, String type, double salary) throws Exception {
        this.name = name;
        this.address = address;
        this.type = type;
        this.salary = salary;
        id = existingId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        ValidationService.validateName(name);
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        ValidationService.validateAddress(address);
        this.address = address;
    }

    public double getSalary() {
        return this.salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getType() {
        return this.type;
    }

    public boolean isUnion() {
        return this.union;
    }

    public void setUnion(boolean union) { this.union = union; }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public int getId() {
        return this.id;
    }

    public void setUnionFee(double unionFee) {
        this.unionFee = unionFee;
    }

    public double getUnionFee() {
        return this.unionFee;
    }

    public List<Fee> getFees() {
        return this.fees;
    }

    public void addFee(String date, String value) throws Exception {
        LocalDate d = DateService.stringToLocalDate(date);
        double v = NumberService.formatAsDouble(value);
        if(v<=0) throw new Exception("Valor deve ser positivo.");
        this.fees.add(new Fee(d, v));
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public LocalDate getContractDate() {
        return this.contractDate;
    }

    public void setContractDate(LocalDate contractDate) {
        this.contractDate = contractDate;
    }

    public void setLastPayDay(LocalDate lastPayDay) {
        if (this.lastPayDay != null && !this.lastPayDay.isEqual(lastPayDay)) {
            this.previousPayDay = this.lastPayDay; // Faz backup da data antiga
        }
        this.lastPayDay = lastPayDay;
    }

    public LocalDate getLastPayDay() {
        return this.lastPayDay;
    }

    public static void resetNextId() {
        nextId = 0;
    }

    public double getUnionDebt() {
        return this.unionDebt;
    }

    public void setUnionDebt(double unionDebt) {
        this.unionDebt = unionDebt;
    }

    public void resetUnionDebt() {
        this.unionDebt = 0;
    }

    //public abstract boolean isPayday(LocalDate date);
    public boolean isPayday(LocalDate date) {
        return this.paymentSchedule.isPayDate(date, this);
    }

    public abstract Paycheck calculatePaycheck(LocalDate date);

    public abstract Paycheck simulatePaycheck(LocalDate date);

    public LocalDate getPreviousPayDay() {
        return previousPayDay;
    }

    public void setPreviousPayDay(LocalDate previousPayDay) {
        this.previousPayDay = previousPayDay;
    }


    public PaymentSchedule getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(PaymentSchedule paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }
    public void setPaymentScheduleFromString(String scheduleStr) throws Exception {
        this.paymentSchedule = ScheduleService.createSchedule(scheduleStr);
    }

    @Override
    public String toString() {
        return String.format("FUNCIONÁRIO: %s, ID: %d", this.name, this.id);
    }

}