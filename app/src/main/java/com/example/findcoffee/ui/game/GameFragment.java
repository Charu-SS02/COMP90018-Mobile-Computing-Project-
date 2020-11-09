package com.example.findcoffee.ui.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.findcoffee.R;

import java.util.Objects;
import java.util.Random;

public class GameFragment extends Fragment{
    Canvas canvas;
    SnakeGame game;
    
    Bitmap headContentBitmap;
    Bitmap bodyContentBitmap;
    Bitmap tailContentBitmap;
    Bitmap foodContentBitmap;

    float x1, x2, y1, y2;

    int margin;
    int bottomMargin;
    int actualWidth;
    int actualHeight;
    int [] snakeWidth;
    int [] snakeHeight;
    int score;

    //stats
    long recentFrameTime;
    int fps;
    int highScore;
    int getLength;
    int foodX;
    int foodY;

    //The size in pixels of a place on the game board
    int quadSize;
    //new
    int width;
    int height;

    int direction = 0;
    
    
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_game, container, false);
        showAlertDialog();
        
        configureDisplay();
        game = new SnakeGame(getActivity());
        
        return game;
    }

    private void showAlertDialog() {
        FragmentManager fm = getChildFragmentManager();
        GameDialogFragment alertDialog = GameDialogFragment.newInstance("Welcome to the SNAKE game \n Swipe to move!");
        alertDialog.show(fm, "fragment_alert");
    }

    @Override
    public void onStop() {
        super.onStop();
        while (true){
            game.pause();
            break;
        }
        getActivity().getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onResume() {
        super.onResume();
        game.resume();
    }


    @Override
    public void onPause() {
        super.onPause();
        game.pause();
    }
    
    
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void configureDisplay(){
        //find out the width and height of the screen
        Display display = ((Activity) Objects.requireNonNull(getContext())).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        actualWidth = size.x;
        actualHeight = size.y;
        margin = actualHeight/16;
        bottomMargin = actualHeight/16;
        quadSize = actualWidth/15;
        width = 15;
        height = ((actualHeight - margin-bottomMargin ))/quadSize;
        //scale the bitmaps to match the block size
        headContentBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.head), quadSize, quadSize, false);
        bodyContentBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.body), quadSize, quadSize, false);
        tailContentBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.body), quadSize, quadSize, false);
        foodContentBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.coffee_cup_logo), quadSize, quadSize, false);

    }


    class SnakeGame extends SurfaceView implements Runnable
    {

        Thread thread = null;
        SurfaceHolder getSurfaceHolder;
        volatile boolean playingSnake;
        Paint paint;

        public SnakeGame(Context context) {
            super(context);

            getSurfaceHolder = getHolder();
            paint = new Paint();

            //Even my 9 year old play tester couldn't
            //get a snake this long
            snakeWidth = new int[200];
            snakeHeight = new int[200];

            //our starting snake
            newGameStart();
            //get an food to munch
            newFood();
        }


        @Override
        public void run() {

            while (playingSnake){
                updateSnake();
                Draw();
                configFPS();
            }
        }


        private void Draw() {
//            Log.d("Game","Game"+getSurfaceHolder.getSurface().isValid());
            if (getSurfaceHolder.getSurface().isValid()) {
                canvas = getSurfaceHolder.lockCanvas();
                //Paint paint = new Paint();
                canvas.drawColor(Color.WHITE);//the background
                paint.setColor(Color.BLACK);
                paint.setTextSize(margin/2);
                canvas.drawText("Score:" + score , 10, margin-6, paint);

                paint.setStrokeWidth(3);//4 pixel border
                canvas.drawLine(1,margin,actualWidth-1,margin,paint);
                canvas.drawLine(actualWidth-1, (height*quadSize)-margin-bottomMargin-10, 1, (height*quadSize)-margin-bottomMargin-10,paint);

                //Draw the snake
                canvas.drawBitmap(headContentBitmap, snakeWidth[0]*quadSize, (snakeHeight[0]*quadSize)+margin, paint);
                //Draw the body
                for(int i = 1; i < getLength-1;i++){
                    canvas.drawBitmap(bodyContentBitmap, snakeWidth[i]*quadSize, (snakeHeight[i]*quadSize)+margin, paint);
                }
                //draw the tail
                canvas.drawBitmap(tailContentBitmap, snakeWidth[getLength-1]*quadSize, (snakeHeight[getLength-1]*quadSize)+margin, paint);

                //draw the food
                canvas.drawBitmap(foodContentBitmap, foodX*quadSize, (foodY*quadSize)+margin, paint);

                getSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

        private void configFPS() {

            long frameTime = (System.currentTimeMillis() - recentFrameTime);
            long sleepTime = 100 - frameTime;
            if (frameTime > 0) {
                fps = (int) (1000 / frameTime);
            }
            if (sleepTime > 0) {

                try {
                    thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    //Print an error message to the console
                    Log.e("error", "Interrupted exception");
                }

            }

            recentFrameTime = System.currentTimeMillis();
        }

        public void updateSnake(){
            if(foodX == snakeWidth[0] && foodY == snakeHeight[0]){
                snakeGrow();
                newFood();
            }

            moveSnakeBody();
            moveSnakeHead(direction);

            if(isDead()){
//                highScore = score;
                score = 0;
                newGameStart();
            }
            
        }

        public void pause(){
            playingSnake = false;
            try {
                thread.join();
            }catch (InterruptedException e){
                Log.e("error", "Interrupted exception during pause");
            }
        }

        public void resume(){
            playingSnake = true;
            thread = new Thread(this);
            thread.start();
        }

        public void newGameStart(){
            getLength = 3;
            int midX = width/2;
            int midY = height/2;
            for(int i = 0; i < getLength; i++){
                snakeWidth[i] = midX;
                snakeHeight[i] = midY-i;
            }
        }

        public boolean isDead(){
            boolean dead = false;

            for (int i = getLength-1; i > 0; i--) {
                if ((i > 4) && (snakeWidth[0] == snakeWidth[i]) && (snakeHeight[0] == snakeHeight[i])) {
                    dead = true;
                }
            }

            return dead;
        }

        public void moveSnakeHead(int direction){
            switch(direction){
                case 0:
                    snakeHeight[0] --;
                    break;

                case 1:
                    snakeWidth[0] --;
                    break;

                case 2:
                    snakeHeight[0] ++;
                    break;

                case 3:
                    snakeWidth[0] ++;
                    break;
            }
            if(snakeWidth[0] >= width){
                snakeWidth[0] = 0;
            }else if(snakeWidth[0] < 0){
                snakeWidth[0] = width - 1;
            }
            if(snakeHeight[0] >= height -6){
                snakeHeight[0] = 0;
            }else if(snakeHeight[0] < 0){
                snakeHeight[0] = height - 7;
            }

        }

        public void moveSnakeBody(){
            for(int i=getLength; i >0 ; i--){
                snakeWidth[i] = snakeWidth[i-1];
                snakeHeight[i] = snakeHeight[i-1];
            }
        }
       

        public void snakeGrow(){
            getLength += 1;
            score += 1;
        }

        public void newFood(){
            Random food = new Random();
            foodX = food.nextInt(width-1)+1;
            foodY = food.nextInt(height-7)+1;
        }


        @Override
        public boolean onTouchEvent(MotionEvent e) {


            switch (e.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    x1 = e.getX();
                    y1 = e.getY();
                    break;


                case MotionEvent.ACTION_UP:
                    x2 = e.getX();
                    y2 = e.getY();
                    float xdiff = x1 - x2;
                    float ydiff = y1 - y2;
                    if(Math.abs(xdiff)> Math.abs(ydiff)){
                        if(xdiff > 0 && direction != 3){
                            direction = 1;
                        }else if(direction != 1){
                            direction = 3;
                        }
                    }else{
                        if(ydiff > 0 && direction != 2){
                            direction = 0;
                        }else if(direction != 0){
                            direction = 2;
                        }
                    }
            }
            return true;
        }


    }



}
