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
    private int mId;

    @ColumnInfo(name = "competition")
    private String mCompName;

    @ColumnInfo(name = "year")
    private String mCompYear;

    public CompetitionsEntity(String Id, String CompName, String CompYear){
        this.mCompName = CompName;
    }
}
