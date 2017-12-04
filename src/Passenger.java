import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by caolei on 11/26/17.
 */
public class Passenger {
    private String firstName;
    private String lastName;
    private int boardingClass;
    private String dateOfBirth;
    private int priority;
    private LocalTime arrivalTime;
    public static final int TRAVEL_WITH_INFANT = 1;
    public static final int ELDERLY = 5;
    public static final int ARMED_SERVICES = 10;
    public static final int FIRST_CLASS = 15;
    public static final int ECO_CLASS = 20;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    public Passenger(String firstName, String lastName, int boardingClass, String dateOfBirth, LocalTime arrivalTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.boardingClass = boardingClass;
        this.dateOfBirth = dateOfBirth;
        this.determinePriority();
        this.arrivalTime = arrivalTime;
    }

    private void determinePriority(){
        if (boardingClass==TRAVEL_WITH_INFANT)
            priority = this.TRAVEL_WITH_INFANT;
        else if (boardingClass==ELDERLY)
            priority = this.ELDERLY;
        else if (boardingClass==ARMED_SERVICES)
            priority = this.ARMED_SERVICES;
        else if (boardingClass==FIRST_CLASS)
            priority = this.FIRST_CLASS;
        else
            priority = this.ECO_CLASS;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPriority() {
        return priority;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public String getBoardingClassString(int BoardingClassInt){
        if (BoardingClassInt == Passenger.TRAVEL_WITH_INFANT)
            return "travel with infant";
        else if (BoardingClassInt == Passenger.ELDERLY)
            return "elderly";
        else if (BoardingClassInt == Passenger.ARMED_SERVICES)
            return "armed forces";
        else if (BoardingClassInt == Passenger.FIRST_CLASS)
            return "first class";
        else
            return "economy class";

    }
    @Override
    public String toString() {
        String name = "NAME:[" + this.firstName + " " + this.lastName + "]";
        String arrivalTime = "ARRV_TIME:[" + this.arrivalTime.format(formatter) + "]";
        String priority = "PRIORITY:[" + (this.priority<10 ? " " + this.priority : this.priority+"") + "]";
        String boardingClass = "CLASS:[" + this.getBoardingClassString(this.boardingClass) + "]";
        // Date Of Birth is considered confidential, thus it is not included in the return string
        return name + ", " + arrivalTime + ", " + priority + ", " + boardingClass;

    }
}
