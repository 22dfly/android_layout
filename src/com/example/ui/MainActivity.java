package com.example.ui;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.main_menu, android.R.layout.simple_list_item_1);
        
        ListView listView = (ListView) findViewById(R.id.menu_list);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectMenu(position);
            }
        });
    }
    
    public void selectMenu(int position) {
        Intent intent;
        switch (position) {
            default:
            case 0:
                intent = new Intent(getBaseContext(), FrameLayoutActivity.class);
                break;
            case 1:
                intent = new Intent(getBaseContext(), LinearLayoutActivity.class);
                break;
            case 2:
                intent = new Intent(getBaseContext(), RelativeLayoutActivity.class);
                break;
            case 3:
                intent = new Intent(getBaseContext(), GridLayoutActivity.class);
                break;
            case 4:
                intent = new Intent(getBaseContext(), FlowLayoutActivity.class);
                break;
        }

        startActivity(intent);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
