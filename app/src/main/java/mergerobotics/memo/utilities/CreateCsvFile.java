package mergerobotics.memo.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import mergerobotics.memo.backend.App;
import mergerobotics.memo.db.EventsDbAdapter;

import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_COMPETITION;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_END_TIME;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_EXTRA;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_MATCH;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_SCOUT_NAME;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_SCOUT_TEAM;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_SIGNATURE;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_START_TIME;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_SUCCESS;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_SYNC_TIME;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_TEAM;
import static mergerobotics.memo.db.EventsContract.EventsEntry.COLUMN_NAME_TYPE;

public class CreateCsvFile {
 /**
 * Creates a csv file using the data collected from matches
 * Adapted from MCMergeManager
 */

    private CreateCsvFile() {
        // Private constructor because everything should be static
    }

    private void writeCell(StringBuilder sb, String o) {
        sb.append(o);
        sb.append(",");
    }
    private void writeCell(StringBuilder sb, int o) {
        sb.append(o);
        sb.append(",");
    }
    private void writeCell(StringBuilder sb, double o) {
        sb.append(o);
        sb.append(",");
    }

    public String createEventsCsvString(Context context) {

        StringBuilder sb = new StringBuilder();
        // Add the header columns
        writeCell(sb, COLUMN_NAME_SYNC_TIME);
        writeCell(sb, COLUMN_NAME_TYPE);
        writeCell(sb, COLUMN_NAME_TEAM);
        writeCell(sb, COLUMN_NAME_MATCH);
        writeCell(sb, COLUMN_NAME_COMPETITION);
        writeCell(sb, COLUMN_NAME_SUCCESS);
        writeCell(sb, COLUMN_NAME_START_TIME);
        writeCell(sb, COLUMN_NAME_END_TIME);
        writeCell(sb, COLUMN_NAME_EXTRA);
        writeCell(sb, COLUMN_NAME_SCOUT_NAME);
        writeCell(sb, COLUMN_NAME_SCOUT_TEAM);
        writeCell(sb, COLUMN_NAME_SIGNATURE);
        sb.append("\n");

        EventsDbAdapter eDB = new EventsDbAdapter(context);
        eDB.open();
        String eventsData = eDB.getDataInCsvString();

        sb.append(eventsData);
        // Print all the stuff
//        for(int i = 0; i < teamArray.length(); i++) {
//            try {
//                TeamStatsReport teamStatsReport = statsEngine.getTeamStatsReport(Integer.parseInt(((String) teamArray.get(i)).substring(3)), false);
//
//                writeCell(sb, teamStatsReport.teamNumber);
//                writeCell(sb, teamStatsReport.OPR);
//                writeCell(sb, teamStatsReport.DPR);
//                writeCell(sb, teamStatsReport.CCWM);
//                writeCell(sb, teamStatsReport.scheduleToughnessByOPR);
//                writeCell(sb, teamStatsReport.wins);
//                writeCell(sb, teamStatsReport.losses);
//                writeCell(sb, teamStatsReport.ties);
//                writeCell(sb, teamStatsReport.autoPickupGround);
//                writeCell(sb, teamStatsReport.autoTotalPlacedCubes);
//                writeCell(sb, teamStatsReport.autoCrossedLine);
//                writeCell(sb, teamStatsReport.autoMalfunction);
//                writeCell(sb, teamStatsReport.pickupPortalAvgMatch);
//                writeCell(sb, teamStatsReport.pickupGroundAvgMatch);
//                writeCell(sb, teamStatsReport.pickupExchangeAvgMatch);
//                writeCell(sb, teamStatsReport.placeScaleAvgMatch);
//                writeCell(sb, teamStatsReport.placeSwitchAvgMatch);
//                writeCell(sb, teamStatsReport.placeExchangeAvgMatch);
//                writeCell(sb, teamStatsReport.totalFumbles);
//                writeCell(sb, teamStatsReport.totalEasyDrop);
//                writeCell(sb, teamStatsReport.totalLeftIt);
//                writeCell(sb, teamStatsReport.switchAvgCycleTime);
//                writeCell(sb, teamStatsReport.scaleAvgCycleTime);
//                writeCell(sb, teamStatsReport.exchangeAvgCycleTime);
//                writeCell(sb, teamStatsReport.droppedAvgCycleTime);
//                writeCell(sb, teamStatsReport.avgDeadness);
//                writeCell(sb, teamStatsReport.highestDeadness);
//                writeCell(sb, (teamStatsReport.numMatchesPlayed - teamStatsReport.numMatchesNoDeadness));
//                writeCell(sb, teamStatsReport.avgClimbTime);
//                writeCell(sb, teamStatsReport.noClimb);
//                writeCell(sb, teamStatsReport.failClimb);
//                writeCell(sb, teamStatsReport.independentClimb);
//                writeCell(sb, teamStatsReport.assistedClimb);
//                writeCell(sb, teamStatsReport.assistedOthersClimb);
//                writeCell(sb, teamStatsReport.onBase);
//                writeCell(sb, teamStatsReport.avgTimeDefending);
//                writeCell(sb, teamStatsReport.maxTimeDefending);
//
//                // New line
//                sb.append("\n");
//            } catch(JSONException e) {
//                e.printStackTrace();
//            }
//        }

        return sb.toString();
    }

    public  void saveEventsCsvFile(Context context, String filename) {
        double startTime = System.currentTimeMillis();

        // Create the file
        File file = new File(FileUtils.sLocalToplevelFilePath + "/" + filename);

        try {
            (new File(file.getParent())).mkdirs();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            String fileString = (String) createEventsCsvString(context);
            bw.write(fileString);

            bw.close();

            // Force the media scanner to scan this file so it shows up from a PC over USB.
            App.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        } catch(IOException e) {
            Log.d("Error saving csv", e.toString());
        }

        System.out.println("Total time: " + (System.currentTimeMillis() - startTime));
    }
}
