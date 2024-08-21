import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import pda.automata.IEdge;
import pda.automata.IState;
import pda.automata.ITransition;


public class Util {// PDA = (Q, Σ, δ, q0, F)
    public static boolean testAB(Character a, Character b) {
    	if(a!=null) return a.equals(b);
    	if(b!=null) return b.equals(a);
    	return true;
    }
    public static IEdge instance(Character c, Character _pop, Character _push) { 
    	return new Edge(c, _pop, _push); 
    }
    public static ITransition instance(IState state, IEdge edge) { 
    	return new Transition(state, edge); 
    }
	public static void checkout(boolean b, String w) { 
		if(b)
			System.out.println("reconheceu: " + w);
		else 
			System.out.println("Não reconheceu: " + w);
	}
	public static String readFile(String fileName) throws IOException {
		String content = new String(Files.readAllBytes(Paths.get(fileName)));
		return content;
	}
	public static void writeFile(String fileName, String content) {
		try {
			Files.deleteIfExists(Paths.get(fileName));
			Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE_NEW);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
