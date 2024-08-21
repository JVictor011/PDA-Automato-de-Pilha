import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pda.automata.IEdge;
import pda.automata.IState;
import pda.automata.ITransition;

public class State implements IState {
    private final String name;
    private Boolean isFinal = false;
    private final List<ITransition> transitions = new ArrayList<ITransition>();

    public State(String name) { this.name = name; }

    public String getName()  { return this.name;    }
    public void setFinal()   { this.isFinal = true; }
    public Boolean isFinal() { return isFinal;      } 

    @Override
    public IState addTransition(IState state, Character c, Character pop, Character push) {
        return addTransitions(state, Util.instance(c, pop, push));
    }

    public IState addTransitions(IState state, IEdge... edges) {
        for (IEdge edge : edges) {
            ITransition transition = Util.instance(state, edge);
            if (transitions.contains(transition))
                continue;
            transitions.add(transition);
            System.out.println("Adicionando transição: " + this.name + " -> " + state.getName() + " com símbolo: " + edge.getC() + ", pop: " + edge.getPop() + ", push: " + edge.getPush());
        }
        return this;
    }

    @Override
    public Set<ITransition> transitions(Character ch, Character pop) {
        Set<ITransition> r = new HashSet<ITransition>();
        for (ITransition t: transitions) {
            IEdge e = t.getEdge();
            System.out.println("Verificando transição: " + e.getC() + ", pop: " + e.getPop() + ", push: " + e.getPush());
            if (e.getC() != null && e.getC().equals(ch) && (e.getPop() == null || e.getPop().equals(pop))) {
                r.add(t);
            }
        }
        return r;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof IState) {
            IState s = (IState) o;
            return s.getName().equals(this.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }

    @Override
    public String toString() {
        return this.name;
    }
}