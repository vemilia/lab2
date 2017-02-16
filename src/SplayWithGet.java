import java.util.NoSuchElementException;

/**
 * Created by Emilia on 2017-02-13.
 */
public class SplayWithGet<E extends Comparable<?
        super E>> extends BinarySearchTree<E> implements CollectionWithGet<E>{

    /* Rotera 1 steg i hogervarv, dvs
               x'                 y'
              / \                / \
             y'  C   -->        A   x'
            / \                    / \
           A   B                  B   C
     */


    public SplayWithGet(){
        super();
    }

    public void rotateRight( Entry x ) {
        Entry   y = x.left;
        E temp = x.element;
        x.element = y.element;
        y.element = temp;
        x.left    = y.left;
        if ( x.left != null )
            x.left.parent   = x;
        y.left    = y.right;
        y.right   = x.right;
        if ( y.right != null )
            y.right.parent  = y;
        x.right   = y;

    } //   rotateRight
    // ========== ========== ========== ==========

    /* Rotera 1 steg i vanstervarv, dvs
              x'                 y'
             / \                / \
            A   y'  -->        x'  C
               / \            / \
              B   C          A   B
    */
    public void rotateLeft( Entry x ) {
        Entry  y  = x.right;
        E temp    = x.element;
        x.element = y.element;
        y.element = temp;
        x.right   = y.right;
        if ( x.right != null )
            x.right.parent  = x;
        y.right   = y.left;
        y.left    = x.left;
        if ( y.left != null )
            y.left.parent   = y;
        x.left    = y;

    } //   rotateLeft
    // ========== ========== ========== ==========

    /* Rotera 2 steg i hogervarv, dvs
              x'                  z'
             / \                /   \
            y'  D   -->        y'    x'
           / \                / \   / \
          A   z'             A   B C   D
             / \
            B   C
    */
    public void doubleRotateRight( Entry x ) {
        Entry   y = x.left,
                z = x.left.right;
        E       e = x.element;
        x.element = z.element;
        z.element = e;
        y.right   = z.left;
        if ( y.right != null )
            y.right.parent = y;
        z.left    = z.right;
        z.right   = x.right;
        if ( z.right != null )
            z.right.parent = z;
        x.right   = z;
        z.parent  = x;

    }  //  doubleRotateRight
    // ========== ========== ========== ==========

    /* Rotera 2 steg i vanstervarv, dvs
               x'                  z'
              / \                /   \
             A   y'   -->       x'    y'
                / \            / \   / \
               z   D          A   B C   D
              / \
             B   C
     */
    public void doubleRotateLeft( Entry x ) {
        Entry  y  = x.right,
                z  = x.right.left;
        E      e  = x.element;
        x.element = z.element;
        z.element = e;
        y.left    = z.right;
        if ( y.left != null )
            y.left.parent = y;
        z.right   = z.left;
        z.left    = x.left;
        if ( z.left != null )
            z.left.parent = z;
        x.left    = z;
        z.parent  = x;

    }

    //ZIGZIG
    public void rotateLeftTwice (Entry x){
        Entry y = x.left,
            z = x.left.left,
                C1 = z.left,
                C2 = z.right,
                C3 = y.right,
                C4 = x.right;

        E e = x.element;
        x.element = z.element;
        z.element = e;

        z.left = C1;
        if (C1 != null)
            C1.parent = z;

        y.left = C2;
        if (C2 != null)
            C2.parent = y;

        x.left = C3;
        if (C3 != null)
            C3.parent = z;

        x.right = C4;
        if(C4 != null)
            C4.parent = x;

        x.right = y;
        y.right = z;
    }
    //ZAGZAG
    public void rotateRightTwice (Entry x){
        Entry y = x.right,
                z = x.right.right,
                C1 = z.right,
                C2 = z.left,
                C3 = y.left,
                C4 = x.left;

        E e = x.element;
        x.element = z.element;
        z.element = e;

        z.right = C1;
        if (C1 != null)
            C1.parent = z;


        y.right = C2;
        if (C2 != null)
            C2.parent = y;


        x.right = C3;
        if (C3 != null)
            C3.parent = z;

        x.left = C4;
        if(C4 != null)
            C4.parent = x;

        x.left = y;
        y.left = z;
    }

    //element e with entry t
    public void splay (Entry t){
        if (t == null){
            throw new NullPointerException();
        }
        while (t != root) {

            if (t == t.parent.left) {
                if (t.parent.parent == null) {
                    rotateRight(t.parent);
                    t = t.parent;
                }

                else if (t.parent == t.parent.parent.left) {
                    rotateLeftTwice(t.parent.parent);
                    t = t.parent.parent;

                } else if (t.parent == t.parent.parent.right) {
                        doubleRotateLeft(t.parent.parent);
                        t = t.parent;
                    }

            } else if (t == t.parent.right){
                if (t.parent.parent == null) {
                    rotateLeft(t.parent);
                    t = t.parent;
                }

                else if (t.parent == t.parent.parent.left) {
                    doubleRotateRight(t.parent.parent);
                    t = t.parent;

                } else {
                    rotateRightTwice(t.parent.parent);
                    t = t.parent.parent;
                }
            }
        }
    }

    @Override
    //@throws NoSuchElementException
    public E get(E e) {
        if ( e == null)
            throw new NullPointerException("e is no");
        Entry t = find(e,root);
        return t == null ? null : t.element;
    }

    public Entry find( E elem, Entry t ) {
        if (t == null){
        return null;
        }
        if (elem == null){
            throw new NullPointerException("e i sno");
        }

        else {
            int jfr = elem.compareTo( t.element );

            if ( jfr  < 0 ) {
                if (t.left == null) {
                    splay(t);
                    return null;
                }
                return find(elem, t.left);
            }
            else if ( jfr > 0 ) {
                if (t.right == null) {
                    splay(t);
                    return null;
                }
                return find(elem, t.right);
            }
            else{
                if (t.parent != null){
                    splay (t);

                }
                return root;
            }
        }
    }

}
