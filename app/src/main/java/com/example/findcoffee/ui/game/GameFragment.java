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
    //the snake head sprite sheet
    Bitmap headAnimBitmap;

//    GameActivity.SnakeView snakeView;

    Bitmap headBitmap;
    Bitmap bodyBitmap;
    Bitmap tailBitmap;
    Bitmap appleBitmap;

    //the portion of the bitmap to be drawn in the current frame
    Rect rectToBeDrawn;

    //The dimensions of  a single frame
    int frameHeight = 64;
    int frameWidth = 64;
    int numFrames  = 6;
    int frameNumber;

    int topGap;
    int screenWidth;
    int screenHeight;
    int [] snakeX;
    int [] snakeY;
    int score;

    //stats
    long lastFrameTime;
    int fps;
    int hi;
    int snakeLength;
    int appleX;
    int appleY;

    //The size in pixels of a place on the game board
    int blockSize;
    int numBlocksWide;
    int numBlocksHigh;
    //new
    int width;
    int height;

    private SoundPool soundPool;

    int sample1 = -1;
    int sample2 = -1;
    int sample3 = -1;
    int sample4 = -1;


    //for snake movement
    int directionOfTravel=0;
    int direction = 0;
    //0 = up, 1 = right, 2 = down, 3= left

    //to start the game from onTouchEvent
    Intent i;


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
        GameDialogFragment alertDialog = GameDialogFragment.newInstance("Welcome to the SNAKE game");
        alertDialog.show(fm, "fragment_alert");
    }


