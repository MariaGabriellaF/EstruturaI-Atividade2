import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DequeString implements Iterable<String> {

    private int n; //Contador de elementos
    private No Sentinela;//Nó artificial para marcar inicio e fim

    public DequeString() {
        n = 0;
        Sentinela = new No();
        Sentinela.prox = Sentinela;
        Sentinela.ant = Sentinela;
    }

    private class No { //Classe Nó
        private String dado;
        private No prox;
        private No ant;

    }

    public void push_front(String item) {

        //criar novo no e armazenar dados
        No tmp = new No();
        tmp.dado = item;

        //definir anterior e proximo do novo no
        tmp.ant = Sentinela;
        tmp.prox = Sentinela.prox;

        //ajustar a sentinela e o seguinte
        Sentinela.prox = tmp;
        tmp.prox.ant = tmp;
        ++n;
    }

    public void push_back(String item) {
        //criar novo no e armazenar dados
        No tmp = new No();
        tmp.dado = item;

        //definir anterior e proximo do novo no
        tmp.ant = Sentinela.ant;
        tmp.prox = Sentinela;

        //ajustar a sentinela e o anterior
        Sentinela.ant = tmp;

        tmp.ant.prox = tmp;

        n++;
    }

    public String pop_front() {

        No tmp = Sentinela.prox;

        String meuDado = tmp.dado;

        //Atualizar o nó anterior para apontar para o proximo do que sera
        tmp.ant.prox = tmp.prox;

        //Atualizar o nó proximo para apontar para o anterior do que sera
        tmp.prox.ant = tmp.ant;

        --n;

        return meuDado;

    }

    public String pop_back() {

        No tmp = Sentinela.ant;

        String meuDado = tmp.dado;

        //Atualizar o nó anterior para apontar para o proximo do que sera
        tmp.ant.prox = tmp.prox;

        //Atualizar o nó proximo para apontar para o anterior do que sera
        tmp.prox.ant = tmp.ant;

        --n;

        return meuDado;
    }

    public No first() {
        if (Sentinela == Sentinela.prox) return null;
        return Sentinela.prox;
    }

    public boolean isEmpty() {
        return n == 0;
    }//Sentinela == Sentinela.prox

    public int size() {
        return n;
    }

    public ListIterator<String> iterator() {
        return new DequeIterator();
    }

    public class DequeIterator implements ListIterator<String> {
        private No atual = Sentinela.prox;
        private int indice = 0;
        private No acessadoultimo = null;

        public boolean hasNext() {
            return indice < (n);
        }

        public boolean hasPrevious() {
            return indice > 0;
        }

        public int previousIndex() {
            return indice - 1;
        }

        public int nextIndex() {
            return indice;
        }

        public String next() {
            if (!hasNext()) return null;

            String meuDado = atual.dado;
            acessadoultimo = atual;
            atual = atual.prox;
            indice++;
            return meuDado;
        }

        public String previous() {
            if (!hasPrevious()) return null;
            atual = atual.ant;

            String meuDado = atual.dado;
            acessadoultimo = atual;
            indice--;
            return meuDado;
        }

        public String get() {
            if (atual == null) throw new IllegalStateException();
            return atual.dado;
        }

        public void set(String x) {
            if (acessadoultimo == null) throw new IllegalStateException();
            acessadoultimo.dado = x;
        }

        public void remove() {
            if (acessadoultimo == null) throw new IllegalStateException();
            acessadoultimo.ant.prox = acessadoultimo.prox;
            acessadoultimo.prox.ant = acessadoultimo.ant;
            --n;
            if (atual == acessadoultimo)
                atual = acessadoultimo.prox;
            else
                indice--;
            acessadoultimo = null;
        }

        public void add(String x) {
            //Inserir apos atual
            No tmp = new No();
            tmp.dado = x;

            tmp.prox = atual.prox;
            tmp.ant = atual;
            tmp.prox.ant = tmp;
            atual .prox = tmp;
            n++;
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            DequeIterator iterator = new DequeIterator();
            while (iterator.hasNext()) {
                s.append(iterator.next());
                s.append(" ");
            }

            return s.toString();
        }
    }

}