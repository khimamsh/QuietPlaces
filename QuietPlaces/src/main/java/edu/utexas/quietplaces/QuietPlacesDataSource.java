package edu.utexas.quietplaces;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Data access object for QuietPlaces
 *
 * TODO: need 'read one by id' and 'update' methods ?
 */
public class QuietPlacesDataSource {

    private final static String TAG = Config.PACKAGE_NAME + ".QuietPlacesDataSource";

    // Database fields
    private SQLiteDatabase database;
    private QuietPlacesSQLiteHelper dbHelper;
    private String[] allColumns = {
            QuietPlacesSQLiteHelper.COLUMN_ID,
            QuietPlacesSQLiteHelper.COLUMN_COMMENT,
            QuietPlacesSQLiteHelper.COLUMN_LATITUDE,
            QuietPlacesSQLiteHelper.COLUMN_LONGITUDE,
            QuietPlacesSQLiteHelper.COLUMN_RADIUS,
            QuietPlacesSQLiteHelper.COLUMN_DATETIME,
            QuietPlacesSQLiteHelper.COLUMN_CATEGORY
    };

    public QuietPlacesDataSource(Context context) {
        dbHelper = new QuietPlacesSQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Save a QuietPlace object to the local database. This also returns a new instance
     * of the 'same' object with the id value filled in.
     *
     * @param quietPlace the place to save
     * @return a new copy of that place with the id field set
     */
    public QuietPlace saveQuietPlace(QuietPlace quietPlace) {
        ContentValues values = new ContentValues();
        values.put(QuietPlacesSQLiteHelper.COLUMN_COMMENT, quietPlace.getComment());
        values.put(QuietPlacesSQLiteHelper.COLUMN_LATITUDE, quietPlace.getLatitude());
        values.put(QuietPlacesSQLiteHelper.COLUMN_LONGITUDE, quietPlace.getLongitude());
        values.put(QuietPlacesSQLiteHelper.COLUMN_RADIUS, quietPlace.getRadius());
        values.put(QuietPlacesSQLiteHelper.COLUMN_DATETIME, quietPlace.getDatetimeString());
        values.put(QuietPlacesSQLiteHelper.COLUMN_CATEGORY, quietPlace.getCategory());
        long insertId = database.insert(QuietPlacesSQLiteHelper.TABLE_PLACES, null,
                values);
        Cursor cursor = database.query(QuietPlacesSQLiteHelper.TABLE_PLACES,
                allColumns, QuietPlacesSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        QuietPlace newQuietPlace = cursorToQuietPlace(cursor);
        cursor.close();
        return newQuietPlace;
    }

    public void deleteQuietPlace(QuietPlace place) {
        long id = place.getId();
        Log.w(TAG, "QuietPlace deleted with id: " + id);
        database.delete(QuietPlacesSQLiteHelper.TABLE_PLACES,
                QuietPlacesSQLiteHelper.COLUMN_ID + " = " + id,
                null);
    }

    public List<QuietPlace> getAllQuietPlaces() {
        List<QuietPlace> places = new ArrayList<QuietPlace>();

        Cursor cursor = database.query(QuietPlacesSQLiteHelper.TABLE_PLACES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            QuietPlace place = cursorToQuietPlace(cursor);
            places.add(place);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return places;
    }

    private QuietPlace cursorToQuietPlace(Cursor cursor) {
        QuietPlace place = new QuietPlace();
        place.setId(cursor.getLong(0));
        place.setComment(cursor.getString(1));
        place.setLatitude(cursor.getDouble(2));
        place.setLongitude(cursor.getDouble(3));
        place.setRadius(cursor.getDouble(4));
        // TODO: probably need some exception handling here
        place.setDatetimeString(cursor.getString(5));
        place.setCategory(cursor.getString(6));
        return place;
    }
}
