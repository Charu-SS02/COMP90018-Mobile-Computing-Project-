package com.example.findcoffee.ui.arView;

/**
 * Created by: Xixiang Wu
 * Date:       1/11/20.
 * Email:      xixiangw@student.unimelb.edu.au
 */

import android.location.Location;

public class LocationHelper {

    /**
    * Location Helper is a class that is used to transfer the GPS coordinate data into the AR math
    *  format. Without the help from the 3D engine like Unity or the Library like Google ARCore,
    *  all of the longitude and latitude needs to be transferred into screen space for display.
    * */

    private final static double WGS84_A = 6378137.0;        // WGS 84 semi-major axis constant meters
    private final static double WGS84_E2 = 0.00669437999014;// square of WGS 84 eccentricity


    /* World Geodetic System to Earth-Centred, Earth-Fixed coordinates*/
    public static float[] WSG84toECEF(Location location) {
        double radLat = Math.toRadians(location.getLatitude());
        double radLon = Math.toRadians(location.getLongitude());

        float clat = (float) Math.cos(radLat);
        float slat = (float) Math.sin(radLat);
        float clon = (float) Math.cos(radLon);
        float slon = (float) Math.sin(radLon);

        float N = (float) (WGS84_A / Math.sqrt(1.0 - WGS84_E2 * slat * slat));

        float x = (float) ((N + location.getAltitude()) * clat * clon);
        float y = (float) ((N + location.getAltitude()) * clat * slon);
        float z = (float) ((N * (1.0 - WGS84_E2) + location.getAltitude()) * slat);

        return new float[] {x , y, z};
    }


    /* Earth-Centered, Earth-Fixed coordinates to East North up coordinates system */
    public static float[] ECEFtoENU(Location currentLocation, float[] ecefCurrentLocation, float[] ecefPOI) {
        double radLat = Math.toRadians(currentLocation.getLatitude());
        double radLon = Math.toRadians(currentLocation.getLongitude());

        float clat = (float)Math.cos(radLat);
        float slat = (float)Math.sin(radLat);
        float clon = (float)Math.cos(radLon);
        float slon = (float)Math.sin(radLon);

        float dx = ecefCurrentLocation[0] - ecefPOI[0];
        float dy = ecefCurrentLocation[1] - ecefPOI[1];
        float dz = ecefCurrentLocation[2] - ecefPOI[2];

        float east = -slon*dx + clon*dy;

        float north = -slat*clon*dx - slat*slon*dy + clat*dz;

        float up = clat*clon*dx + clat*slon*dy + slat*dz;

        return new float[] {east , north, up, 1};
    }

    public static double GeoDistanceCalculation(Location location, Location location2) {
        double lat1 = location.getLatitude();
        double lat2 = location2.getLatitude();
        double el1  = location.getAltitude();
        double el2  = location2.getAltitude();
        double lon1 = location.getLongitude();
        double lon2 = location2.getLongitude();

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000;

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

}
