package br.ufal.ic.p2.wepayu.service;

import java.beans.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class SystemService {

    public static void salvarDados(List<?> dados, String nomeArquivo) {
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(nomeArquivo))) {
            encoder.setPersistenceDelegate(LocalDate.class, new PersistenceDelegate() {
                @Override
                protected Expression instantiate(Object oldInstance, Encoder out) {
                    LocalDate date = (LocalDate) oldInstance;
                    return new Expression(date,
                            LocalDate.class,
                            "of", // Nome do método estático para criar a data
                            new Object[] { date.getYear(), date.getMonthValue(), date.getDayOfMonth() });
                }
            });
            encoder.writeObject(dados);
        } catch (Exception e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> carregarDados(String nomeArquivo) {
        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(nomeArquivo))) {
            // O cast é inseguro, mas inevitável
            return (List<T>) decoder.readObject();
        } catch (Exception e) {
            return new ArrayList<T>(); // Retorna lista vazia do tipo T
        }
    }

    public static void reset(List<?> list) {
        if(!list.isEmpty()){
            list.clear();
        }
    }
}
