/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends FragmentActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);

    }

    public void convertCtoF(View v)
    {
        DecimalFormat df = new DecimalFormat("#.##");
        EditText iTempTextCtoF = findViewById(R.id.userInputCtoF);
        if(iTempTextCtoF.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Enter number bruh", Toast.LENGTH_SHORT).show();
            return;
        }
        //Toast.makeText(getApplicationContext(), iTempTextCtoF.getText(), Toast.LENGTH_SHORT).show();
        double iTempCtoF = Double.parseDouble(iTempTextCtoF.getText().toString());

        double fResCtoF = (iTempCtoF * 9 / 5) + 32;

        TextView resTextCtoF = findViewById(R.id.resultCtoF);
        resTextCtoF.setText(df.format(fResCtoF) + "F");
    }

    public void convertFtoC(View v)
    {
        DecimalFormat df = new DecimalFormat("#.##");
        EditText iTempTextCtoF = findViewById(R.id.userInputFtoC);
        if(iTempTextCtoF.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Enter number bruh", Toast.LENGTH_SHORT).show();
            return;
        }
        //Toast.makeText(getApplicationContext(), iTempTextCtoF.getText(), Toast.LENGTH_SHORT).show();
        double iTempCtoF = Double.parseDouble(iTempTextCtoF.getText().toString());

        double fResCtoF = (iTempCtoF - 32) * 5 / 9;

        TextView resTextCtoF = findViewById(R.id.resultFtoC);
        resTextCtoF.setText(df.format(fResCtoF) + "C");
    }

    public void onArticleSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment, Fragment is replaced depending on position
        if(position == 0)
        {
            CToFFragment cToFFragment = new CToFFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, cToFFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(position == 1)
        {
            FToCFragment fToCFragment = new FToCFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, fToCFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (position == 2)
        {
            HelpFragment helpFragment = new HelpFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, helpFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        //TextView article = findViewById(R.id.article);
        //article.setText(Ipsum.Articles[position]);

    }
}