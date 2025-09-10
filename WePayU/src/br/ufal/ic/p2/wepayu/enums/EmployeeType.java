package br.ufal.ic.p2.wepayu.enums;

public enum EmployeeType {
    SALARIED("assalariado"),
    HOURLY("horista"),
    COMMISSIONED("comissionado");

    private final String type;

    EmployeeType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

    public static EmployeeType fromString(String text) {
        for (EmployeeType employeeType : EmployeeType.values()) {
            if (employeeType.type.equalsIgnoreCase(text)) {
                return employeeType;
            }
        }
        throw new IllegalArgumentException();
    }
}
