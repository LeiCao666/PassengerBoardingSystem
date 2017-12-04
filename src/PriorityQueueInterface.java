/**
 * Created by caolei on 11/25/17.
 */
public interface PriorityQueueInterface {
    boolean enqueue(Passenger newItem);
    Passenger dequeue();
    Passenger peek();
    int size();

}
