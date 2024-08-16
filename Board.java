package edu.fvtc.tictactoegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.widget.Toast;


public class Board {
    public final static int BOARDSIZE = 3;
    String[][] cellValues = new String[BOARDSIZE][BOARDSIZE];
    Rect[][] cells = new Rect[BOARDSIZE][BOARDSIZE];
    int SIZE = 55;
    int OFFSET = 10;

    public static final String TAG = "Board";
    int viewWidth;
    int viewHeight;



    public Board()
    {

        initCellValues();
    }
    public Board(int width)
    {
        viewWidth = width / BOARDSIZE;
        viewHeight = viewWidth;
        SIZE = viewWidth -3;
        initCellValues();
    }




    public void initCellValues() {
        for (int row = 0; row < BOARDSIZE; row++) {
            for (int col = 0; col < BOARDSIZE; col++) {
                cellValues[row][col] = "";
            }
        }
    }


    public void Draw(Canvas canvas, String currentPlayer) {
        // Draw the board.
        Paint paint = new Paint();

        for (int row = 0; row < cells[0].length; row++) {
            for (int col = 0; col < cells[1].length; col++) {
                paint.setColor(Color.GRAY);
                cells[row][col] = new Rect();
                cells[row][col].left = col * SIZE + OFFSET;
                cells[row][col].top = row * SIZE + OFFSET;
                cells[row][col].right = col * SIZE + OFFSET + SIZE;
                cells[row][col].bottom = row * SIZE + OFFSET + SIZE;
                canvas.drawRect(cells[row][col], paint);


                if (!cellValues[row][col].isEmpty()) {
                    drawTurn(canvas, cells[row][col], cellValues[row][col]);
                }
            }
        }

        Paint paint1 = new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setStrokeWidth(10);
        canvas.drawLine(350, 10, 350, 1082, paint1);
        canvas.drawLine(750, 10, 750, 1082, paint1);
        canvas.drawLine(10, 350, 1082, 350, paint1);
        canvas.drawLine(10, 700, 1082, 700, paint1);
    }
    public String checkWinner() {
        // Check rows
        for (int i = 0; i < BOARDSIZE; i++) {
            if (!cellValues[i][0].isEmpty() && cellValues[i][0].equals(cellValues[i][1]) && cellValues[i][0].equals(cellValues[i][2])) {
                return cellValues[i][0]; // Row i is a winning combination
            }
        }

        // Check columns
        for (int i = 0; i < BOARDSIZE; i++) {
            if (!cellValues[0][i].isEmpty() && cellValues[0][i].equals(cellValues[1][i]) && cellValues[0][i].equals(cellValues[2][i])) {
                return cellValues[0][i]; // Column i is a winning combination
            }
        }

        // Check diagonals
        if (!cellValues[0][0].isEmpty() && cellValues[0][0].equals(cellValues[1][1]) && cellValues[0][0].equals(cellValues[2][2])) {
            return cellValues[0][0]; // Top-left to bottom-right diagonal is a winning combination
        }
        if (!cellValues[0][2].isEmpty() && cellValues[0][2].equals(cellValues[1][1]) && cellValues[0][2].equals(cellValues[2][0])) {
            return cellValues[0][2]; // Top-right to bottom-left diagonal is a winning combination
        }

        // No winner found
        return "";
    }


    private void drawTurn(Canvas canvas, Rect rect, String symbol) {
        Paint paint1 = new Paint();
        paint1.setColor(Color.BLUE); // Default color for "X"
        Paint paint2 = new Paint();
        paint2.setColor(Color.RED); // Default color for "O"
        paint2.setTextSize(300);
        paint2.setTextAlign(Paint.Align.CENTER);

        if (symbol.equals("O")) {
            paint1.setStrokeWidth(5);
            paint1.setStyle(Paint.Style.STROKE);
            int x = rect.centerX();
            int y = rect.centerY();
            canvas.drawCircle(x, y, SIZE * .35f, paint1);

        } else if (symbol.equals("X")) {
            paint2.setStyle(Paint.Style.FILL);
            int x = rect.centerX();
            int y = rect.centerY();
            Paint.FontMetrics fontMetrics = paint2.getFontMetrics();
            float textHeight = fontMetrics.descent - fontMetrics.ascent;
            canvas.drawText("X", x, y + textHeight/3 , paint2);

        }

    }

    public String hitTest(Point point, String turn, Context context) {
        String result = "-1";
        Log.d(TAG, "hitTest: " + result);

        for (int row = 0; row < cells[0].length; row++) {
            for (int col = 0; col < cells[1].length; col++) {
                if (cellValues[row][col].isEmpty() && cells[row][col].contains(point.x, point.y)) {
                    cellValues[row][col] = turn;
                    result = "0";
                    String winner = checkWinner();
                    if (!winner.isEmpty()) {

                        Log.d(TAG, "Winner: " + winner);
                        String message = "Congratulations "+ winner + " Won!!";
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                        initCellValues();

                    }
                    return result;
                }
            }
        }
        return result;
    }

}
