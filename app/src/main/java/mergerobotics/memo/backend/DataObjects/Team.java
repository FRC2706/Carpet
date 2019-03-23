package mergerobotics.memo.backend.DataObjects;

import android.database.Cursor;

public class Team {
    public int number;
    public String name;
    public String pubkey;

    public static Team fromSQLCursor(Cursor c) {
        return new Team (
                c.getInt(c.getColumnIndex("team")),
                c.getString(c.getColumnIndex("name")),
                c.getString(c.getColumnIndex("public_key"))
        );
    }
    
    public Team(int number, String name, String pubkey)
    {
        this.number = number;
        this.name = name;
        this.pubkey = pubkey;
    }
}
