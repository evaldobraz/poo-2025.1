package br.ufal.ic.p2.wepayu.service;

import br.ufal.ic.p2.wepayu.model.schedule.MonthlySchedule;
import br.ufal.ic.p2.wepayu.model.schedule.PaymentSchedule;
import br.ufal.ic.p2.wepayu.model.schedule.WeeklySchedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleService {

    private static final List<String> availableSchedules = new ArrayList<>();

    static {
        reset();
    }

    public static void reset() {
        availableSchedules.clear();
        availableSchedules.add("semanal 5"); // alias para semanal 1 5
        availableSchedules.add("mensal $");
        availableSchedules.add("semanal 2 5");
    }

    public static void addSchedule(String description) throws Exception {
        if (availableSchedules.contains(description)) {
            throw new Exception("Agenda de pagamentos ja existe");
        }

        validateScheduleFormat(description);

        availableSchedules.add(description);
    }

    // Método chamado pelo comando 'alteraEmpregado' (setScheduleFromString)
    public static PaymentSchedule createSchedule(String scheduleString) throws Exception {
        if (!availableSchedules.contains(scheduleString)) {
            throw new Exception("Agenda de pagamento nao esta disponivel");
        }

        String[] parts = scheduleString.split(" ");

        if (parts[0].equals("mensal")) {
            return new MonthlySchedule(parts[1]);
        }

        if (parts[0].equals("semanal")) {
            if (parts.length == 2) {
                int day = Integer.parseInt(parts[1]);
                return new WeeklySchedule(1, day);
            } else {
                int interval = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                return new WeeklySchedule(interval, day);
            }
        }

        throw new Exception("Tipo de agenda desconhecido");
    }

    private static void validateScheduleFormat(String description) throws Exception {
        String[] parts = description.split(" ");
        String type = parts[0];

        try {
            if (type.equals("mensal")) {
                if (parts.length != 2) throw new Exception();
                String dayStr = parts[1];

                if (!dayStr.equals("$")) {
                    int day = Integer.parseInt(dayStr);
                    if (day < 1 || day > 28) throw new Exception();
                }
            }
            else if (type.equals("semanal")) {
                if (parts.length == 2) {
                    // Formato: semanal <dia> (ex: semanal 5)
                    int day = Integer.parseInt(parts[1]);
                    if (day < 1 || day > 7) throw new Exception();
                }
                else if (parts.length == 3) {
                    // Formato: semanal <intervalo> <dia> (ex: semanal 2 5)
                    int interval = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    if (interval < 1 || interval > 52) throw new Exception();
                    if (day < 1 || day > 7) throw new Exception();
                }
                else {
                    throw new Exception();
                }
            }
            else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Descricao de agenda invalida");
        }
    }
}