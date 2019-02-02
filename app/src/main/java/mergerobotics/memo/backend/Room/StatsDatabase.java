package mergerobotics.memo.backend.Room;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Room;
import mergerobotics.memo.backend.Room.CompetitionsEntity;

@Database(entities = {CompetitionsEntity.class}, version = 1)
public abstract class StatsDatabase extends RoomDatabase{



}
