import pda.automata.IPDA;
import pda.automata.IState;
import pda.automata.lib.PDA;

public class Test {// PDA = (Q, Σ, δ, q0, F)
	public static void calc(String w) throws Exception {
        IState q0 = new State("q0");
        IState q1 = new State("q1");
        IState q2 = new State("q2");
        IState qf = new State("qf");
    
        qf.setFinal();
    
        // Transições para a expressão aritmética
        q0.addTransition(q1, null, null, '$'); // Começa com '$'
        q1.addTransition(q2, null, null, 'E'); // E vai para 'E'
        
        // E -> T + E
        q2.addTransition(q1, null, 'E', 'E'); // Lê 'E'
        q1.addTransition(q2, null, null, '+'); // Lê '+' e vai para 'T'
        q2.addTransition(q2, null, null, 'T'); // Lê 'T'
    
        // E -> T
        q2.addTransition(q2, null, 'E', 'T');
    
        // T -> F * E
        q2.addTransition(q1, null, 'T', 'E'); // Lê 'T'
        q1.addTransition(q2, null, null, '*'); // Lê '*'
        q2.addTransition(q2, null, null, 'F'); // Lê 'F'
    
        // T -> F
        q2.addTransition(q2, null, 'T', 'F');
    
        // F -> a
        q2.addTransition(q2, null, 'F', 'a');
    
        // F -> (E)
        q2.addTransition(q1, null, 'F', ')');
        q1.addTransition(q2, null, null, 'E');
        q2.addTransition(q2, null, null, '(');
    
        // Transições para reconhecer os caracteres válidos
        q2.addTransition(q2, 'a', 'a', null);
        q2.addTransition(q2, '+', '+', null);
        q2.addTransition(q2, '*', '*', null);
        q2.addTransition(q2, '(', '(', null);
        q2.addTransition(q2, ')', ')', null);
    
        q2.addTransition(qf, null, '$', null); // Aceita se chegar em qf
    
        IPDA pda = new PDAImpl(q0);
        boolean b = pda.run(w);
        Util.checkout(b, w);
        System.out.println("*****************************");
    }
    
	
    public static void reverso(String w) throws Exception {
        IState q1 = new State("q1");
        IState q2 = new State("q2");
        IState q3 = new State("q3");
        IState q4 = new State("q4");
        q1.setFinal(); 
        q4.setFinal();
    
        q1.addTransition(q2, null, null, '$'); // Inicializa a pilha com '$'
    
        q2.addTransition(q2, '0', null, '0'); // Empilha '0'
        q2.addTransition(q2, '1', null, '1'); // Empilha '1'
        q2.addTransition(q3, null, null, null); // Passa para o próximo estado sem consumir nada
    
        q3.addTransition(q3, '0', '0', null); // Desempilha '0'
        q3.addTransition(q3, '1', '1', null); // Desempilha '1'
        
        q3.addTransition(q4, null, '$', null); // Finaliza se encontra '$'
    
        IPDA pda = new PDAImpl(q1);
        boolean b = pda.run(w);
        Util.checkout(b, w);
        System.out.println("*****************************");
    }
    

    public static void teste_y_x(String w) throws Exception {
        System.out.println("{ w in Σ^* | w é um número binário múltiplo de 3}");
        IState q0 = new State("q0");
        IState q1 = new State("q1");
        IState q2 = new State("q2");
        q0.setFinal();
    
        q0.addTransition(q0, '0', null, null); // Estado q0 permanece em q0 com '0'
        q0.addTransition(q1, '1', null, null); // Estado q0 vai para q1 com '1'
        q1.addTransition(q0, '1', null, null); // Estado q1 vai para q0 com '1'
        q1.addTransition(q2, '0', null, null); // Estado q1 vai para q2 com '0'
        q2.addTransition(q2, '1', null, null); // Estado q2 permanece em q2 com '1'
        q2.addTransition(q1, '0', null, null); // Estado q2 vai para q1 com '0'
    
        IPDA pda = new PDAImpl(q0);
        boolean b = pda.run(w);
        Util.checkout(b, w);
        System.out.println("*****************************");
    }
    

    public static void testes_adicionais() {
        System.out.println("Testando múltiplos de 3:");
        try {
            teste_y_x("110");  // 6 em decimal, múltiplo de 3
            teste_y_x("1110"); // 14 em decimal, não múltiplo de 3
            teste_y_x("1001"); // 9 em decimal, múltiplo de 3
            teste_y_x("000");   // 0 em decimal, múltiplo de 3
            teste_y_x("1101");  // 13 em decimal, não múltiplo de 3
        } catch (Exception e) {
            System.out.println("Erro em teste de múltiplos de 3: " + e.getMessage());
        }

        // Teste de reverso
        System.out.println("Testando reverso:");
        try {
            reverso("101");        // '101' é palíndromo
            reverso("1100");       // '1100' não é palíndromo
            reverso("11100111");   // '11100111' é palíndromo
            reverso("0000");       // '0000' é palíndromo
        } catch (Exception e) {
            System.out.println("Erro em teste de reverso: " + e.getMessage());
        }

        System.out.println("Testando expressões aritméticas:");
        try {
            calc("a+b");             // Deve ser aceito
            calc("(a*b)+(c*a)");     // Deve ser aceito
            calc("a+(b*a)");         // Deve ser aceito
            calc("a*+b");            // Deve ser rejeitado
        } catch (Exception e) {
            System.out.println("Erro em teste de expressões aritméticas: " + e.getMessage());
        }
    }
}