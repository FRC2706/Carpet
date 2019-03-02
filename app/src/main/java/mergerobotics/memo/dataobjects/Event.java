package mergerobotics.memo.dataobjects;

import android.os.SystemClock;

import java.io.Serializable;
import java.util.Date;

/**
 * This is an abstract class to represent any timestamped event during an FRC match.
 *
 * It implements Comparator to allow them to be sorted by timestamp.
 */
public class Event implements Comparable<Event>, Serializable {

    public enum Phase {
        PREMATCH, SAND, TELEOP, ENDGAME
    }

    // Used for event Type in the events table
    public enum Cycle {
        NIL, START, DRIVER, CARGO, HATCH, CLIMB, DEADNESS, OTHER
    }
// **** Not needed, using button labels
//    // Used for pickup location, to be mapped to pickup in the events table
//    public enum Pickup {
//        NIL, PRELOADED, LOADINGSTN, DEPOT, GROUND,
//    }
//
//    // Used for delivery outcome, to be mapped to delivery in the events table
//    public enum Delivery {
//        NIL, CARGOSHIP, ROCKETLVL1, ROCKETLVL2, ROCKETLVL3, DROPPED
//    }

    public double initTimestamp = 0;

    /** Data stored in the events table or used to create the Event for the db
        enums will be mapped to db definitions
            */
    // @TODO should do setters and getters
    public String scoutName;
    public int scoutTeam;
    public double timestamp;
    public int teamNum;
    public int match;
    public Phase phase;
    public String eventType;
    public String location;
    public String competition;
    public int success;
    public double startTime;
    public double endTime;
    public String extra;
    public String signature;


    /**
     * This version of Event constructor requires minimal parameters, to be
     * used primarily for non-cycle event data
     */
    public Event(Phase phase, String eventType, int teamNum, int match,
                 String comments, String scout, int scoutingTeam) {

        Date date = new Date();

        this.timestamp = SystemClock.currentThreadTimeMillis();
        this.phase = phase;
        this.eventType = eventType;
        this.location = "NIL";
        this.teamNum = teamNum;
        this.match = match;
        this.competition = date.toString();
        this.success = 1;
        this.startTime = this.timestamp;
        this.endTime = 0;
        this.extra = comments;
        this.scoutName = scout;
        this.scoutTeam = scoutingTeam;
        this.signature = "TODO"; //hardcoded text for now

    }

    public Event(double timestamp, Phase phase, String cycle, String location,
                 int teamNum, int match,
                 int success, double startTime, double endTime, String comments,
                 String scout, int scoutingTeam) {

        Date date = new Date();

        this.timestamp = timestamp;
        this.phase = phase;
        this.eventType = cycle;
        this.location = location;
        this.teamNum = teamNum;
        this.match = match;
        this.competition = date.toString(); // default to the date, could add to GUI
        this.success = success;
        this.startTime = startTime;
        this.endTime = endTime;
        this.extra = comments;
        this.scoutName = scout;
        this.scoutTeam = scoutingTeam;
        this.signature = "jpl"; //hardcoded text for now

    }

    public Event(double timestamp,
                 Phase phase, String cycle, String location,
                 int teamNum, int match,
                 double startTime, double endTime, String comments,
                 String scout, int scoutingTeam) {

        Date date = new Date();

        this.timestamp = timestamp;
        this.phase = phase;
        this.eventType = cycle;
        this.location = location;
        this.teamNum = teamNum;
        this.match = match;
        this.competition = date.toString(); // default to the date, could add to GUI
        this.success = 1; // not sure we need this, default to successful
        this.startTime = startTime;
        this.endTime = endTime;
        this.extra = comments;
        this.scoutName = scout;
        this.scoutTeam = scoutingTeam;
        this.signature = "jpl"; //hardcoded text for now

    }

    /**
     * Compare this event to another event and return -1,0,1 if this event started before, at the same time as, or after the other event.
     */
    @Override
    public int compareTo(Event e) {
        final int compare = Double.compare(this.timestamp, e.timestamp);
        return compare;
//        if (this.timestamp < e.timestamp)
//            return -1;
//        else if (this.timestamp > e.timestamp)
//            return 1;
//        else
//            return 0;
    }
}
