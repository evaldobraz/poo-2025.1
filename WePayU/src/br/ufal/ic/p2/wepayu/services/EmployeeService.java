package br.ufal.ic.p2.wepayu.services;

import br.ufal.ic.p2.wepayu.enums.EmployeeAttribute;
import br.ufal.ic.p2.wepayu.enums.EmployeeType;
import br.ufal.ic.p2.wepayu.exceptions.*;
import br.ufal.ic.p2.wepayu.models.Employee;

import java.util.ArrayList;
import java.util.List;


public class EmployeeService {
    public static Employee createNewEmployee(List<Employee> employees, String name, String address, String type, String salary) {
        EmployeeService.validateParameters(name, address, type, salary);
        Employee newEmployee = new Employee(name, address, type, NumberService.formatAsDouble(salary));

        employees.add(newEmployee);
        return newEmployee;
    }

    public static Employee createNewEmployee(List<Employee> employees, String name, String address, String type, String salary, String commission) {
        EmployeeService.validateParameters(name, address, type, salary, commission);
        
        Employee newEmployee = new Employee(name, address, type, NumberService.formatAsDouble(salary), NumberService.formatAsDouble(commission));

        employees.add(newEmployee);
        return newEmployee;
    }

    public static String searchEmployeeField(List<Employee> employeeList, String employeeID, String searchField) throws EmpregadoNaoExisteException, AtributoNuloException {
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
            }
        }

        if(employee == null) throw new EmpregadoNaoExisteException("Empregado nao existe.");

        validateAttribute(searchField);

        return switch (searchField) {
            case "nome" -> String.valueOf(employee.getName());
            case "endereco" -> String.valueOf(employee.getAddress());
            case "tipo" -> String.valueOf(employee.getType());
            case "salario" -> NumberService.formatAsCurrency(employee.getSalary());
            case "sindicalizado" -> String.valueOf(employee.isUnion());
            case "comissao" -> NumberService.formatAsCurrency(employee.getCommission());
            default -> null;
        };
    }


    public static Employee searchByName(String name, int index, List<Employee> employeeList) throws EmpregadoNaoExisteException{
        int foundIndex = 1;

        for(Employee employee : employeeList){
            if(employee.getName().equalsIgnoreCase(name) && foundIndex == index){
                return employee;
            }
            else if(employee.getName().equalsIgnoreCase(name) && foundIndex < index) foundIndex++;
        }

        throw new EmpregadoNaoExisteException("Nao ha empregado com esse nome.");
    }

    public static void removeEmployee(List<Employee> employeeList, String id) throws EmpregadoNaoExisteException, AtributoNuloException {

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

    public static void validateParameters(String name, String address, String type, String  salary)  {
        validateName(name);
        validateAddress(address);
        validateType(type);
        validateSalary(salary);
    }

    public static void validateParameters(String name, String address, String type, String  salary, String commission)  throws IllegalArgumentException {
        validateParameters(name, address, type, salary);
        validateCommission(commission);
    }

    public static void validateName(String name) throws AtributoNuloException {
        if(name == null || name.isEmpty()) throw new AtributoNuloException("Nome nao pode ser nulo.");
    }

    public static void validateAddress(String address) throws AtributoNuloException {
        if(address == null || address.isEmpty()) throw new AtributoNuloException("Endereco nao pode ser nulo.");
    }

    public static void validateType(String type) throws AtributoNuloException, IllegalTypeException {
        if(type == null || type.isEmpty()) throw new AtributoNuloException("Tipo nao pode ser nulo.");

        try{
            EmployeeType validate = EmployeeType.fromString(type);
        }catch (IllegalArgumentException e) {
            throw new IllegalTypeException();
        }
    }

    public static void validateSalary(String salary) throws AtributoNuloException {
        if(salary == null || salary.isEmpty()) throw new AtributoNuloException("Salario nao pode ser nulo.");
        
        try {
            NumberService.formatAsDouble(salary);
        }catch(NotANumberException e) {
            throw new NotANumberException("Salario deve ser numerico.");
        }
    }

    public static void validateCommission(String commission) throws AtributoNuloException {


        if(commission == null || commission.isEmpty()) throw new AtributoNuloException("Comissao nao pode ser nula.");

        try {
            NumberService.formatAsDouble(commission);
        } catch(NotANumberException e) {
            throw new NotANumberException("Comissao deve ser numerica.");
        }

    }

    public static void validateAttribute(String attribute) throws AtributoNuloException, IllegalTypeException {
        try{
            EmployeeAttribute validate = EmployeeAttribute.fromString(attribute);
        }catch (IllegalArgumentException e) {
            throw new IllegalAttributeException();
        }
    }
}