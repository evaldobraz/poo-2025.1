package br.ufal.ic.p2.wepayu.service;

import br.ufal.ic.p2.wepayu.exception.*;
import br.ufal.ic.p2.wepayu.model.*;

import java.util.List;


public class EmployeeService {
    public static Employee createNewEmployee(List<Employee> employees, String name, String address, String type, String salary) throws Exception{

        ValidationService.validateParameters(name, address, type, salary);

        Employee newEmployee = switch(type){
            case "horista" -> new Hourly(name, address, type, NumberService.formatAsDouble(salary));
            case "assalariado" -> new Salaried(name, address, type, NumberService.formatAsDouble(salary));
            default -> throw new IllegalArgumentException("Tipo nao aplicavel.");
        };

        employees.add(newEmployee);
        return newEmployee;
    }

    public static Employee createNewEmployee(List<Employee> employees, String name, String address, String type, String salary, String commission) throws Exception{

        ValidationService.validateParameters(name, address, type, salary, commission);

        Employee newEmployee;

        if ("comissionado".equals(type)) {
            newEmployee = new Commissioned(name, address, type, NumberService.formatAsDouble(salary), NumberService.formatAsDouble(commission));
        } else {
            throw new Exception("Tipo nao aplicavel.");
        }
        
        employees.add(newEmployee);
        return newEmployee;
    }

    public static String searchEmployeeField(List<Employee> employeeList, String employeeID, String searchField) throws Exception {

        Employee employee = getEmployeeById(employeeList, employeeID);
        PaymentMethod paymentMethod = employee.getPaymentMethod();

        return switch (searchField) {
            case "nome" -> String.valueOf(employee.getName());
            case "endereco" -> String.valueOf(employee.getAddress());
            case "tipo" -> String.valueOf(employee.getType());
            case "salario" -> NumberService.formatAsCurrency(employee.getSalary());
            case "sindicalizado" -> String.valueOf(employee.isUnion());
            case "idSindicato" -> {
                if(employee.isUnion()){
                    yield  employee.getUnionId();
                } else throw new Exception("Empregado nao eh sindicalizado.");
            }
            case "taxaSindical" -> {
                if(employee.isUnion()){
                    yield  NumberService.formatAsCurrency(employee.getUnionFee());
                } else throw new Exception("Empregado nao eh sindicalizado.");
            }
            case "comissao" -> {
                if(employee instanceof Commissioned commissioned){
                    yield NumberService.formatAsCurrency(commissioned.getCommission());
                } else throw new Exception("Empregado nao eh comissionado.");
            }
            case "banco" -> {
                if((paymentMethod instanceof BankAccountDeposit account)) {
                    yield account.getBankName();
                } else throw new Exception("Empregado nao recebe em banco.");
            }
            case "agencia" -> {
                if((paymentMethod instanceof BankAccountDeposit account)) {
                    yield account.getAgencyNumber();
                } else throw new Exception("Empregado nao recebe em banco.");
            }
            case "contaCorrente" -> {
                if((paymentMethod instanceof BankAccountDeposit account)) {
                    yield account.getAccountNumber();
                } else throw new Exception("Empregado nao recebe em banco.");
            }
            case "metodoPagamento" -> {
                if((paymentMethod instanceof InHandsCheck)) yield "emMaos";
                else if ((paymentMethod instanceof BankAccountDeposit)) yield "banco";
                else yield "correios";
            }
            case "agendaPagamento" -> employee.getPaymentSchedule().toString();

            default -> throw new IllegalAttributeException();
        };
    }

    static Employee getEmployeeById(List<Employee> employeeList, String employeeID) throws EmpregadoNaoExisteException {

        int id;

        if(employeeID ==  null || employeeID.isEmpty()) {
            throw new AtributoNuloException("Identificacao do empregado nao pode ser nula.");
        }

        try{
            id = Integer.parseInt(employeeID);
        }catch(NumberFormatException e){
            throw new EmpregadoNaoExisteException("Empregado nao existe.");
        }

        Employee employee = null;

        for(Employee employeeSearch : employeeList) {
            if(employeeSearch.getId() == id) {
                employee = employeeSearch;
                break;
            }
        }

        if(employee == null) throw new EmpregadoNaoExisteException("Empregado nao existe.");
        return employee;
    }

