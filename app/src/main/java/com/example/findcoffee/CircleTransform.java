/*
 * Copyright 2014 Julian Shen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.findcoffee;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.squareup.picasso.Transformation;

/**
 * Created by julian on 13/6/21.
 */
public class CircleTransform implements Transformation {
    private int mBorderSize;
    private int mCornerRadius = 0;
    private int mColor;

    public CircleTransform(int borderSize, int color) {
        this.mBorderSize = borderSize;
        this.mColor = color;
    }

    public CircleTransform(int borderSize, int cornerRadius, int color) {
        this.mBorderSize = borderSize;
        this.mCornerRadius = cornerRadius;
        this.mColor = color;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap image = Bitmap.createBitmap(width, height, source.getConfig());
        Canvas canvas = new Canvas(image);
        canvas.drawARGB(0, 0, 0, 0);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect rect = new Rect(0, 0, width, height);


        if(this.mCornerRadius == 0) {
            canvas.drawRect(rect, paint);
        }
        else {
            canvas.drawRoundRect(new RectF(rect),
                    this.mCornerRadius, this.mCornerRadius, paint);
        }

        paint.setXfermode(new PorterDuffXfermode((PorterDuff.Mode.SRC_IN)));
        canvas.drawBitmap(source, rect, rect, paint);

        Bitmap output;

        if(this.mBorderSize == 0) {
            output = image;
        }
        else {
            width = width + this.mBorderSize * 2;
            height = height + this.mBorderSize * 2;

            output = Bitmap.createBitmap(width, height, source.getConfig());
            canvas.setBitmap(output);
            canvas.drawARGB(0, 0, 0, 0);

            rect = new Rect(0, 0, width, height);

            paint.setXfermode(null);
            paint.setColor(this.mColor);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawRoundRect(new RectF(rect), this.mCornerRadius, this.mCornerRadius, paint);

            canvas.drawBitmap(image, this.mBorderSize, this.mBorderSize, null);
        }

        if(source != output){
            source.recycle();
        }
        return output;
    }

    @Override
    public String key() {
        return "bitmapBorder(" +
                "borderSize=" + this.mBorderSize + ", " +
                "cornerRadius=" + this.mCornerRadius + ", " +
                "color=" + this.mColor +")";
    }
}