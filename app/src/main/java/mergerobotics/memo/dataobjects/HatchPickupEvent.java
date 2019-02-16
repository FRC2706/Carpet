package mergerobotics.memo.dataobjects;

/**
 * Created by Merge on 2019-02-16.
 */

public class HatchPickupEvent extends Event {

    public static final String ID = "pickup_hatch";

    public enum PickupType {
        PRELOADED, GROUND, LOADING_STATION
    }

    public PickupType pickupType;

    public HatchPickupEvent() {
        // Empty Constructor
    }

    public HatchPickupEvent(double timestamp, PickupType PickupType){
        super(timestamp);
        this.pickupType = PickupType;

    }

}
