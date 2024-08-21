import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import pda.automata.IEdge;
import pda.automata.IPDA;
import pda.automata.IState;
import pda.automata.ITransition;

public class PDAImpl implements IPDA {
    protected IState q; // Estado inicial do PDA
    protected final Stack<Character> pilha = new Stack<>(); // Pilha de acesso
    protected boolean log = false; // Se tem ou nao log

    public PDAImpl(IState q) {
        this.q = q;
        pilha.add('#');
    }

    @Override
    public boolean run(String w) {
        IState estadoAtual = q;
        pilha.clear();
        pilha.add('#');

        estadoAtual = processarTransicoesEpsilon(estadoAtual);

        for (char simbolo : w.toCharArray()) {
            boolean transicaoEncontrada = false;

            System.out.println("Estado atual: " + estadoAtual.getName() + ", Símbolo: " + simbolo + ", Topo da pilha: " + pilha.peek());

            Set<ITransition> transicoes = estadoAtual.transitions(simbolo, pilha.peek());
            System.out.println("Transições encontradas: " + transicoes.size());

            for (ITransition transicao : transicoes) {
                IEdge edge = transicao.getEdge();
                System.out.println("Transição: " + edge.getC() + ", Pop: " + edge.getPop() + ", Push: " + edge.getPush());
                if (edge.getC() == simbolo && (edge.getPop() == pilha.peek() || edge.getPop() == null)) {
                    estadoAtual = transicao.getState();
                    if (edge.getPop() != null) {
                        pilha.pop();
                    }
                    if (edge.getPush() != null) {
                        pilha.push(edge.getPush());
                    }
                    transicaoEncontrada = true;
                    break;
                }
            }

            if (!transicaoEncontrada) {
                System.out.println("Transição não encontrada para o símbolo: " + simbolo);
                return false;
            }

            estadoAtual = processarTransicoesEpsilon(estadoAtual);
        }

        estadoAtual = processarTransicoesEpsilon(estadoAtual);

        System.out.println("Estado final: " + estadoAtual.getName() + ", Pilha: " + pilha);
        return estadoAtual.isFinal() && pilha.peek() == '#';
    }

    private IState processarTransicoesEpsilon(IState estadoAtual) {
        boolean transicaoEncontrada;
        do {
            transicaoEncontrada = false;
            Set<ITransition> transicoesEpsilon = estadoAtual.transitions(null, pilha.peek());
            System.out.println("Transições epsilon encontradas: " + transicoesEpsilon.size());

            for (ITransition transicao : transicoesEpsilon) {
                IEdge edge = transicao.getEdge();
                System.out.println("Transição epsilon: " + edge.getC() + ", Pop: " + edge.getPop() + ", Push: " + edge.getPush());
                estadoAtual = transicao.getState();
                if (edge.getPop() != null) {
                    pilha.pop();
                }
                if (edge.getPush() != null) {
                    pilha.push(edge.getPush());
                }
                transicaoEncontrada = true;
                break;
            }
        } while (transicaoEncontrada);

        return estadoAtual;
    }

    @Override
    public void makeLog() {
        //TODO: insira seu codigo aqui caso deseje fazer log. Sugestao log = true;
    }
}