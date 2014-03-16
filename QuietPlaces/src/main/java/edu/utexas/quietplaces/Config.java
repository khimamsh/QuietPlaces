package edu.utexas.quietplaces;

import android.graphics.Color;

/**
 * Generic bucket for configuration values.
 *
 */
class Config {
    public static final String APP_NAME = "QuietPlaces";
    public static final String PACKAGE_NAME = "edu.utexas.quietplaces";

    // http://developer.android.com/training/location/receive-location-updates.html
    //
    // Basically, we normally only ask for location updates every 30 seconds,
    // but we'll accept updates as often as every 5 seconds, if other apps
    // are requesting more frequent updates.

    public static final long LOCATION_UPDATE_INTERVAL_MS = 30000;
    public static final long LOCATION_FASTEST_INTERVAL_MS = 5000;

    // Probably belongs in an XML file somewhere
    public static final int QP_CIRCLE_STROKE_COLOR = Color.argb(150, 0, 0, 255);
    public static final int QP_CIRCLE_FILL_COLOR = Color.argb(100, 138, 241, 255);

    // The suggested radius of a manually added quiet place is a multiplier of the current
    // width in meters of the shortest dimension of the current map viewing region
    public static final double SUGGESTED_RADIUS_MULTIPLIER = 0.1;
}
