import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by caolei on 11/26/17.
 */
public class BoardingSystem {
    private PassengerComparator comparator;
    private LeiPriorityQueue priorityQ;
    private final LocalTime START_BOARD_TIME = LocalTime.of(15,30,00);
    private Queue<Passenger> arrivalOrderQ;
    private LocalTime arrivalTimeTracker;
    private final int ARRIVAL_TIME_INTERVAL = 40;
    private final int BOARD_TIME_INTERVAL = 20;
    private LocalTime simulPresentTime; // This variable is to simulate the real present time
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private int twentySecBoardingRecorder; // This variable is to ensure the boarding time duration is 20 seconds.
    private String boardTimeInFormat; // This string is used as a format to print LocalTime object
    private LocalTime timeWhenLastPassengerBoard; // This LocalTime object is to record the time when the last passenger's done boarding.
    private Passenger lastPassengerHolder;

    public BoardingSystem() {

        twentySecBoardingRecorder = BOARD_TIME_INTERVAL;
        simulPresentTime = LocalTime.of(15,25,00);
        arrivalTimeTracker = START_BOARD_TIME.minusSeconds(ARRIVAL_TIME_INTERVAL * 7); // the first 7 passenger arrive before START_BOARD_TIME
        comparator = new PassengerComparator();
        priorityQ = new LeiPriorityQueue(10, comparator);
        // the arrivalOrderQ is to simulate the one-by-one arrival sequence
        arrivalOrderQ = new LinkedList<>();
        arrivalOrderQ.add(new Passenger("a's", "name", Passenger.ECO_CLASS, "Nov 12, 1984",
                arrivalTimeTracker));
        arrivalOrderQ.add(new Passenger("b's", "name", Passenger.FIRST_CLASS, "Sep 20, 1970",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));
        arrivalOrderQ.add(new Passenger("c's", "name", Passenger.ECO_CLASS, "May 2, 1990",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));
        arrivalOrderQ.add(new Passenger("d's", "name", Passenger.ARMED_SERVICES, "Jul 18, 1994",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));
        arrivalOrderQ.add(new Passenger("e's", "name", Passenger.ELDERLY, "Jan 5, 1950",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));
        arrivalOrderQ.add(new Passenger("f's", "name", Passenger.TRAVEL_WITH_INFANT, "Aug 16, 1986",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));
        arrivalOrderQ.add(new Passenger("g's", "name", Passenger.ELDERLY, "Jan 5, 1950",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));
        arrivalOrderQ.add(new Passenger("h's", "name", Passenger.TRAVEL_WITH_INFANT, "Nov 22, 1984",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));
        arrivalOrderQ.add(new Passenger("i's", "name", Passenger.ARMED_SERVICES, "Sep 21, 1970",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));
        arrivalOrderQ.add(new Passenger("j's", "name", Passenger.ECO_CLASS, "May 6, 1990",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));
        arrivalOrderQ.add(new Passenger("k's", "name", Passenger.FIRST_CLASS, "Jul 19, 1994",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));
        arrivalOrderQ.add(new Passenger("l's", "name", Passenger.ELDERLY, "Jan 6, 1950",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));
        arrivalOrderQ.add(new Passenger("m's", "name", Passenger.ECO_CLASS, "Aug 17, 1986",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));
        arrivalOrderQ.add(new Passenger("n's", "name", Passenger.ELDERLY, "Jan 6, 1950",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));
        arrivalOrderQ.add(new Passenger("o's", "name", Passenger.ELDERLY, "Nov 13, 1984",
                arrivalTimeTracker = arrivalTimeTracker.plusSeconds(ARRIVAL_TIME_INTERVAL)));

        printArrivalList();
    }

    public void start() {
        System.out.println("10 Mins boarding window begins at " + simulPresentTime.format(formatter));
        System.out.println("**************************************");
        for (int sec = 0; sec < 600; sec++){ //simulating 10 mins(600 sec) covering from the first passenger arrives to the last passenger gets on board.
            simulPresentTime = simulPresentTime.plusSeconds(1);
            boardTimeInFormat = simulPresentTime.format(formatter);
            // when it's start-boarding time, dequeue those already in priorityQ
            if (simulPresentTime.compareTo(START_BOARD_TIME) >= 0)
                dequeueAndBoard();
            // when the the passenger arrives at his/her arrival time, enqueue to priorityQ
            passengerArriveAndEnqueue();
        }
        System.out.println("**************************************");
        System.out.println("10 Mins boarding window ends at " + simulPresentTime.format(formatter));
        System.out.println("\nAll Passengers arrive by " + lastPassengerHolder.getArrivalTime().format(formatter));
        System.out.println("Last Passenger starts boarding process at " + timeWhenLastPassengerBoard.minusSeconds(BOARD_TIME_INTERVAL).format(formatter));
        System.out.println("Last Passenger finishes boarding process at " + timeWhenLastPassengerBoard.format(formatter) );

    }

    private void passengerArriveAndEnqueue() {
        if (!arrivalOrderQ.isEmpty() && simulPresentTime.compareTo(arrivalOrderQ.peek().getArrivalTime()) >= 0)
            priorityQ.enqueue(arrivalOrderQ.poll()); // poll from arrivalOrderQ and enqueue to priorityQ
    }

    private void dequeueAndBoard(){
        if (twentySecBoardingRecorder==BOARD_TIME_INTERVAL) {
            if(!priorityQ.isEmpty()) {
                printBoardTime();
                lastPassengerHolder = priorityQ.dequeue();
                timeWhenLastPassengerBoard = simulPresentTime.plusSeconds(BOARD_TIME_INTERVAL);
                twentySecBoardingRecorder = -1; // it will become 0 after incrementing at the end of this method
            } else {
                twentySecBoardingRecorder = BOARD_TIME_INTERVAL - 1; // it will become BOARD_TIME_INTERVAL after incrementing at the end of this method
            }
        }
        twentySecBoardingRecorder++;
    }
    private void printBoardTime() {
        System.out.print("boarding time: " + boardTimeInFormat + "| ");
    }

    public void printArrivalList() {
        System.out.println("This is passengers arrival time records: ");
        System.out.println("**************************************");
        String formatDateTime;
        String priority;
        for (Passenger p : arrivalOrderQ) {
            System.out.println(p);
        }
        System.out.println("**************************************\n\n");
    }


}
