import java.util.Comparator;

/**
 * Created by caolei on 11/25/17.
 */
public class LeiPriorityQueue implements PriorityQueueInterface {
    private Passenger[] theData; // using circular array is not necessary for Priority Queue
    private int capacity;
    private int size;
    private final int FIRST_NAME_SUBSTRING_END_INDEX = 1;
    private Comparator comp;

    // L. child position: 2p + 1
    // R. child position: 2p + 2

    public LeiPriorityQueue(int initCapacity, Comparator comp) {
        this.comp = comp;
        this.capacity = initCapacity;
        this.theData = new Passenger[this.capacity];
        this.size = 0;
    }

    @Override
    public boolean enqueue(Passenger p) {
        if (size==capacity)
            reallocate();
        theData[size] = p;
        int pos = size;
        size++;
        int parent = 0;
        // the <= logic operator rather than < is to ensure equal priority follow first-come-first serve
        while (pos != 0 && (comp.compare(p, theData[parent = (pos - 1) / 2]) < 0)) {
                theData[pos] = theData[parent];
                pos = parent ;
        }
        theData[pos] = p;
        return true;
    }


    @Override
    public Passenger dequeue() {
        if (this.size == 0) {
            System.out.println("The heap is empty");
            return null;
        }
        int parent, child;
        Passenger temp;
        Passenger root = theData[0]; // save the old root, will return at the end of this method
        theData[0] = theData[size-1]; // replacing the root with the Last In the Heap (LIH)
        //temp = theData[size-1];
        parent = 0;
        child = 2 * parent + 1; // the left child
        size--;

        // the first two conditions is to ensure that the parent has children
        while (child < size) { // check the left child first. If it is null, the right child will be null too.
            if (child + 1 < size) {

                if (comp.compare(theData[parent],theData[child])>0 || comp.compare(theData[parent],theData[child+1])>0) {
                    if (comp.compare(theData[child], theData[child + 1]) < 0) {
                        temp = theData[parent];
                        theData[parent] = theData[child];
                        theData[child] = temp;
                        parent = child;
                        child = 2 * parent + 1;
                    } else {
                        temp = theData[parent];
                        theData[parent] = theData[child + 1];
                        theData[child+1] = temp;
                        parent = child + 1;
                        child = 2 * parent + 1;
                    }
                } else if (comp.compare(theData[parent],theData[child])<0 && comp.compare(theData[parent],theData[child+1])<0){
                    break;
                }
            } else {
                if (comp.compare(theData[parent], theData[child]) > 0) {
                    temp = theData[parent];
                    theData[parent] = theData[child];
                    theData[child] = temp;
                    parent = child;
                    child = 2 * parent + 1;
                    //break;
                } else {
                    break;
                }
            }
        }
        System.out.println(root);
        return root;
    }

    @Override
    public Passenger peek() {
        if (size!=0)
            return theData[0];
        else {
            System.out.println("the queue is empty!");
            return null;
        }
    }

    @Override
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }
    private void reallocate(){
        int newCapacity = 2 * capacity;
        Passenger[] newData = new Passenger[newCapacity];
        System.arraycopy(theData, 0, newData, 0, this.size);
        capacity = newCapacity;
        theData = newData;
    }

//// The following method is for debug purpose, it will print the theData array of priority.
//    public void printAll() {
//        System.out.print("[");
//        for (int i = 0; i < size; i ++) {
//            if (i!=size-1)
//                System.out.print((theData[i].getPriority()<10 ? "0" + theData[i].getPriority(): theData[i].getPriority())+ "(" + theData[i].getFirstName().substring(0,FIRST_NAME_SUBSTRING_END_INDEX) + "),");
//            else
//                System.out.print((theData[i].getPriority()<10 ? "0" + theData[i].getPriority(): theData[i].getPriority()) + "(" + theData[i].getFirstName().substring(0,FIRST_NAME_SUBSTRING_END_INDEX)+")");
//
//        }
//        System.out.println("]");
//    }
}