//    class SnakeAnimView extends SurfaceView implements Runnable
//    {
//        Thread ourThread = null;
//        SurfaceHolder ourHolder;
//        volatile boolean playingSnake;
//        Paint paint;
//
//        public SnakeAnimView(Context context) {
//            super(context);
//            ourHolder = getHolder();
//            paint = new Paint();
//
//            //Even my 9 year old play tester couldn't
//            //get a snake this long
//            snakeX = new int[200];
//            snakeY = new int[200];
//
//            //our starting snake
//            getSnake();
//            //get an apple to munch
//            getApple();
//        }
//        public void getSnake(){
//            snakeLength = 3;
//            //start snake head in the middle of screen
//            snakeX[0] = numBlocksWide/2;
//            snakeY[0] = numBlocksHigh /2;
//
//            //Then the body
//            snakeX[1] = snakeX[0]-1;
//            snakeY[1] = snakeY[0];
//
//            //And the tail
//            snakeX[1] = snakeX[1]-1;
//            snakeY[1] = snakeY[0];
//        }
//
//        public void getApple(){
//            Random random = new Random();
//            appleX = random.nextInt(numBlocksWide-1)+1;
//            appleY = random.nextInt(numBlocksHigh-1)+1;
//        }
//
//        @Override
//        public void run() {
//            while (playingSnake){
//                update();
//                Draw();
//                controlFPS();
//            }
//        }
//
//        private void controlFPS() {
//
//            long timeThisFrame = (System.currentTimeMillis() - lastFrameTime);
//            long timeToSleep = 100 - timeThisFrame;
//            if (timeThisFrame > 0) {
//                fps = (int) (1000 / timeThisFrame);
//            }
//            if (timeToSleep > 0) {
//
//                try {
//                    ourThread.sleep(timeToSleep);
//                } catch (InterruptedException e) {
//                    //Print an error message to the console
//                    Log.e("error", "failed to load sound files");
//                }
//
//            }
//
//            lastFrameTime = System.currentTimeMillis();
//        }
//
//        private void Draw() {
////            Log.d("Game","Game"+ourHolder.getSurface().isValid());
//            if (ourHolder.getSurface().isValid()) {
//                canvas = ourHolder.lockCanvas();
//                //Paint paint = new Paint();
//                canvas.drawColor(Color.WHITE);//the background
//                paint.setColor(Color.argb(255, 255, 255, 255));
//                paint.setTextSize(topGap/2);
//                canvas.drawText("Score:" + score + "  Hi:" + hi, 10, topGap-6, paint);
//
//                //draw a border - 4 lines, top right, bottom , left
//                paint.setStrokeWidth(3);//4 pixel border
//                canvas.drawLine(1,topGap,screenWidth-1,topGap,paint);
//                canvas.drawLine(screenWidth-1,topGap,screenWidth-1,topGap+(numBlocksHigh*blockSize),paint);
//                canvas.drawLine(screenWidth-1,topGap+(numBlocksHigh*blockSize),1,topGap+(numBlocksHigh*blockSize),paint);
//                canvas.drawLine(1,topGap, 1,topGap+(numBlocksHigh*blockSize), paint);
//
//                //Draw the snake
//                canvas.drawBitmap(headBitmap, snakeX[0]*blockSize, (snakeY[0]*blockSize)+topGap, paint);
//                //Draw the body
//                for(int i = 1; i < snakeLength-1;i++){
//                    canvas.drawBitmap(bodyBitmap, snakeX[i]*blockSize, (snakeY[i]*blockSize)+topGap, paint);
//                }
//                //draw the tail
//                canvas.drawBitmap(tailBitmap, snakeX[snakeLength-1]*blockSize, (snakeY[snakeLength-1]*blockSize)+topGap, paint);
//
//                //draw the apple
//                canvas.drawBitmap(appleBitmap, appleX*blockSize, (appleY*blockSize)+topGap, paint);
//
//                ourHolder.unlockCanvasAndPost(canvas);
//            }
//        }
//
//        private void update() {
//
////Did the player get the apple
//            if(snakeX[0] == appleX && snakeY[0] == appleY){
//                //grow the snake
//                snakeLength++;
//                //replace the apple
//                getApple();
//                //add to the score
//                score = score + snakeLength;
////                soundPool.play(sample1, 1, 1, 0, 0, 1);
//            }
//
//            //move the body - starting at the back
//            for(int i=snakeLength; i >0 ; i--){
//                snakeX[i] = snakeX[i-1];
//                snakeY[i] = snakeY[i-1];
//            }
//
//
//            //Move the head in the appropriate direction
//            switch (directionOfTravel){
//                case 0://up
//                    snakeY[0]  --;
//                    break;
//
//                case 1://right
//                    snakeX[0] ++;
//                    break;
//
//                case 2://down
//                    snakeY[0] ++;
//                    break;
//
//                case 3://left
//                    snakeX[0] --;
//                    break;
//            }
//
//            //Have we had an accident
//            boolean dead = false;
//            //with a wall
//            if(snakeX[0] == -1)dead=true;
//            if(snakeX[0] >= numBlocksWide)dead=true;
//            if(snakeY[0] == -1)dead=true;
//            if(snakeY[0] == numBlocksHigh)dead=true;
//            //or eaten ourselves?
//            for (int i = snakeLength-1; i > 0; i--) {
//                if ((i > 4) && (snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i])) {
//                    dead = true;
//                }
//            }
//
//
//            if(dead){
//                //start again
////                soundPool.play(sample4, 1, 1, 0, 0, 1);
//                score = 0;
//                getSnake();
//
//            }
//        }
//
//
//        public void pause(){
//            playingSnake = false;
//            try {
//                ourThread.join();
//            }catch (InterruptedException e){
//
//            }
//        }
//
//        public void resume(){
//            playingSnake = true;
//            ourThread = new Thread(this);
//            ourThread.start();
//        }
//
//        @Override
//        public boolean onTouchEvent(MotionEvent motionEvent) {
//
//            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
//                case MotionEvent.ACTION_UP:
//                    if (motionEvent.getX() >= screenWidth / 2) {
//                        //turn right
//                        directionOfTravel ++;
//                        if(directionOfTravel == 4) {//no such direction
//                            //loop back to 0(up)
//                            directionOfTravel = 0;
//                        }
//                    } else {
//                        //turn left
//                        directionOfTravel--;
//                        if(directionOfTravel == -1) {//no such direction
//                            //loop back to 0(up)
//                            directionOfTravel = 3;
//                        }
//                    }
//            }
//            return true;
//        }
//
//
//
//
//
//    }


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


    //for back press
    public boolean onKeyDwon(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            game.pause();
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void configureDisplay(){
        //find out the width and height of the screen
        Display display = ((Activity) Objects.requireNonNull(getContext())).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        topGap = screenHeight/14;

        //Determine the size of each block/place on the game board
        blockSize = screenWidth/40;

        //Determine how many game blocks will fit into the height and width
        //Leave one block for the score at the top
        width = 40;
        height = ((screenHeight - topGap ))/blockSize;

        //Load and scale bitmaps
        headBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head);
        bodyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.body);
        tailBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tail);
        appleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.apple);

        //scale the bitmaps to match the block size
        headBitmap = Bitmap.createScaledBitmap(headBitmap, blockSize, blockSize, false);
        bodyBitmap = Bitmap.createScaledBitmap(bodyBitmap, blockSize, blockSize, false);
        tailBitmap = Bitmap.createScaledBitmap(tailBitmap, blockSize, blockSize, false);
        appleBitmap = Bitmap.createScaledBitmap(appleBitmap, blockSize, blockSize, false);

    }


    class SnakeGame extends SurfaceView implements Runnable
    {

        Thread thread = null;
        SurfaceHolder ourHolder;
        volatile boolean playingSnake;
        Paint paint;

        public SnakeGame(Context context) {
            super(context);
            new OnSwipeTouchListener(context, this);
            ourHolder = getHolder();
            paint = new Paint();

            //Even my 9 year old play tester couldn't
            //get a snake this long
            snakeX = new int[200];
            snakeY = new int[200];

            //our starting snake
            newGame();
            //get an apple to munch
            newApple();
        }


        @Override
        public void run() {

            while (playingSnake){
                update();
                Draw();
                controlFPS();
            }
        }

        private void Draw() {
//            Log.d("Game","Game"+ourHolder.getSurface().isValid());
            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();
                //Paint paint = new Paint();
                canvas.drawColor(Color.WHITE);//the background
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(topGap/2);
                canvas.drawText("Score:" + score + "  Hi:" + hi, 10, topGap-6, paint);

                //draw a border - 4 lines, top right, bottom , left
                paint.setStrokeWidth(3);//4 pixel border
                canvas.drawLine(1,topGap,screenWidth-1,topGap,paint);
                canvas.drawLine(screenWidth-1,topGap,screenWidth-1,topGap+(height*blockSize),paint);
                canvas.drawLine(screenWidth-1,topGap+(height*blockSize),1,topGap+(height*blockSize),paint);
                canvas.drawLine(1,topGap, 1,topGap+(height*blockSize), paint);

                //Draw the snake
                canvas.drawBitmap(headBitmap, snakeX[0]*blockSize, (snakeY[0]*blockSize)+topGap, paint);
                //Draw the body
                for(int i = 1; i < snakeLength-1;i++){
                    canvas.drawBitmap(bodyBitmap, snakeX[i]*blockSize, (snakeY[i]*blockSize)+topGap, paint);
                }
                //draw the tail
                canvas.drawBitmap(tailBitmap, snakeX[snakeLength-1]*blockSize, (snakeY[snakeLength-1]*blockSize)+topGap, paint);

                //draw the apple
                canvas.drawBitmap(appleBitmap, appleX*blockSize, (appleY*blockSize)+topGap, paint);

                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        private void controlFPS() {

            long timeThisFrame = (System.currentTimeMillis() - lastFrameTime);
            long timeToSleep = 100 - timeThisFrame;
            if (timeThisFrame > 0) {
                fps = (int) (1000 / timeThisFrame);
            }
            if (timeToSleep > 0) {

                try {
                    thread.sleep(timeToSleep);
                } catch (InterruptedException e) {
                    //Print an error message to the console
                    Log.e("error", "failed to load sound files");
                }

            }

            lastFrameTime = System.currentTimeMillis();
        }

        public void update(){
            if(appleX == snakeX[0] && appleY == snakeY[0]){
                snakeGrow();
                newApple();
            }

            moveSnakeBody();
            moveSnakeHead(direction);

            if(isDead()){
                score = 0;
                newGame();
            }


        }

        public void pause(){
            playingSnake = false;
            try {
                thread.join();
            }catch (InterruptedException e){

            }
        }

        public void resume(){
            playingSnake = true;
            thread = new Thread(this);
            thread.start();
        }

        public void newGame(){
            snakeLength = 3;
            int midX = width/2;
            int midY = height/2;
            for(int i = 0; i < snakeLength; i++){
                snakeX[i] = midX;
                snakeY[i] = midY-i;
            }
        }

        public boolean isDead(){
            boolean dead = false;

            for (int i = snakeLength-1; i > 0; i--) {
                if ((i > 4) && (snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i])) {
                    dead = true;
                }
            }

            return dead;
        }

        public void moveSnakeHead(int direction){
            switch(direction){
                case 0:
                    snakeY[0] --;
                    break;

                case 1:
                    snakeX[0] ++;
                    break;

                case 2:
                    snakeY[0] ++;
                    break;

                case 3:
                    snakeX[0] --;
                    break;
            }
            if(snakeX[0] >= width){
                snakeX[0] = 1;
            }else if(snakeX[0] <= 0){
                snakeX[0] = width - 1;
            }
            if(snakeY[0] >= height){
                snakeY[0] = 1;
            }else if(snakeY[0] <= 0){
                snakeY[0] = height - 1;
            }

        }

        public void moveSnakeBody(){
            for(int i=snakeLength; i >0 ; i--){
                snakeX[i] = snakeX[i-1];
                snakeY[i] = snakeY[i-1];
                if(snakeX[i] >= width){
                    snakeX[i] = 1;
                }else if(snakeX[i] <= 0){
                    snakeX[i] = width - 1;
                }
                if(snakeY[i] >= height){
                    snakeY[i] = 1;
                }else if(snakeY[i] <= 0){
                    snakeY[i] = height - 1;
                }
            }
        }
        public int getSnakeLength(){
            return snakeLength;
        }

        public void snakeGrow(){
            snakeLength += 1;
            score += 1;
        }

        public void newApple(){
            Random apple = new Random();
            appleX = apple.nextInt(width-1)+1;
            appleY = apple.nextInt(height-1)+1;
        }

        public void setDirection(int dir) {
            direction = dir;
        }
        public int getDirection() {
            return direction;
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {

            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    if (motionEvent.getX() >= screenWidth / 2) {
                        //turn right
                        direction ++;
                        if(direction == 4) {//no such direction
                            //loop back to 0(up)
                            direction = 0;
                        }
                    } else {
                        //turn left
                        direction--;
                        if(direction == -1) {//no such direction
                            //loop back to 0(up)
                            direction = 3;
                        }
                    }
            }
            return true;
        }


        public void goRight() {
            if(direction != 3){
                direction = 1;
            }
        }

        public void goLeft(){
            if(direction != 1){
                direction = 3;
            }
        }

        public void goUp(){
            if(direction != 0){
                direction = 2;
            }
        }

        public void goDown(){
            if(direction != 2){
                direction = 0;
            }
        }
    }



}
