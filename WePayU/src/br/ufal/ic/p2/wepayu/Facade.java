package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.model.Employee;
import br.ufal.ic.p2.wepayu.service.*;

import java.util.ArrayList;
import java.util.List;


public class Facade {

    private static final String DATABASE = "database.xml";
    static List<Employee> employees = SystemService.carregarDados(DATABASE);

    public void zerarSistema() {
        ScheduleService.reset();
        SystemService.reset(employees);
        SystemService.salvarDados(employees, DATABASE);
    }

    public static void encerrarSistema() {
        SystemService.salvarDados(employees, DATABASE);
    }

    //criar empregado normal ou horista
    public int criarEmpregado(String nome, String endereco, String tipo, String salario) throws Exception{
        Employee newEmployee = EmployeeService.createNewEmployee(employees, nome, endereco, tipo, salario);

        return newEmployee.getId();
    }

    //criar empregado com comissão
    public int criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws Exception{
        Employee newEmployee = EmployeeService.createNewEmployee(employees, nome, endereco, tipo, salario, comissao);

        return newEmployee.getId();
    }

    public String getAtributoEmpregado (String employeeID, String atributo) throws Exception {
        return EmployeeService.searchEmployeeField(employees, employeeID, atributo);
    }

    public int getEmpregadoPorNome(String nome, int index) throws Exception {
        Employee foundEmployee = EmployeeService.searchByName(nome, index, employees);
        return  foundEmployee.getId();
    }

    public void removerEmpregado(String id) throws Exception {
        EmployeeService.removeEmployee(employees, id);
        SystemService.salvarDados(employees, DATABASE);
    }

//US3
    public void lancaCartao(String id, String date, String hours) throws Exception {
        HourlyService.submitTimesheet(employees, id, date, NumberService.formatAsDouble(hours));
    }

    public int getHorasNormaisTrabalhadas(String id, String inicialDate, String finalDate) throws Exception {
        return HourlyService.getWorkedNormalHours(employees, id, inicialDate, finalDate);
    }

    public String getHorasExtrasTrabalhadas(String id, String inicialDate, String finalDate) throws Exception {
        return HourlyService.getWorkedExtraHours(employees, id, inicialDate, finalDate);
    }

//US4
    public void lancaVenda(String employeeId, String saleDate, String value) throws Exception {

        CommissionedService.submitSale(employees, employeeId, saleDate, value);
    }

    public String getVendasRealizadas(String employeeId, String startDate, String endDate) throws Exception {
        return CommissionedService.getSalesValue(employees, employeeId, startDate, endDate);
    }

//US5 e US6

    public void alteraEmpregado(String employeeId, String attribute, String value, String unionId, String unionFee) throws Exception { //serve pra filiar o empregado ao sindicato
        EmployeeService.editEmployee(employees, employeeId, attribute, value, unionId, unionFee);
        SystemService.salvarDados(employees, DATABASE);
    }

    public void alteraEmpregado(String employeeId, String attribute, String value) throws Exception { //altera a maioria dos atributos sem lógica complexa
        EmployeeService.editEmployee(employees, employeeId, attribute, value);
        SystemService.salvarDados(employees, DATABASE);
    }

    public void alteraEmpregado(String employeeId, String attribute, String value, String bank, String agency, String account) throws Exception { //altera o pagamento pra conta bancária
        EmployeeService.editEmployee(employees, employeeId, attribute, value, bank, agency, account);
        SystemService.salvarDados(employees, DATABASE);
    }

    public void alteraEmpregado(String employeeId, String attribute, String value1, String value2) throws Exception { //alterar empregado para comissionado
        EmployeeService.editEmployee(employees, employeeId, attribute, value1, value2);
        SystemService.salvarDados(employees, DATABASE);
    }

    public String getTaxasServico(String employeeId, String startDate, String endDate) throws Exception {
        return NumberService.formatAsCurrency(UnionService.getFees(employees, employeeId, startDate, endDate));
    }

    public void lancaTaxaServico(String employeeUnionId, String date, String value) throws Exception {
        UnionService.submitFee(employees, employeeUnionId, date, value);
        SystemService.salvarDados(employees, DATABASE);
    }

//US7

    public String totalFolha(String date) throws Exception {
        return PayrollService.totalPayroll(date, employees);
    }

    public void rodaFolha(String date, String outputFileName) throws Exception {
        PayrollService.runPayroll(date, outputFileName, employees);
        SystemService.salvarDados(employees, DATABASE);
    }

//US9 não tem comandos novos
//US10
    public void criarAgendaDePagamentos(String descricao) throws Exception {
        ScheduleService.addSchedule(descricao);
    }



}
