/** ***********************************************************************
 *  Compilation:  javac LinkedListST.java
 *  Execution:    java LinkedListST
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/31elementary/tinyST.txt  
 *  
 *  Symbol table implementation with an ordered linked list.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *  
 *  % java LinkedListST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 *************************************************************************/

// The StdIn class provides static methods for reading strings and numbers from standard input.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdIn.html
import edu.princeton.cs.algs4.StdIn; 

// This class provides methods for printing strings and numbers to standard output.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://algs4.cs.princeton.edu/code/javadoc/edu/princeton/cs/algs4/StdOut.html
import edu.princeton.cs.algs4.StdOut; 

// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html 
// http://codereview.stackexchange.com/questions/48109/simple-example-of-an-iterable-and-an-iterator-in-java
import java.util.Iterator;

import edu.princeton.cs.algs4.In;

/** This is an implementation of a symbol table whose keys are comparable.
 * The keys are kept in increasing order in an linked list.
 * Following our usual convention for symbol tables, 
 * the keys are pairwise distinct.
 * <p>
 * For additional documentation, see 
 * <a href="http://algs4.cs.princeton.edu/31elementary/">Section 3.1</a> 
 * of "Algorithms, 4th Edition" (p.378 of paper edition), 
 * by Robert Sedgewick and Kevin Wayne.
 *
 */

public class LinkedListST<Key extends Comparable<Key>, Value> {
    // atributos de estado
    int size;
    Node head;
    Node tail;

    private class Node {
	Key key;
	Value value;
	Node next;
    }
    
    /** Constructor.
     * Creates an empty symbol table with default initial capacity.
     */
    public LinkedListST() {
        // escreva seu método a seguir
	head = new Node();
    }   

    /** Is the key in this symbol table?
     */
    public boolean contains(Key key) {
        // escreva seu método a seguir
	for (Node i = head; i != null; i = i.next)
	    if (i.key.compareTo(key) == 0)
		return true;

	return false;
    }

    /** Returns the number of (key,value) pairs in this symbol table.
     */
    public int size() {
        // escreva seu método a seguir
	return size;
    }

    /** Is this symbol table empty?
     */
    public boolean isEmpty() {
        // escreva seu método a seguir
	return size == 0;
    }

    /** Returns the value associated with the given key, 
     *  or null if no such key.
     *  Argument key must be nonnull.
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        // escreva seu método a seguir
	for (Node i = head; i != null; i = i.next) {
	    if (i.key.compareTo(key) == 0) {
		return i.value;
	    }
	}
	return null;
    } 
    
    /** Returns the number of keys in the table 
     *  that are strictly smaller than the given key.
     *  Argument key must be nonnull.
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        // escreva seu método a seguir
	int smaller = 0;

	for (Node i = head; i != null && i.key.compareTo(key) < 0; i = i.next)
	    smaller++;

	return smaller;
    } 

    
    /** Search for key in this symbol table. 
     * If key is in the table, update the corresponing value.
     * Otherwise, add the (key,val) pair to the table.
     * Argument key must be nonnull.
     * If argument val is null, the key must be deleted from the table.
     */
    public void put(Key key, Value val)  {
        if (key == null) throw new IllegalArgumentException("argument to put() is null");
       
        // escreva seu método a seguir
	Node temporary = null;
	Node recent = null;
	Node i;
	
	if (head.key == null) {
	    head.key = key;
	    head.value = val;
	    tail = head;
	    return;
	}

	for (i = head; i != null && i.key.compareTo(key) <= 0; i = i.next) {
	    if (i.key.compareTo(key) == 0) {
		i.value = val;
		return;
	    }
	    
	    temporary = i;
	}
	
       
       
	recent = new Node();
	recent.key = key;
	recent.value = val;
	size++;

	if (temporary != null) {
	    temporary.next = recent;
	    recent.next = i;
	}

	else {
	    temporary = head.next;
	    head = recent;
	    head.next = temporary;
	}

	while (tail.next != null) tail = tail.next;

	return;
	
    }

    /** Remove key (and the corresponding value) from this symbol table.
     * If key is not in the table, do nothing.
     */
    public void delete(Key key)  {
        if (key == null) throw new IllegalArgumentException("argument to put() is null");
        // escreva seu método a seguir
	Node temporary = null;
	for (Node i = head; i != null; i = i.next) {
	    if (i.key.compareTo(key) == 0) {
		temporary.next = i.next;
		i = null;
		return;
	    }
	    
            temporary = i;
	}
	
    } 

    /** Delete the minimum key and its associated value
     * from this symbol table.
     * The symbol table must be nonempty.
     */
    public void deleteMin() {
        if (isEmpty()) throw new java.util.NoSuchElementException("deleteMin(): Symbol table underflow error");
        // escreva seu método a seguir
	Node temporary = head;
	head = head.next;
	temporary = null;
    }

