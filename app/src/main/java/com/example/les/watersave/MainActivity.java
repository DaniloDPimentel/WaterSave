package com.example.les.watersave;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.les.watersave.models.Mock;
import com.github.lzyzsd.circleprogress.CircleProgress;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("SdCardPath")
    private CircleProgress circleProgress;

    private EditText volumeCaixa;

    private Timer timerAtual = new Timer();
    private final Handler handler = new Handler();

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        volumeCaixa = (EditText) findViewById(R.id.editText);
        Button salvarVolumeCaixa = (Button) findViewById(R.id.button);

        TextView consumoText = (TextView) findViewById(R.id.textView1);
        TextView estivativaText = (TextView) findViewById(R.id.textView2);

        String consumo = String.format(
                getResources().getString(R.string.consumo_text),
                Mock.Instance.getMediaConsumoDiaria()
        );
        consumoText.setText(consumo);

        String estimativa = String.format(
                getResources().getString(R.string.estimativa_text),
                Mock.Instance.getEstimativa()
        );
        estivativaText.setText(estimativa);

        salvarVolumeCaixa.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Context contexto = getApplicationContext();
                        String texto = "Caixa salva com: " + volumeCaixa.getText() + " L";
                        int duracao = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(contexto, texto, duracao);
                        toast.show();

                    }
                }
        );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        circleProgress = (CircleProgress) findViewById(R.id.circle_progress);

        ativaTimer();
    }

    int i = 4;
    private void ativaTimer(){
        TimerTask task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        i++;
                        circleProgress.setProgress(i);
                    }
                });
            }};

        timerAtual.schedule(task, 300, 300);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(getApplicationContext(), DicasActivity.class);
            startActivity(intent);

        }else if(id == R.id.historicoDeConsumo) {
            Intent intent = new Intent(getApplicationContext(), HistoricoActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
