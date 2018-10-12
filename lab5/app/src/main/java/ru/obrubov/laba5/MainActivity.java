package ru.obrubov.laba5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.logging.Logger;

import ru.obrubov.laba5.fragment.UsersDataDialogFragment;

public class MainActivity extends AppCompatActivity {

    Logger logger = Logger.getGlobal();

    EditText etName;
    EditText etEmail;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editText);
        etEmail = findViewById(R.id.editText2);
        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();
    }


    public void onClickCreate(View view) {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        long id = db.insert("user", null, contentValues);
        if(id > 0) {
            etName.setText("");
            etEmail.setText("");
            Toast toast = Toast.makeText(
                    this,
                    "Пользователь добавлен: id = " + id + ", имя = " + name + ", e-mail = " + email,
                    Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onClickRead(View view) {
        try(Cursor cursor = db.query("user", null, null, null, null, null, null)) {
            StringBuilder infoMessage = new StringBuilder();
            if(cursor.moveToFirst()) {
                int columnIdIndex = cursor.getColumnIndex("id");
                int columnNameIndex = cursor.getColumnIndex("name");
                int columnEmailIndex = cursor.getColumnIndex("email");

                do {
                    int id = cursor.getInt(columnIdIndex);
                    String name = cursor.getString(columnNameIndex);
                    String email = cursor.getString(columnEmailIndex);
                    infoMessage
                            .append("id = ").append(id)
                            .append(", имя = ").append(name)
                            .append(", e-mail = ").append(email)
                            .append("\n");
                } while(cursor.moveToNext());
            }
            UsersDataDialogFragment dialog = new UsersDataDialogFragment();
            Bundle args = new Bundle();
            args.putString("text", infoMessage.toString());
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "users");
        }
    }

    public void onClickClear(View view) {
        db.delete("user", null, null);
        Toast toast = Toast.makeText(
                this,
                "Таблица пользователей очищена",
                Toast.LENGTH_LONG
        );
        toast.show();
    }

    class DBHelper extends SQLiteOpenHelper {

        Context context;

        public DBHelper(Context context) {
            super(context, "app", null, 1);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY, name TEXT, email TEXT)");
            Toast toast = Toast.makeText(
                    this.context,
                    "База данных создана",
                    Toast.LENGTH_LONG);
            toast.show();
            logger.info("Database created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Toast toast = Toast.makeText(
                    this.context,
                    "База данных обновлена",
                    Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
