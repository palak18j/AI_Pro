/**
 * CSCI 2120 Fall 2014
 * Risk class PlayerQueue
 *
 * @author Shane McCulley
 * @date Dec 6, 2014
 **/
package classes;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;

public class ConcreteQueue<E> extends AbstractQueue<E> {
    private ArrayList<E> data;

    public ConcreteQueue() {
        data = new ArrayList<E>();
    }

    @Override
    public boolean offer(E element) {
        return (data.add(element));
    }

    @Override
    public E peek() {
        return data.get(0);
    }

    @Override
    public E poll() {
        return data.size() > 0 ? data.remove(0) : null;
    }

    @Override
    public Iterator<E> iterator() {
        return data.iterator();
    }


    @Override
    public int size() {
        return data.size();
    }

}
