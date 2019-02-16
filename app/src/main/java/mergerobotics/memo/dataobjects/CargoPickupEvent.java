package mergerobotics.memo.dataobjects;

/**
 * Created by Merge on 2019-02-16.
 */

public class CargoPickupEvent extends Event {

    public static final String ID = "pickup_hatch";

    public enum PickupType {
        PRELOADED, GROUND, DEPOT, LOADING_STATION
    }

    public PickupType pickupType;

    public CargoPickupEvent() {
        // Empty Constructor
    }

    public CargoPickupEvent(double timestamp, PickupType PickupType){
        super(timestamp);
        this.pickupType = PickupType;

    }

}
