package br.ufal.ic.p2.wepayu.enums;

public enum EmployeeAttribute {
    NAME("nome"),
    ADDRESS("endereco"),
    SALARY("salario"),
    TYPE("tipo"),
    UNION("sindicalizado"),
    COMMISSION("comissao");

    private final String attribute;

    EmployeeAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getType(){
        return this.attribute;
    }

    public static EmployeeAttribute fromString(String text) {
        for (EmployeeAttribute employeeAttribute : EmployeeAttribute.values()) {
            if (employeeAttribute.attribute.equalsIgnoreCase(text)) {
                return employeeAttribute;
            }
        }
        throw new IllegalArgumentException();
    }
}
