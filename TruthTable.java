import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

public class TruthTable {
    private String var1;
    private String var2;

    public TruthTable(String var1, String var2) {
        this.var1 = var1;
        this.var2 = var2;
    }

    // Método que pega todos os valores das variaveis 1 e 2 passadas pelo usuário
    private boolean [][] GetVars() {
        int lines = 4;
        boolean [][] listOfLists = new boolean[2][lines];
        // Variável responsável por fazer o comportamento básico de alternÂncia
        int switchValueAt = 1;

        for (int i = 1; i >= 0; i--) {
            boolean value = true;
            int oldSwitchValue = switchValueAt;
            boolean [] listOfValues = new boolean[lines];

            // Loop que seta valores e executa alternância
            for (int j = 0; j < lines; j++) {
                listOfValues[j] = value;

                if (switchValueAt - 1 <= 0) {
                    value = !value;
                    switchValueAt = oldSwitchValue;
                } else {
                    switchValueAt--;
                }
            }

            listOfLists[i] = listOfValues;
            switchValueAt = switchValueAt * 2;
        }

        return listOfLists;
    }

    // Método básica que transforma em lista de tipo primitivo para lista em classe
    private Boolean[] ToBoolean(boolean[] input) {
        Boolean[] output = new Boolean[input.length];

        for (int i = 0; i < output.length; i++) {
            output[i] = Boolean.valueOf(input[i]);
        }

        return output;
    }

    // COMEÇO: Métodos de ajuda para fazer as operações lógicas!!
    public Boolean HelperConjuction(Boolean p, Boolean q) {
        return p && q;
    }

    public Boolean HelperDisjunction(Boolean p, Boolean q) {
        return p || q;
    }

    public Boolean HelperImplication(Boolean p, Boolean q) {
        return HelperDisjunction(!p, q);
    }

    public Boolean HelperBiImplication(Boolean p, Boolean q) {
        return HelperConjuction(HelperImplication(p, q), HelperImplication(q, p));
    }
    // FIM: Métodos de ajuda para fazer as operações lógicas!!

    public LinkedHashMap<String, Boolean[]> GetTable() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // Variáveis de apoio (vars) e variável de saida (output)
        boolean[][] vars = GetVars();
        LinkedHashMap<String, Boolean[]> output = new LinkedHashMap<>();
        output.put(var1, ToBoolean(vars[0]));
        output.put(var2, ToBoolean(vars[1]));

        // Variáveis de métodos
        Method[] all_methods = TruthTable.class.getMethods();
        Method[] helper_methods = new Method[4];
        String[] helper_names = {"HelperConjuction", "HelperDisjunction", "HelperImplication", "HelperBiImplication"};
        int counter = 0;
        

        // filtrando os métodos 
        for (int i = 0; i < all_methods.length; i++) {
            for (int j = 0; j < helper_names.length; j++) {
                if (all_methods[i].getName().equals(helper_names[j])) {
                    helper_methods[counter] = all_methods[i];
                    counter++;
                    break;
                }
            }
        }

        // Loop responsável por executar todas os métodos na lista
        for (int i = 0; i < helper_methods.length; i++) {
            Boolean[] operationValues = new Boolean[vars[0].length];

            // Pegando todos os valores das variáveis 1 e 2 e executando no método
            for (int j = 0; j < operationValues.length; j++) {
                operationValues[j] = (Boolean) helper_methods[i].invoke(this, vars[0][j], vars[1][j]);
            }

            output.put(helper_methods[i].getName().replace("Helper", ""), operationValues);
        }


        return output;
    }
}
