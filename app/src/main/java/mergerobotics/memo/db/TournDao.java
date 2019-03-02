package mergerobotics.memo.db;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import java.util.*;

@Dao
public interface TournDao {

    @Query("select * from competitions where year=2018 order by competition asc")
    List<CompetitionsEntity> getAllTourn();

}
