package com.example.yura.mysqliteexample1;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.example.yura.mysqliteexample1.data.HotelContract;
import com.example.yura.mysqliteexample1.data.HotelContract.GuestEntry;
import com.example.yura.mysqliteexample1.data.HotelDbHelper;

import static com.example.yura.mysqliteexample1.data.HotelDbHelper.LOG_TAG;
import static java.lang.Integer.parseInt;

public class  EditorActivity extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mCityEditText;
    private EditText mAgeEditText;
    private EditText mIdEditText;

    private Spinner mGenderSpinner;

    Button btnAdd, btnRead, btnClear, btnUpd, btnDel;
    EditText etName, etEmail, etID;

    /**
     * Пол для гостя. Возможные варианты:
     * 0 для кошки, 1 для кота, 2 - не определен.
     */
    private int mGender = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        mIdEditText =  (EditText) findViewById(R.id.ID_guest);
        mNameEditText = (EditText) findViewById(R.id.edit_guest_name);
        mCityEditText = (EditText) findViewById(R.id.edit_guest_city);
        mAgeEditText = (EditText) findViewById(R.id.edit_guest_age);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        setupSpinner();
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickS(v);
            }
        });
        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickS(v);
            }
        });
        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickS(v);
            }
        });
        btnUpd = (Button) findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickS(v);
            }
        });
        btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickS(v);
            }
        });


    }

    public void onClickS(View v) {
        String Message = "";
        Toast toast;

        // создаем объект для данных
//        ContentValues cv = new ContentValues();
        ContentValues values = new ContentValues();

        // получаем данные из полей ввода
        String gid1 = mIdEditText.getText().toString();
        String gname = mNameEditText.getText().toString();
        String gcity= mCityEditText.getText().toString();
        String gage = mAgeEditText.getText().toString();
        String gspiner= mGenderSpinner.toString();

        // подключаемся к БД
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
        HotelDbHelper mDbHelper = new HotelDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Зададим условие для выборки - список столбцов
        String[] projection = {
                GuestEntry._ID,
                GuestEntry.COLUMN_NAME,
                GuestEntry.COLUMN_CITY,
                GuestEntry.COLUMN_GENDER,
                GuestEntry.COLUMN_AGE };

        values = new ContentValues();
        values.put(HotelContract.GuestEntry.COLUMN_NAME, gname);
        values.put(HotelContract.GuestEntry.COLUMN_CITY, gcity);
        values.put(HotelContract.GuestEntry.COLUMN_GENDER, mGender);
        values.put(HotelContract.GuestEntry.COLUMN_AGE, gage);
////////////////////////////////////////////////////////////////////////////////////////////////

        switch (v.getId()) {
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца -
                // значение



                // вставляем запись и получаем ее ID
                long rowID = db.insert(HotelContract.GuestEntry.TABLE_NAME, null, values);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                Message = "Запись вставлена";
                toast = Toast.makeText(getBaseContext(), Message, Toast.LENGTH_LONG);
                toast.show();
                mNameEditText.setText("");
                mCityEditText.setText("");
                mAgeEditText.setText("");
                mGenderSpinner.setSelection(2);
                break;

////////////////////////////////////////////////////////////////////////////////////////////////

            case R.id.btnRead:
                Cursor cursor = db.query(
                        GuestEntry.TABLE_NAME,   // таблица
                        projection,            // столбцы
                        null,                  // столбцы для условия WHERE
                        null,                  // значения для условия WHERE
                        null,                  // Don't group the rows
                        null,                  // Don't filter by row groups
                        null);                   // порядок сортировки


                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (cursor.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColumnIndex = cursor.getColumnIndex(GuestEntry._ID);
                    int nameColumnIndex = cursor.getColumnIndex(GuestEntry.COLUMN_NAME);
                    int cityColumnIndex = cursor.getColumnIndex(GuestEntry.COLUMN_CITY);
                    int genderColumnIndex = cursor.getColumnIndex(GuestEntry.COLUMN_GENDER);
                    int ageColumnIndex = cursor.getColumnIndex(GuestEntry.COLUMN_AGE);


                    do {
                        { Log.d(LOG_TAG,
                                "ID = " + cursor.getString(idColumnIndex) + ", name = "
                                        + cursor.getString(nameColumnIndex) + ", city = "
                                        + cursor.getString(cityColumnIndex) + ", пол ="
                                        + cursor.getString(genderColumnIndex) + ", возраст ="
                                        + cursor.getString(ageColumnIndex));}

if (gid1.equals(cursor.getString(idColumnIndex)))
{
    mIdEditText.setText(cursor.getString(idColumnIndex));
    mNameEditText.setText(cursor.getString(nameColumnIndex));
    mCityEditText.setText(cursor.getString(cityColumnIndex));
    mAgeEditText.setText(cursor.getString(ageColumnIndex));
//    mGenderSpinner.setSelection(parseInt(cursor.getString(genderColumnIndex)));
    mGenderSpinner.setSelection(cursor.getInt(genderColumnIndex));

}

                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false -
                        // выходим из цикла
                    } while (cursor.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                cursor.close();
                break;
////////////////////////////////////////////////////////////////////////////////////////////////
            case R.id.btnClear:

                Message = "Поля очищены";
                toast = Toast.makeText(getBaseContext(), Message, Toast.LENGTH_LONG);
                toast.show();

                mIdEditText.setText("");
                mNameEditText.setText("");
                mCityEditText.setText("");
                mAgeEditText.setText("");
                mGenderSpinner.setSelection(2);
                break;
////////////////////////////////////////////////////////////////////////////////////////////////
            case R.id.btnUpd:
                if (!gid1.equals(""))
            {
                db.update(GuestEntry.TABLE_NAME,
                        values,
                        "_ID = ?",
                        new String[]{gid1});
                Message = "Поля обновлены";
                toast = Toast.makeText(getBaseContext(), Message, Toast.LENGTH_LONG);
                toast.show();
            } else {
                    Message = "Ошибка!! Поле с индексом не может быть пустым";
                    toast = Toast.makeText(getBaseContext(), Message, Toast.LENGTH_LONG);
                    toast.show();
                }
        break;
////////////////////////////////////////////////////////////////////////////////////////////////
         case R.id.btnDel:
            if (!gid1.equals(""))
            {
                int delCount = db.delete(GuestEntry.TABLE_NAME, "_ID ="+ gid1, null);
                Message = "Поле удалено";
                toast = Toast.makeText(getBaseContext(), Message, Toast.LENGTH_LONG);
                toast.show();
                mIdEditText.setText("");
                mNameEditText.setText("");
                mCityEditText.setText("");
                mAgeEditText.setText("");
                mGenderSpinner.setSelection(2);

            } else {
                Message = "Ошибка!! Поле с индексом не может быть пустым";
                toast = Toast.makeText(getBaseContext(), Message, Toast.LENGTH_LONG);
                toast.show();
            }
            break;
////////////////////////////////////////////////////////////////////////////////////////////////
        }
        // закрываем подключение к БД
        mDbHelper.close();
    }


    /**
     * Настраиваем spinner для выбора пола у гостя.
     */
    private void setupSpinner() {

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mGenderSpinner.setAdapter(genderSpinnerAdapter);
        mGenderSpinner.setSelection(2);

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {

                    if (selection.equals(getString(R.string.gender_female))) {
                        mGender = HotelContract.GuestEntry.GENDER_FEMALE; // Кошка
                    } else if (selection.equals(getString(R.string.gender_male))) {
                        mGender = HotelContract.GuestEntry.GENDER_MALE; // Кот
                    } else {
                        mGender = HotelContract.GuestEntry.GENDER_UNKNOWN; // Не определен
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 2; // Unknown
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertGuest();
                // Закрываем активность
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Сохраняем введенные данные в базе данных.
     */
    private void insertGuest() {
        // Считываем данные из текстовых полей
        String name = mNameEditText.getText().toString().trim();
        String city = mCityEditText.getText().toString().trim();
        String ageString = mAgeEditText.getText().toString().trim();
        int age = parseInt(ageString);

        HotelDbHelper mDbHelper = new HotelDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HotelContract.GuestEntry.COLUMN_NAME, name);
        values.put(HotelContract.GuestEntry.COLUMN_CITY, city);
        values.put(HotelContract.GuestEntry.COLUMN_GENDER, mGender);
        values.put(HotelContract.GuestEntry.COLUMN_AGE, age);

        // Вставляем новый ряд в базу данных и запоминаем его идентификатор
        long newRowId = db.insert(HotelContract.GuestEntry.TABLE_NAME, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при заведении гостя", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Гость заведён под номером: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

}
