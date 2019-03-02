package mergerobotics.memo.db;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {CompetitionsEntity.class}, version = 1, exportSchema = false)
public abstract class StatsDatabase extends RoomDatabase{

    public abstract TournDao tournDao();

}