    public static Employee getEmployeeByUnionId(List<Employee> employeeList,String employeeUnionId) throws Exception {
        if(employeeUnionId ==  null || employeeUnionId.isEmpty()) {
            throw new AtributoNuloException("Identificacao do membro nao pode ser nula.");
        }

        Employee employee = null;

        for(Employee employeeSearch : employeeList) {
            if(employeeSearch.getUnionId().equals(employeeUnionId)) {
                employee = employeeSearch;
                break;
            }
        }

        if(employee == null) throw new EmpregadoNaoExisteException("Membro nao existe.");
        return employee;
    }

    public static Employee searchByName(String name, int index, List<Employee> employeeList) throws Exception{
        int foundIndex = 1;

        for(Employee employee : employeeList){
            if(employee.getName().equalsIgnoreCase(name) && foundIndex == index){
                return employee;
            }
            else if(employee.getName().equalsIgnoreCase(name) && foundIndex < index) foundIndex++;
        }

        throw new EmpregadoNaoExisteException("Nao ha empregado com esse nome.");
    }

    public static void removeEmployee(List<Employee> employeeList, String id) throws Exception {

        if(id == null || id.isEmpty()) {
            throw new AtributoNuloException("Identificacao do empregado nao pode ser nula.");
        }

        int employeeID;

        try{
            employeeID = Integer.parseInt(id);
        }catch(NumberFormatException e){
            throw new EmpregadoNaoExisteException("Empregado nao existe.");
        }

        Employee employeeToRemove = null;

        for (Employee employee : employeeList) {
            if (employee.getId() == employeeID) {
                employeeToRemove = employee;
                break;
            }
        }

        if(employeeToRemove != null) {
            employeeList.remove(employeeToRemove);
        }

        throw new EmpregadoNaoExisteException("Empregado nao existe.");
    }


    public static void editEmployee(List<Employee> employeeList, String employeeId, String attribute, String value, String unionId, String unionFee) throws Exception {


        Employee foundEmployee = getEmployeeById(employeeList,employeeId);

        if(attribute.equalsIgnoreCase("sindicalizado")) {
            if(UnionService.unionIdAlreadyExists(unionId, employeeList)) throw new Exception("Ha outro empregado com esta identificacao de sindicato");

            if(value.equalsIgnoreCase("true")) {
                ValidationService.validateUnion(unionId, unionFee);
                foundEmployee.setUnion(true);
                foundEmployee.setUnionFee(NumberService.formatAsDouble(unionFee));
                foundEmployee.setUnionId(unionId);
            }
            else throw new IllegalArgumentException("Valor deve ser true ou false.");

        }else throw new Exception("Atributo nao existe.");
    }

    public static void editEmployee(List<Employee> employeeList, String employeeId, String attribute, String value) throws Exception {


        Employee foundEmployee = getEmployeeById(employeeList,employeeId);

        switch(attribute) {
            case "sindicalizado" -> {
                if(value.equalsIgnoreCase("false")) {
                    foundEmployee.setUnion(false);
                    foundEmployee.setUnionFee(0);
                    foundEmployee.setUnionId("");
                }
                else throw new IllegalArgumentException("Valor deve ser true ou false.");
            }
            case"comissao" -> {
                if(foundEmployee instanceof Commissioned commissioned) {
                    ValidationService.validateCommission(value);
                    commissioned.setCommission(NumberService.formatAsDouble(value));
                }
                else throw new WrongEmployeeTypeException("Empregado nao eh comissionado.");
            }
            case "nome" -> foundEmployee.setName(value);
            case "endereco" -> foundEmployee.setAddress(value);
            case "salario" -> {
                ValidationService.validateSalary(value);
                foundEmployee.setSalary(NumberService.formatAsDouble(value));
            }
            case "metodoPagamento" -> {
                editEmployee(employeeList, employeeId, attribute, value, null, null, null);
            }
            case "tipo" -> {
                if(value.equalsIgnoreCase("assalariado")) {
                    Salaried updatedEmployee = (Salaried)getUpdatedEmployee(value, foundEmployee);

                    employeeList.add(updatedEmployee);
                    employeeList.remove(foundEmployee);
                } else throw new Exception("Tipo invalido.");
            }
            case "agendaPagamento" -> {

                foundEmployee.setPaymentScheduleFromString(value);
            }
            default -> throw new Exception("Atributo nao existe.");
        }
    }

