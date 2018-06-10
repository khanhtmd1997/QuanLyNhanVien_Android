package e.admin.manager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String DATABASE_NAME = "EmployeeDB.sqlite";
    SQLiteDatabase database;

    ListView listView;
    ArrayList<NhanVien> list;
    AdapterNhanVien adapter;
    Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien",null);
        cursor.moveToFirst();
        Toast.makeText(this,cursor.getString(1),Toast.LENGTH_SHORT).show();

        addControls();
        readData();
    }

    private void addControls() {
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        listView =findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterNhanVien(this,list);
        listView.setAdapter(adapter);
    }
    private void readData(){
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien",null);
        list.clear();
       for (int i = 0;i< cursor.getCount();i++){
           cursor.moveToPosition(i);
           int id = cursor.getInt(0);
           String ten = cursor.getString(1);
           String sdt = cursor.getString(2);
           byte[] anh = cursor.getBlob(3);
           list.add(new NhanVien(id, ten, sdt, anh));
       }
       adapter.notifyDataSetChanged();
    }
}
