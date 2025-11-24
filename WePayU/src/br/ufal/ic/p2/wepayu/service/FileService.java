package br.ufal.ic.p2.wepayu.service;

import br.ufal.ic.p2.wepayu.model.CommissionedPaycheck;
import br.ufal.ic.p2.wepayu.model.HourlyPaycheck;
import br.ufal.ic.p2.wepayu.model.Paycheck;
import br.ufal.ic.p2.wepayu.model.SalariedPaycheck;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FileService {

    public static void generatePayrollFile(String fileName, List<HourlyPaycheck> hourlyPaychecks, List<SalariedPaycheck> salariedPaychecks, List<CommissionedPaycheck> commissionedPaychecks, LocalDate payDay, double totalPayroll) throws Exception {

        try(FileWriter fw = new FileWriter(fileName)) {
            final String lineSeparator = "===============================================================================================================================\n";

            fw.write("FOLHA DE PAGAMENTO DO DIA "+payDay.toString()+"\n");
            fw.write("====================================\n\n");
            fw.write(lineSeparator);
            fw.write("===================== HORISTAS ================================================================================================\n");
            fw.write(lineSeparator);
            fw.write("Nome                                 Horas Extra Salario Bruto Descontos Salario Liquido Metodo\n");
            fw.write("==================================== ===== ===== ============= ========= =============== ======================================\n");
            for(Paycheck paycheck : hourlyPaychecks) {
                fw.write(paycheck.toString()+"\n");
            }
            fw.write("\n"+CalculatorService.calculateTotalHourlyPaycheck(hourlyPaychecks)+"\n\n");
            //Assalariados
            fw.write(lineSeparator);
            fw.write("===================== ASSALARIADOS ============================================================================================\n");
            fw.write(lineSeparator);
            fw.write("Nome                                             Salario Bruto Descontos Salario Liquido Metodo\n");
            fw.write("================================================ ============= ========= =============== ======================================\n");
            for(Paycheck paycheck : salariedPaychecks) {
                fw.write(paycheck.toString()+"\n");
            }
            fw.write("\n"+CalculatorService.calculateTotalSalariedPaycheck(salariedPaychecks)+"\n\n");

            //Comissionados
            fw.write(lineSeparator);
            fw.write("===================== COMISSIONADOS ===========================================================================================\n");
            fw.write(lineSeparator);
            fw.write("Nome                  Fixo     Vendas   Comissao Salario Bruto Descontos Salario Liquido Metodo\n");
            fw.write("===================== ======== ======== ======== ============= ========= =============== ======================================\n");
            for(Paycheck paycheck : commissionedPaychecks) {
                fw.write(paycheck.toString()+"\n");
            }
            fw.write("\n"+CalculatorService.calculateTotalCommissionedPaycheck(commissionedPaychecks)+"\n");

            //Total folha (Bruto)
            fw.write("\nTOTAL FOLHA: "+ NumberService.formatAsCurrency(totalPayroll)+"\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
