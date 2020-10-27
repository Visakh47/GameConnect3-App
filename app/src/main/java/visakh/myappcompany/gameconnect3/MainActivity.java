package visakh.myappcompany.gameconnect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
//we use margins to fix the margins of the tokens w.r.t grid , check xml code , all margin:10dp , left margin:15dp
    //also we put the grid using grid view and put rows and columns and fixed the background img using attributes
    //and we copy pasted one image view onto every row and column in xml code

   //0:yellow , 1:red , 2: Empty - Initially all elements in the game state are 2 : empty
    int activePlayer =0;
    int[] gameState = {2,2,2,2,2,2,2,2,2}; // representing the color or no element in each counter space

    int winningPositons [][] = {{0,1,2},{3,4,5},{6,7,8},{0,4,8},{2,4,6},{0,3,6},{1,4,7},{2,5,8}}; //horizontally , vertically or diagonally
    int WinR=0,WinY=0;
    TextView yellowWinText;
    TextView redWinText;
    boolean gameActive = true;
    public void dropIn(View view) //click function on counter spaces

    {
        ImageView counter = (ImageView) view; //image is initially null. we reference the view here directly - not getting any id as it is a common function

        Log.i("INFO", counter.getTag().toString()); //gets tag of each element 0,1 to 8 whenever that element is clicked


        int tag = Integer.parseInt(counter.getTag().toString()); //we get the tag number
        if (gameState[tag]==2 && gameActive == true ) //we will only allow to insert counter if there is not counter present , or counter is initially empty && if the game is active

        {
            gameState[tag] = activePlayer; //setting the game state of the token on the basis of the activePlayer , where 2 = no active player


            counter.setTranslationY(-1500); // setting the imageview outside the screen
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow); //setting new image with yellow counter on click
                activePlayer = 1; //red's chance next;
            } else {
                counter.setImageResource(R.drawable.red);  //setting new image with yellow counter on click
                activePlayer = 0; //goes back to yellow
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(300); //pulling the image down by animation


            for (int[] i : winningPositons) //we are looping through each element- check notebook
            {
                if (gameState[i[0]] == gameState[i[1]] && gameState[i[1]] == gameState[i[2]] && gameState[i[0]] != 2) {
                    //Someone Won
                    gameActive = false;
                    String message;
                    if (activePlayer == 1) {
                        message = " Yellow Has Won !";
                        WinY++;
                    }
                    else {
                        message = "Red has Won";
                        WinR++;
                    }
                    Button Retry = (Button)findViewById(R.id.Retry);
                    TextView WinnerMessage = (TextView) findViewById(R.id.WinnerMessage);
                    WinnerMessage.setText(message);
                    Retry.setVisibility(View.VISIBLE);
                    WinnerMessage.setVisibility(View.VISIBLE);

                }
            }
            //No winner
            int count=0;
            for(int i:gameState)
            {
                if(i!=2)
                    count++;
            }
            if(count>8)
            {
                Button Retry = (Button)findViewById(R.id.Retry);
                TextView WinnerMessage = (TextView) findViewById(R.id.WinnerMessage);
                WinnerMessage.setText("No One Won");
                Retry.setVisibility(View.VISIBLE);
                WinnerMessage.setVisibility(View.VISIBLE);
            }

        }
    }

    public void PlayAgain(View view)
    {
        Button Retry = (Button) findViewById(R.id.Retry);
        TextView WinnerMessage = (TextView) findViewById(R.id.WinnerMessage);
        Retry.setVisibility(View.INVISIBLE);
        WinnerMessage.setVisibility(View.INVISIBLE);
        redWinText = (TextView)findViewById(R.id.redWinText);
        yellowWinText = (TextView)findViewById(R.id.yellowWinText);
        if(WinR!=0&&WinR!=1) {
            String MessageR = "Red Has Won " + Integer.toString(WinR) + " Times";
            redWinText.setText(MessageR);
        }
        else if(WinR==1)
        {
            String MessageR = "Red Has Won Once";
            redWinText.setText(MessageR);
        }
        if(WinY!=0&&WinY!=1) {
            String MessageY = "Yellow Has Won " + Integer.toString(WinY) + " Times";
            yellowWinText.setText(MessageY);
        }
        else if(WinY==1)
        {
            String MessageY = "Yellow Has Won Once" ;
            yellowWinText.setText(MessageY);
        }

        redWinText.setVisibility(View.VISIBLE);
        yellowWinText.setVisibility(View.VISIBLE);

        //making a variable of Grid Layout for looping through it's contents :
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0;i<gridLayout.getChildCount();i++)
        {
            ImageView counter = (ImageView) gridLayout.getChildAt(i); //we are getting the child contents of the gird layout instead of finding them by id having type image view at each ith iteration
            counter.setImageDrawable(null);
        }

         for(int i=0;i<gameState.length; i++) //array length - length
         {
            gameState[i]= 2; //setting to empty
         }
        activePlayer =0;
       gameActive = true;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}