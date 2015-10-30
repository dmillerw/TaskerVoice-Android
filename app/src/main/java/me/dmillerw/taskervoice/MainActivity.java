package me.dmillerw.taskervoice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public static final UUID UUID_PEBBLE_APP = UUID.fromString("c3ee0729-cc25-47c8-9882-76bec2caec28");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
