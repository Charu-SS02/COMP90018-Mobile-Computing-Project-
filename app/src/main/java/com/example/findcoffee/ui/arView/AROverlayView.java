package com.example.findcoffee.ui.arView;

/**
 * Created by: Xixiang Wu
 * Date:       1/11/20.
 * Email:      xixiangw@student.unimelb.edu.au
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Location;
import android.opengl.Matrix;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import com.example.findcoffee.R;
import com.example.findcoffee.model.Shop;
import com.example.findcoffee.model.ShopMapper;
import com.example.findcoffee.ui.arView.helper.LocationHelper;
import com.example.findcoffee.ui.arView.model.ARPoint;

public class AROverlayView extends View {

    Context context;
    private float[] rotatedProjectionMatrix = new float[16];
    private Location currentLocation;
    private List<ARPoint> arPoints = new ArrayList<ARPoint>();


    public AROverlayView(Context context) {
        super(context);

        this.context = context;
    }

    public void addShopData(String name, double lat, double lon, double alt) {
        arPoints.add(new ARPoint(name, lat, lon, alt));
    }

    public void updateRotatedProjectionMatrix(float[] rotatedProjectionMatrix) {
        this.rotatedProjectionMatrix = rotatedProjectionMatrix;
        this.invalidate();
    }

    public void updateCurrentLocation(Location currentLocation){
        this.currentLocation = currentLocation;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (currentLocation == null) {
            return;
        }

        final int radius = 30;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setTextSize(60);

        for (int i = 0; i < arPoints.size(); i ++) {
            float[] currentLocationInECEF = LocationHelper.WSG84toECEF(currentLocation);
            float[] pointInECEF = LocationHelper.WSG84toECEF(arPoints.get(i).getLocation());
            float[] pointInENU = LocationHelper.ECEFtoENU(currentLocation, currentLocationInECEF, pointInECEF);

            float[] cameraCoordinateVector = new float[4];
            Matrix.multiplyMV(cameraCoordinateVector, 0, rotatedProjectionMatrix, 0, pointInENU, 0);


            // Retrieve the shop
            Shop shop = ShopMapper.getInstance().retrieveByName(arPoints.get(i).getName());

            // cameraCoordinateVector[2] is z, that always less than 0 to display on right position
            // if z > 0, the point will display on the opposite
            if (cameraCoordinateVector[2] < 0) {
                float x  = (0.5f + cameraCoordinateVector[0]/cameraCoordinateVector[3]) * canvas.getWidth();
                float y = (0.5f - cameraCoordinateVector[1]/cameraCoordinateVector[3]) * canvas.getHeight();

                canvas.drawCircle(x, y, radius, paint);

                Resources res = getResources();
                Bitmap bubbleBG = BitmapFactory.decodeResource(res, R.drawable.rectangle_resized);

                // Create a name bubble
                final float nameBubbleScale = 1.0f;
                int halfWidth = (int) (bubbleBG.getWidth()*nameBubbleScale);
                int halfHeight = (int) (bubbleBG.getHeight()*nameBubbleScale);
                int xInt = (int) x;
                int yInt = (int) y;

                Rect nameBubbleRect = new Rect(xInt-halfWidth,  yInt-halfHeight, xInt+halfWidth, yInt+halfHeight);
                canvas.drawBitmap(bubbleBG, null, nameBubbleRect, null);
                canvas.drawText(shop.getShopName(),  x - (30 * arPoints.get(i).getName().length() / 2), y - 50, paint);

                paint.setTextSize(paint.getTextSize() * 0.6f);

                // Outdoor and Indoor seats
                String outdoorSeatsStr;
                String indoorSeatsStr;

                if (shop.getShopOutdoorSeatNum() == 0 && shop.getShopIndoorSeatNum() == 0) {
                    outdoorSeatsStr = "No seats data in Melbourne database.";
                    indoorSeatsStr  = "";
                } else {
                    outdoorSeatsStr = "Outdoor seats: " + shop.getShopOutdoorSeatNum();
                    indoorSeatsStr  = "Indoor seats: " + shop.getShopIndoorSeatNum();
                }

                canvas.drawText(outdoorSeatsStr,  x - (18 * outdoorSeatsStr.length() / 2), y + 10, paint);
                canvas.drawText(indoorSeatsStr ,  x - (18 * indoorSeatsStr.length()  / 2), y + 50, paint);

            }
        }
    }

}
