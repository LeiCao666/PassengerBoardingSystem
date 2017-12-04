import java.util.Comparator;

/**
 * Created by caolei on 11/26/17.
 */
public class PassengerComparator implements Comparator<Passenger>{

    @Override
    public int compare(Passenger p1, Passenger p2) {
        if(p1.getPriority()<p2.getPriority())
            return -1;
        else if (p1.getPriority()==p2.getPriority()) {
            /*
                This if-else statement is to ensure first-come-first-serve rule
                if the two passengers' priorities are the same, by further comparing
                their arrival time. If p1's arrival time is before p2's, return -1.
                If p1's arrival time is after p2's, return 1.
                We don't consider the possibility of same arrival time, which is not required
                in this project.
             */
            if (p1.getArrivalTime().compareTo(p2.getArrivalTime()) < 0)
                return -1;
            else if (p1.getArrivalTime().compareTo(p2.getArrivalTime()) > 0)
                return 1;
        }
        else
            return 1;
        return 0; /* "return 0" is added only to make the method complete,
                     value 0 is not used in this project*/
    }
}
