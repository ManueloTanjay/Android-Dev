package edu.umb.cs443.hw2;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Random;

public class MainActivity extends Activity {

    GridView gridView;
    Thread OMove;
    Thread X;
    HashSet<Integer> XPositions = new HashSet<Integer>();
    //TextView cells = (TextView)findViewById(R.id.textCell);
    TextView cells;
    //TextView treasures = (TextView)findViewById(R.id.textTreasure);
    TextView treasures;
    int cellsCount = 0;
    int treasuresCount = 0;

    private static int w=5,curx,cury;

    private Random r=new Random();

    static String[] tiles = new String[w*w];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView1);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, tiles);

        gridView.setAdapter(adapter);

        init();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

//                Toast.makeText(getApplicationContext(),
//                        (Integer.toString(curx) + " " + Integer.toString(cury)),
////                        Toast.LENGTH_SHORT).show();

                OMove(position);
            }


        });

    }

    public void X()
    {
        final Runnable genX = new Runnable() {
            @Override
            public void run() {
                int numX = 0;
                Random rand = new Random();
                //HashSet<Integer> XPositions = new HashSet<Integer>();
                while (!Thread.currentThread().isInterrupted()) {
                    while (XPositions.size() < 4) {
                        int newPosition = rand.nextInt(25);
                        while (XPositions.contains(newPosition) || tiles[newPosition] == "O") {
                            newPosition = rand.nextInt(25);
                        }
                        XPositions.add(newPosition);

                        for (int i = 0; i < tiles.length; i++) {
                            if (XPositions.contains(i)) {
                                tiles[i] = "X";
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ArrayAdapter) gridView.getAdapter()).notifyDataSetChanged();
                            }
                        });
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        X = new Thread(genX);
        X.start();
    }

    public void OMove(final int position)
    {
        final Runnable move = new Runnable() {
            @Override
            public void run() {

                int currPos = curx * w + cury;
                int targetX = position % w;
                int targetY = position / w;

                while (cury > targetY)
                {
                    try
                    {
                        Thread.sleep(300);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    //for(int i=0;i<tiles.length;i++) tiles[i]=" ";
                    tiles[cury * w + curx] = " ";

                    cury = cury - 1;

                    tiles[cury*w+curx] = "O";

                    if(XPositions.contains(cury*w+curx))
                    {
                        XPositions.remove(cury*w+curx);
                        treasuresCount++;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ArrayAdapter)gridView.getAdapter()).notifyDataSetChanged();
                            cellsCount++;
                            cells = (TextView)findViewById(R.id.textCell);
                            cells.setText(Integer.toString(cellsCount) + " Cells");
                            treasures = (TextView)findViewById(R.id.textTreasure);
                            treasures.setText(Integer.toString(treasuresCount) + " Treasures");
                        }
                    });
                }
                while (curx > targetX)
                {
                    try
                    {
                        Thread.sleep(300);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    //for(int i=0;i<tiles.length;i++) tiles[i]=" ";
                    tiles[cury * w + curx] = " ";

                    curx = curx - 1;

                    tiles[cury*w+curx] = "O";

                    if(XPositions.contains(cury*w+curx))
                    {
                        XPositions.remove(cury*w+curx);
                        treasuresCount++;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ArrayAdapter)gridView.getAdapter()).notifyDataSetChanged();
                            cellsCount++;
                            cells = (TextView)findViewById(R.id.textCell);
                            cells.setText(Integer.toString(cellsCount) + " Cells");
                            treasures = (TextView)findViewById(R.id.textTreasure);
                            treasures.setText(Integer.toString(treasuresCount) + " Treasures");
                        }
                    });
                }
                while (cury < targetY)
                {
                    try
                    {
                        Thread.sleep(300);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    //for(int i=0;i<tiles.length;i++) tiles[i]=" ";
                    tiles[cury * w + curx] = " ";

                    cury = cury + 1;

                    tiles[cury*w+curx] = "O";

                    if(XPositions.contains(cury*w+curx))
                    {
                        XPositions.remove(cury*w+curx);
                        treasuresCount++;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ArrayAdapter)gridView.getAdapter()).notifyDataSetChanged();
                            cellsCount++;
                            cells = (TextView)findViewById(R.id.textCell);
                            cells.setText(Integer.toString(cellsCount) + " Cells");
                            treasures = (TextView)findViewById(R.id.textTreasure);
                            treasures.setText(Integer.toString(treasuresCount) + " Treasures");
                        }
                    });
                }
                while (curx < targetX)
                {
                    try
                    {
                        Thread.sleep(300);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    //for(int i=0;i<tiles.length;i++) tiles[i]=" ";
                    tiles[cury * w + curx] = " ";

                    curx = curx + 1;

                    tiles[cury*w+curx] = "O";

                    if(XPositions.contains(cury*w+curx))
                    {
                        XPositions.remove(cury*w+curx);
                        treasuresCount++;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((ArrayAdapter)gridView.getAdapter()).notifyDataSetChanged();
                            cellsCount++;
                            cells = (TextView)findViewById(R.id.textCell);
                            cells.setText(Integer.toString(cellsCount) + " Cells");
                            treasures = (TextView)findViewById(R.id.textTreasure);
                            treasures.setText(Integer.toString(treasuresCount) + " Treasures");
                        }
                    });
                }
            }
        };

        OMove = new Thread(move);
        OMove.start();

    }

    public void quit(View view)
    {
        this.finish();
        System.exit(0);
    }

    public void reset(View view){
        //init();
        X.interrupt();
        for(int i=0;i<tiles.length;i++) tiles[i]=" ";
        XPositions.clear();

        curx=r.nextInt(w);
        cury=r.nextInt(w);
        X();
        tiles[cury*w+curx]="O";


        ((ArrayAdapter)gridView.getAdapter()).notifyDataSetChanged();
    }

    void init(){
        for(int i=0;i<tiles.length;i++) tiles[i]=" ";
        XPositions.clear();

        curx=r.nextInt(w);
        cury=r.nextInt(w);
        X();
        tiles[cury*w+curx]="O";


        ((ArrayAdapter)gridView.getAdapter()).notifyDataSetChanged();
    }

}
