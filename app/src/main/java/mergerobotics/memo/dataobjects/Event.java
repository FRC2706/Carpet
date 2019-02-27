package mergerobotics.memo.dataobjects;

import android.os.SystemClock;

import java.io.Serializable;

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

    // Used for pickup location, to be mapped to pickup in the events table
    public enum Pickup {
        NIL, PRELOADED, LOADINGSTN, DEPOT, GROUND,
    }

    // Used for delivery outcome, to be mapped to delivery in the events table
    public enum Delivery {
        NIL, CARGOSHIP, ROCKETLVL1, ROCKETLVL2, ROCKETLVL3, DROPPED
    }

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
    public Cycle eventType;
    public Pickup pickup;
    public Delivery delivery;
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
    public Event(Phase phase, Cycle eventType, int teamNum, int match, String competition,
                 int success, double startTime, double endTime, String comments,
                 String scout, int scoutingTeam) {
        this.timestamp = SystemClock.currentThreadTimeMillis();
        this.phase = phase;
        this.eventType = eventType;
        this.delivery = Delivery.NIL;
        this.pickup = Pickup.NIL;
        this.teamNum = teamNum;
        this.match = match;
        this.competition = competition;
        this.success = 0;
        this.startTime = initTimestamp;
        this.endTime = initTimestamp;
        this.extra = comments;
        this.scoutName = scout;
        this.scoutTeam = scoutingTeam;
        this.signature = "jpl"; //hardcoded text for now

    }

    public Event(double timestamp, Phase phase, Cycle cycle, Pickup pickup, Delivery deliveryOutcome,
                 int teamNum, int match, String competition,
                 int success, double startTime, double endTime, String comments,
                 String scout, int scoutingTeam) {
        this.timestamp = timestamp;
        this.phase = phase;
        this.eventType = cycle;
        this.delivery = deliveryOutcome;
        this.pickup = pickup;
        this.teamNum = teamNum;
        this.match = match;
        this.competition = competition;
        this.success = success;
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
