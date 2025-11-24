package br.ufal.ic.p2.wepayu.service;

import br.ufal.ic.p2.wepayu.enums.EmployeeType;
import br.ufal.ic.p2.wepayu.exception.AtributoNuloException;
import br.ufal.ic.p2.wepayu.exception.IllegalTypeException;
import br.ufal.ic.p2.wepayu.exception.NotANumberException;

public class ValidationService {
    public static void validateParameters(String name, String address, String type, String  salary) throws Exception {
        validateName(name);
        validateAddress(address);
        validateType(type);
        validateSalary(salary);
    }

    public static void validateParameters(String name, String address, String type, String  salary, String commission)  throws Exception {
        validateParameters(name, address, type, salary);
        validateCommission(commission);
    }

    public static void validateName(String name) throws AtributoNuloException {
        if(name == null || name.isEmpty()) throw new AtributoNuloException("Nome nao pode ser nulo.");
    }

    public static void validateAddress(String address) throws AtributoNuloException {
        if(address == null || address.isEmpty()) throw new AtributoNuloException("Endereco nao pode ser nulo.");
    }

    public static void validateType(String type) throws Exception {
        if(type == null || type.isEmpty()) throw new AtributoNuloException("Tipo nao pode ser nulo.");

        try{
            /*EmployeeType validate = */
            EmployeeType.fromString(type);
        }catch (IllegalArgumentException e) {
            throw new IllegalTypeException();
        }
    }

    public static void validateSalary(String salary) throws Exception {
        if(salary == null || salary.isEmpty()) throw new AtributoNuloException("Salario nao pode ser nulo.");
        double remuneration;
        try {
            remuneration = NumberService.formatAsDouble(salary);
        }catch(NotANumberException e) {
            throw new NotANumberException("Salario deve ser numerico.");
        }

        if(remuneration <= 0) throw new Exception("Salario deve ser nao-negativo.");
    }

    public static void validateCommission(String commission) throws Exception {

        if(commission == null || commission.isEmpty()) throw new AtributoNuloException("Comissao nao pode ser nula.");

        double remuneration;
        try {
            remuneration = NumberService.formatAsDouble(commission);
        } catch(NotANumberException e) {
            throw new NotANumberException("Comissao deve ser numerica.");
        }
        if(remuneration <= 0) throw new Exception("Comissao deve ser nao-negativa.");
    }

    public static void validateBankAccount(String bank, String agency, String account) {
        if(bank.isEmpty()) throw new AtributoNuloException("Banco nao pode ser nulo.");
        if(agency.isEmpty()) throw new AtributoNuloException("Agencia nao pode ser nulo.");
        if (account.isEmpty()) throw new AtributoNuloException("Conta corrente nao pode ser nulo.");
    }

    public static void validateUnion(String unionId, String unionFee) throws Exception {
        if(unionId.isEmpty()) throw new AtributoNuloException("Identificacao do sindicato nao pode ser nula.");
        if(unionFee.isEmpty()) throw new  AtributoNuloException("Taxa sindical nao pode ser nula.");

        try {
            if(NumberService.formatAsDouble(unionFee) <= 0) throw new IllegalArgumentException("Taxa sindical deve ser nao-negativa.");
        } catch(NotANumberException e) {
            throw new IllegalArgumentException("Taxa sindical deve ser numerica.");
        }
    }
}
