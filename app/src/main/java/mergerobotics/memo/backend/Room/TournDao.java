package mergerobotics.memo.backend.Room;
import android.arch.persistence.room.ColumnInfo;
import mergerobotics.memo.backend.Room.StatsDatabase;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.*;

@Dao
public interface TournDao {

    @Query("select * from competitions where year=2018 order by competition asc")
    ArrayList<String> getAllTourn();

}