    /** Delete the maximum key and its associated value
     * from this symbol table.
     */
    public void deleteMax() {
        if (isEmpty()) throw new java.util.NoSuchElementException("deleteMax(): Symbol table underflow error");
        // escreva seu método a seguir
	Node temporary = null;
	for (Node i = head; i != null; i = i.next) {
	    if (i.next == null) {
		temporary.next = i.next;
		tail = temporary;
		i = null;
	    }
	    temporary = i;
	}
    }

    

   /***************************************************************************
    *  Ordered symbol table methods
    **************************************************************************/

    /** Returns the smallest key in this table.
     * Returns null if the table is empty.
     */
    public Key min() {
        // escreva seu método a seguir
	return head.key;
    }

   
    /** Returns the greatest key in this table.
     * Returns null if the table is empty.
     */
    public Key max() {
        // escreva seu método a seguir
	return tail.key;
    }

    /** Returns a key that is strictly greater than 
     * (exactly) k other keys in the table. 
     * Returns null if k < 0.
     * Returns null if k is greater that or equal to 
     * the total number of keys in the table.
     */
    public Key select(int k) {
        // escreva seu método a seguir
	if (k < 0 || k > size) return null;

	Node temporary = head;
	
	for (int i = 0; i <= k; i++) {
	    temporary = temporary.next;
	}

	return temporary.next;
    }

    /** Returns the greatest key that is 
     * smaller than or equal to the given key.
     * Argument key must be nonnull.
     * If there is no such key in the table
     * (i.e., if the given key is smaller than any key in the table), 
     * returns null.
     */
    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        // escreva seu método a seguir
	Node temporary = null;
	
	for (Node i = head; i.key.compareTo(key) < 0; i = i.next) {
	    temporary = i;
	}

	if (i.key.compareTo(key) == 0) {
	    return i;
	}

	return temporary;
    }

    /** Returns the smallest key that is 
     * greater than or equal to the given key.
     * Argument key must be nonnull.
     * If there is no such key in the table
     * (i.e., if the given key is greater than any key in the table), 
     * returns null.
     */
    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        // escreva seu método a seguir
	Node temporary = null;
	
	for (Node i = head; i.key.compareTo(key) < 0; i = i.next) {
	    temporary = i;
	}

	if (i.key.compareTo(key) == 0) {
	    return i;
	}

	return temporary.next;
	
    }

    /** Returns all keys in the symbol table as an Iterable.
     * To iterate over all of the keys in the symbol table named st, use the
     * foreach notation: for (Key key : st.keys()).
     */
    public Iterable<Key> keys() {
        return new ListKeys();
    }

    /**
     * implements Iterable<Key> significa que essa classe deve 
     * ter um método iterator(), acho...
     */
    private class ListKeys implements Iterable<Key> {
        /** 
         * Devolve um iterador que itera sobre os itens da ST 
         * da menor até a maior chave.<br>
         */
        public Iterator<Key> iterator() {
            return new KeysIterator();
        }
        
        private class KeysIterator implements Iterator<Key> {
            // variáveis do iterador
	    Node iterator = head;
            
            public boolean hasNext() {
                // escreva seu método a seguir
		if (iterator != null)
		    return iterator.next != null;
            }

            public Key next() {
                // escreva seu método a seguir
		Node save = null;
		if (iterator != null) {
		    save = iterator;
		    iterator = iterator.next;
		}
		return save.key;
            }
                    
            public void remove() { 
                throw new UnsupportedOperationException(); 
            }
        }
    }

    public void print () {
	for (Node i = head; i != null; i = i.next)
	    StdOut.println(i.key);
    }
    

   /** Test client.
    * Reads a sequence of strings from the standard input.
    * Builds a symbol table whose keys are the strings read.
    * The value of each string is its position in the input stream
    * (0 for the first string, 1 for the second, and so on).
    * Then prints all the (key,value) pairs.
    */
    public static void main(String[] args) { 
        LinkedListST<String, Integer> st;
        st = new LinkedListST<String, Integer>();
	String comando, palavra;
	In arquivo = new In(args[0]);
	for (int i = 0; !arquivo.isEmpty(); i++) {
            String key = arquivo.readString();
            st.put(key, i);
        }
	
	while (!StdIn.isEmpty()) {
               comando = StdIn.readString();
	
	       switch (comando) {
	           case "show":
		       st.print();
		       break;
	           case "delete":
		       palavra = StdIn.readString();
		       if (st.contains(palavra)) {
		           st.delete(palavra);
		       }
		       break;
	           case "contains":
		       palavra = StdIn.readString();
		       StdOut.println(st.contains(palavra));
		       break;
	           case "size":
		       StdOut.println(st.size());
		       break;
	           default:
		       StdOut.println(st.get(comando));
	       }
	 }
    }
}
