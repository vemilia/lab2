import com.sun.tools.javac.util.GraphUtils;

import java.util.NoSuchElementException;

/**
 * Created by Emilia on 2017-02-13.
 */
public class SLCWithGet<E extends Comparable<? super E>> extends LinkedCollection<E> implements CollectionWithGet<E>{


    public SLCWithGet(){
        super();
    }
    //@throws NullPointerException
    public boolean add (E element ) {

        if ( element == null ) {
            throw new NullPointerException();
        }

        Entry nya = new Entry (element, null);
        if (head == null){
            head = nya;
            return true;
        }

        if(element.compareTo(head.element) <= 0){
            nya.next = head;
            head = nya;
            return true;
        }

        else {
            Entry current;
            current = head;
            while (current.next != null && element.compareTo(current.next.element) > 0){
                current = current.next;
            }
            nya.next = current.next;
            current.next = nya;
            return true;

        }
    }

    //@throws NosuchelelementException
    public E get (E element){

        if ( element == null)
            throw new NoSuchElementException();

        Entry entry = head;
        while (entry != null){
            if (entry.element.equals(element)){
                return entry.element;
            }
            entry = entry.next;
        }
        return null;
    }
}