    public static void editEmployee(List<Employee> employeeList, String employeeId, String attribute, String value, String bank, String agency, String account ) throws Exception {
        Employee foundEmployee = getEmployeeById(employeeList,employeeId);
        switch(value) {
            case "banco"  -> {
                    ValidationService.validateBankAccount(bank, agency, account);
                    BankAccountDeposit newMethod = new BankAccountDeposit(bank, agency, account);
                    foundEmployee.setPaymentMethod(newMethod);
            }
            case "emMaos"  -> {
                foundEmployee.setPaymentMethod(new InHandsCheck());
            }
            case "correios" -> {
                foundEmployee.setPaymentMethod(new MailCheck());
            }
            default -> throw new Exception("Metodo de pagamento invalido.");
        }

    }

    public static void editEmployee(List<Employee> employeeList, String employeeId, String attribute, String value1, String value2) throws Exception {
        Employee foundEmployee = getEmployeeById(employeeList,employeeId);

        switch (attribute) {
            case "tipo" -> {
                if (value1.equalsIgnoreCase("comissionado")) {
                    Commissioned updatedEmployee = (Commissioned) getUpdatedEmployee(value1, foundEmployee, value2);

                    employeeList.add(updatedEmployee);
                    employeeList.remove(foundEmployee);
                } else if (value1.equalsIgnoreCase("horista")) {
                    Hourly updatedEmployee = (Hourly) getUpdatedEmployee(value1, foundEmployee, value2);

                    employeeList.add(updatedEmployee);
                    employeeList.remove(foundEmployee);
                }else throw new Exception("Tipo invalido.");
            }
        }
    }

    private static Employee getUpdatedEmployee(String newType, Employee foundEmployee) throws Exception {
        return getUpdatedEmployee(newType, foundEmployee, null);
    }

    private static Employee getUpdatedEmployee(String newType, Employee foundEmployee, String remuneration) throws Exception {

        int existingId = foundEmployee.getId();
        String name = foundEmployee.getName();
        String address = foundEmployee.getAddress();
        double salary = foundEmployee.getSalary();

        Employee newEmployee;
        double remunerationValue = (remuneration != null) ? NumberService.formatAsDouble(remuneration) : salary; // Trata o parse se houver

        switch (newType.toLowerCase()) {
            case "assalariado":
                newEmployee = new Salaried(existingId, name, address, newType, remunerationValue);
                break;
            case "horista":
                newEmployee = new Hourly(existingId, name, address, newType, remunerationValue);
                break;
            case "comissionado":
                if (remuneration == null) {
                    throw new IllegalArgumentException("Comissão nao pode ser nula.");
                }
                newEmployee = new Commissioned(existingId, name, address, newType, salary, remunerationValue);
                break;
            default:
                throw new IllegalArgumentException("Tipo de empregado inválido.");
        }

        newEmployee.setFees(foundEmployee.getFees());
        newEmployee.setUnionId(foundEmployee.getUnionId());
        newEmployee.setUnionFee(foundEmployee.getUnionFee());
        newEmployee.setUnion(foundEmployee.isUnion());
        newEmployee.setPaymentMethod(foundEmployee.getPaymentMethod());

        return newEmployee;
    }


}