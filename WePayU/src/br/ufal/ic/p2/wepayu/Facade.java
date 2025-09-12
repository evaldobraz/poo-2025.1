package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.exceptions.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.Employee;
import br.ufal.ic.p2.wepayu.services.EmployeeService;
import br.ufal.ic.p2.wepayu.services.ResetSystemService;

import java.util.ArrayList;
import java.util.List;


public class Facade {
    static List<Employee> employees = new ArrayList<>();

    public void zerarSistema()
    {
        ResetSystemService.reset(employees);
    }

    //criar empregado normal
    public int criarEmpregado(String nome, String endereco, String tipo, String salario) {
        Employee newEmployee = EmployeeService.createNewEmployee(employees, nome, endereco, tipo, salario);

        return newEmployee.getId();
    }

    //criar empregado com comissão
    public int criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) {
        Employee newEmployee = EmployeeService.createNewEmployee(employees, nome, endereco, tipo, salario, comissao);
        return newEmployee.getId();
    }

    public String getAtributoEmpregado (String employeeID, String atributo) throws EmpregadoNaoExisteException {
        return EmployeeService.searchEmployeeField(employees, employeeID, atributo);
    }

    public int getEmpregadoPorNome(String nome, int index) throws EmpregadoNaoExisteException {
        Employee foundEmployee = EmployeeService.searchByName(nome, index, employees);
        return  foundEmployee.getId();
    }

    public void removerEmpregado(String id) throws EmpregadoNaoExisteException {
        EmployeeService.removeEmployee(employees, id);
    }


    public static void encerrarSistema()
    {
        //System.exit(0);
//        List<Employee> emp_copy = employees;
//        for(Employee employee : employees) {
//            System.out.println(employee);
//        }

    }

}
