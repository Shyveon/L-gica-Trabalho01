import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        // Pegando os valores que o usuário passa
        Scanner reader = new Scanner(System.in);
        System.out.print("Variável 1:");
        String char1 = reader.nextLine();
        System.out.print("Variável 2:");
        String char2 = reader.nextLine();
        reader.close();

        // Formando os valores da tabela verdade
        TruthTable truthTable = new TruthTable(char1, char2);
        LinkedHashMap<String, Boolean[]> values = truthTable.GetTable();
        Object[] keys = values.keySet().toArray();

        StringBuilder stringBuilder = new StringBuilder();

        // Tudo abaixo é formatação, deveria ser mandado para outra classe mas a preguiça ó 😥
        for (int i = 0; i < values.size(); i++) {
            stringBuilder.append(String.format(" %s |", keys[i]));
        }
        
        stringBuilder.append("\n");
        stringBuilder.append("-".repeat(stringBuilder.length() - 1));
        stringBuilder.append("\n");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < values.size(); j++) {
                stringBuilder.append(String.format(" %s%s |", values.get(keys[j])[i] ? "1" : "0", " ".repeat(keys[j].toString().length()-1)));   
            }
            stringBuilder.append("\n");
        }

        System.out.println(stringBuilder.toString());
    }
}
