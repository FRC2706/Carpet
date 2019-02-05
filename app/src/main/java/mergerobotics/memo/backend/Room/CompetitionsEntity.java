package mergerobotics.memo.backend.Room;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.arch.persistence.room.Database;


@Entity(tableName = "competitions")
public class CompetitionsEntity {

    @NonNull

    @PrimaryKey
    public String mId;

    @ColumnInfo(name = "competition")
    public String mCompName;

    @ColumnInfo(name = "year")
    public String mCompYear;

    public CompetitionsEntity(String mId, String mCompName, String mCompYear){
        this.mCompName = mCompName;
        this.mCompYear = mCompYear;
        this.mId = mId;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(@NonNull String mId) {
        this.mId = mId;
    }

    public String getmCompName() {
        return mCompName;
    }

    public void setmCompName(String mCompName) {
        this.mCompName = mCompName;
    }

    public String getmCompYear() {
        return mCompYear;
    }

    public void setmCompYear(String mCompYear) {
        this.mCompYear = mCompYear;
    }

}