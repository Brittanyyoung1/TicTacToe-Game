package edu.fvtc.tictactoegame;


import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;



import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;


public class MainActivity extends AppCompatActivity {

    HubConnection hubConnection;
    String hubConnectionId;
    int width;
    int height;
    Point point = new Point();
    Board board = new Board();
    String currentPlayer="";

    private static final String TAG = "MainActivity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
        currentPlayer = "X";

        getScreenDims();
        board = new Board(width);

        Log.d(TAG, "onCreate: End");
    }

    private void initSignalR() {
        hubConnection = HubConnectionBuilder
                .create("https://fvtcdp.azurewebsites.net/GameHub")
                .build();
        Log.d(TAG, "initSignalR: Hub Built");

        hubConnection.start().blockingAwait();
        hubConnection.invoke(Void.class, "GetConnectionId");

        hubConnection.on("ReceiveMessage", (user, message) -> {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: User: " + user);
                    Log.d(TAG, "run: Message: " + message);

                    String[] data = user.split(":", 3);
                    setContentView(new DrawView(getMainActivity(),
                            Integer.valueOf(data[1]),
                            Integer.valueOf(data[2])));

                }
            });
        }, String.class, String.class);
    }
    private MainActivity getMainActivity()
    {
        return this;
    }
    private void getScreenDims() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        Log.d(TAG, "getScreenDims: " + width + ":" + height);
    }

    private  class DrawView extends View implements View.OnTouchListener {
        int lastTouchX;
        int lastTouchY;
        int posX = 140;
        int posY = 130;


        public DrawView(Context context) {
            super(context);
            this.setOnTouchListener(this);
            Log.d(TAG, "DrawView: touch");
        }

        public DrawView(Context context, int x, int y) {
            super(context);
            this.setOnTouchListener(this);
            posX = x;
            posY = y;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.DKGRAY);
            Paint paint = new Paint();

            paint.setColor(0xFFFF0000);
            Log.d(TAG, "onDraw: ");
            board.Draw(canvas, currentPlayer);





        }

        public boolean onTouch(View view, MotionEvent event) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {

                int x = (int) event.getX();
                int y = (int) event.getY();

                Point touchPoint = new Point(x, y);
                String hitResult = board.hitTest(touchPoint, currentPlayer, getContext());


                if (hitResult.equals("0")) {

                    currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";

                    invalidate();

                }
                return true;
            }
            return false;
        }
    }
}