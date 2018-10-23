package ie.ul.davidbeck.jersey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import static ie.ul.davidbeck.jersey.R.color.awayJersey;
import static ie.ul.davidbeck.jersey.R.color.homeJersey;
import static ie.ul.davidbeck.jersey.R.drawable.green_jersey;
import static ie.ul.davidbeck.jersey.R.drawable.purple_jersey;
import static ie.ul.davidbeck.jersey.R.string.number_format;

public class MainActivity extends AppCompatActivity {

    private TextView mNameTextView, mNumberTextView;
    private ImageView mJerseyImageView;
    private Jersey mCurrentJersey;

    private final static String PREFS = "PREFS";
    private static final String KEY_JERSEY_NAME = "KEY_JERSEY_NAME";
    private static final String KEY_JERSEY_NUMBER = "KEY_JERSEY_NUMBER";
    private static final String KEY_JERSEY_HOME = "KEY_JERSEY_HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNameTextView = findViewById(R.id.textViewName);
        mNumberTextView = findViewById(R.id.textViewNumber);
        mJerseyImageView = findViewById(R.id.imageViewJersey);

        String mName = prefs.getString(KEY_JERSEY_NAME, getString(R.string.name_default));
        int mNumber = Integer.parseInt(prefs.getString(KEY_JERSEY_NUMBER, getString(R.string.number_default)));
        boolean mIsHomeJersey = Boolean.parseBoolean(prefs.getString(KEY_JERSEY_HOME, getString(R.string.home_default)));
        mCurrentJersey = new Jersey(mName, mNumber, mIsHomeJersey);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editJersey();
            }
        });
        showCurrentJersey();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_JERSEY_NAME, mCurrentJersey.getJerseyName());
        editor.putString(KEY_JERSEY_NUMBER, "" + mCurrentJersey.getJerseyNumber());
        editor.putString(KEY_JERSEY_HOME, "" + mCurrentJersey.isHomeJersey());
        editor.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_reset:
                mCurrentJersey = new Jersey(getString(R.string.name_default),
                                            Integer.parseInt(getString(R.string.number_default)),
                                            Boolean.parseBoolean(getString(R.string.home_default)));
                showCurrentJersey();
                return true;
            case R.id.action_settings:
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void editJersey(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.dialog_edit, null, false);
        builder.setView(view);
        final EditText nameEditText = view.findViewById(R.id.edit_name);
        final EditText numberEditText = view.findViewById(R.id.edit_number);
        final ToggleButton mHomeJerseyButton = view.findViewById(R.id.color_button);

        nameEditText.setText(mCurrentJersey.getJerseyName());
        numberEditText.setText("" + mCurrentJersey.getJerseyNumber());
        mHomeJerseyButton.setChecked(mCurrentJersey.isHomeJersey());
        mHomeJerseyButton.setBackgroundColor(getResources().getColor(mCurrentJersey.isHomeJersey() ? homeJersey : awayJersey));

        mHomeJerseyButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    buttonView.setBackgroundColor(getResources().getColor(homeJersey));
                else
                    buttonView.setBackgroundColor(getResources().getColor(awayJersey));
            }
        });

        AlertDialog.Builder builder1 = builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameEditText.getText().toString();
                int number;
                try {
                    number = Integer.parseInt(numberEditText.getText().toString());
                } catch (NumberFormatException e){
                    number = 0;
                }

                mCurrentJersey.setJerseyName(name);
                mCurrentJersey.setJerseyNumber(number);
                mCurrentJersey.setHomeJersey(mHomeJerseyButton.isChecked());
                showCurrentJersey();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();

    }

    private void showCurrentJersey(){

            mNameTextView.setText(mCurrentJersey.getJerseyName());
            mNumberTextView.setText(getString(number_format, mCurrentJersey.getJerseyNumber()));
            if (mCurrentJersey.isHomeJersey()){
                mJerseyImageView.setImageResource(green_jersey);
            } else {
                mJerseyImageView.setImageResource(purple_jersey);
            }
    }
}
