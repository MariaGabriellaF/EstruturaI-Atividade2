import java.io.PrintStream;
import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class DequeCity  implements Iterable<Cidade>{
    private int n;
    private No Sentinela;
    public DequeCity(){
        n = 0;
        Sentinela = new No();
        Sentinela.prox = Sentinela;
        Sentinela.ant = Sentinela;
    }
    private class No{
        private Cidade dado;
        private String chave;
        private No prox;
        private No ant;
    }
    public void push_front(String key, Cidade item){
        No tmp = new No();
        tmp.dado = item;
        tmp.chave = key;

        tmp.ant = Sentinela;
        tmp.prox = Sentinela.prox;

        Sentinela.prox = tmp;
        tmp.prox.ant = tmp;
        ++n;
    }
    public void push_back(String key, Cidade item){
        No tmp = new No();
        tmp.dado = item;
        tmp.chave = key;

        tmp.ant = Sentinela.ant;
        tmp.prox = Sentinela;

        Sentinela.ant = tmp;
        tmp.ant.prox = Sentinela;
        n++;
    }
    public boolean contains (String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }
    public Cidade get(String key){
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        for (No x = Sentinela.prox; x!= Sentinela; x = x.prox){
            if (key.equals(x.chave))
                return x.dado;
        }
        return null;
    }
    public void delete (String key){
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        delete(Sentinela.prox, key);
    }
    private void remove (No tmp){
        tmp.ant.prox = tmp.prox;

        tmp.prox.ant = tmp.ant;
        --n;
    }
    private void delete (No x, String key){
        if (x == Sentinela) return;
        if (key.equals(x.chave)){
            remove(x);
            return;
        }
        delete (x.prox, key);
    }
    public void put (String key, Cidade val){
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null){
            delete(key);
            return;
        }
        for (No x = Sentinela.prox; x != Sentinela; x = x.prox){
            if (key.equals(x.chave)){
                x.dado = val;
                return;
            }
        }
        push_front(key, val);
    }
    public Cidade pop_front(){
        No tmp = Sentinela.prox;
        Cidade meuDado = tmp.dado;

        tmp.ant.prox = tmp.prox;
        tmp.prox.ant = tmp.ant;
        --n;
        return meuDado;
    }
    public Cidade pop_back(){
        No tmp = Sentinela.ant;
        Cidade meuDado = tmp.dado;

        tmp.ant.prox = tmp.prox;

        tmp.prox.ant = tmp.ant;
        --n;
        return meuDado;
    }
    public No first(){
        if (Sentinela == Sentinela.prox) return null;
        return Sentinela.prox;
    }
    public boolean isEmpty(){
        return n==0;
    }
    public int size(){
        return n;
    }
    public ListIterator<Cidade> iterator(){
        return new DequeIterator();
    }
    public class DequeIterator implements ListIterator<Cidade> {
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

        public Cidade next() {
            if (!hasNext()) return null;

            Cidade meuDado = atual.dado;
            acessadoultimo = atual;
            atual = atual.prox;
            indice++;
            return meuDado;

        }

        public Cidade previous() {
            if (!hasPrevious()) return null;
            atual = atual.ant;
            Cidade meuDado = atual.dado;
            acessadoultimo = atual;
            indice--;
            return meuDado;

        }

        public Cidade get() {
            if (atual == null) throw new IllegalStateException();
            return atual.dado;
        }

        public void set(Cidade x) {
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

        public void add(Cidade x) {
            //Inserir apos atual
            No tmp = new No();
            tmp.dado = x;

            tmp.prox = atual.prox;
            tmp.ant = atual;

            tmp.prox.ant = tmp;
            atual.prox = tmp;
            n++;
        }
    }

    public String toString() {

        StringBuilder s = new StringBuilder();
        for (Cidade item : this)
            s.append(item + " ");
        return s.toString();
    }

    public Iterable<String> keys() {

        DequeString queue = new DequeString();

        for (No x = Sentinela.prox; x != Sentinela; x = x.prox)
            queue.push_back(x.chave);
        return queue;

    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("\n\nUso: java DequeCity arquivo-1 arquivo-2\n\n");
            System.exit(0);
        }
        try {

            FileReader inl = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(inl);
            int total = Integer.parseInt(br.readLine());

            int temperature = 0;

            System.out.println(" Total = " + total);

            DequeCity st = new DequeCity();

            for (int i = 0; i < total; i++) {
                String tmp = br.readLine();
                StringTokenizer tk = new StringTokenizer(tmp);
                String key = tk.nextToken();
                temperature = Integer.parseInt(tk.nextToken());
                Cidade myCity = new Cidade(key, temperature);
                st.put(key, myCity);
            }

            br.close();
            inl.close();

            System.out.println("-----Testando--- Procure afterword");
            System.out.println(st.get("afternvord"));
            System.out.println("-----Testando--- Procure Feeney");
            System.out.println(st.get("Feeney"));
            System.out.println("-----Testando--- Procure Fee");
            System.out.println(st.get("Fee"));

            inl = new FileReader(args[1]);
            br = new BufferedReader(inl);

            total = Integer.parseInt(br.readLine());

            for (int i = 0; i < total; i++) {
                String tmp = br.readLine();
                StringTokenizer tk = new StringTokenizer(tmp);
                Cidade myCity = st.get(tk.nextToken());

                if (myCity == null) System.out.print("An[Failed] " + tmp + " não foi encontrada.");
                else {
                    System.out.println("\n[Ok] " + myCity.get_nome() + " foi encontrada. Temperatura lá é: " + myCity.get_temp() + "F");
                }
            }
            br.close();
            inl.close();

            System.out.println(st.keys());

        } catch (IOException e) {

        }

        //*/

    }
}
