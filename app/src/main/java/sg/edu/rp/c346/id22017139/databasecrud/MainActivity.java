package sg.edu.rp.c346.id22017139.databasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnEdit, btnRetrieve;
    TextView tvDBContent;
    EditText etContent;
    ArrayList<Note> al;
    ListView lv;
    ArrayAdapter<Note> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the variables with UI here
        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnRetrieve =findViewById(R.id.btnRetrieve);
        tvDBContent = findViewById(R.id.tvContent);
        etContent = findViewById(R.id.etContent);
        lv = findViewById(R.id.lv);

        al = new ArrayList<Note>();
        aa = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        btnAdd.setOnClickListener(v -> {
            String data = etContent.getText().toString();
            DBHelper dbh = new DBHelper(MainActivity.this);
            long inserted_id = dbh.insertNote(data);

            if(inserted_id != -1) {
                al.clear();
                al.addAll(dbh.getAllNotes());
                aa.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Insert succesfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnRetrieve.setOnClickListener(v -> {
            DBHelper dbh = new DBHelper(MainActivity.this);
            al.clear();
            String filtertext = etContent.getText().toString().trim();
            if(filtertext.length() == 0) {
                al.addAll(dbh.getAllNotes());
            } else {
                al.addAll(dbh.getAllNotes(filtertext));
            }
            aa.notifyDataSetChanged();
        });

        btnEdit.setOnClickListener(v -> {
            Note target = al.get(0);

            Intent i = new Intent(MainActivity.this, EditActivity.class);
            i.putExtra("data", target);
            startActivity(i);
        });

        lv.setOnItemClickListener((parent, view, position, id) -> {
            Note data = al.get(position);
            Intent i = new Intent(MainActivity.this, EditActivity.class);
            i.putExtra("data", data);
            startActivity(i);
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        btnRetrieve.performClick();
    }
}