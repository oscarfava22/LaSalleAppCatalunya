package com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import com.practica2.projectes2.lasalle.lasalleappcatalunya.model.User;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.repositories.UsersRepository;
import com.practica2.projectes2.lasalle.lasalleappcatalunya.utils.DatabaseHelper;

import java.util.List;

/**
 * Created by Ramon on 27/04/2018.
 */

public class UsersDB implements UsersRepository{

    private static final String TABLE_NAME = "user";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    private Context context;

    public UsersDB (Context context) {
        this.context = context;
    }

    @Override
    public void addUser(User u) {

        DatabaseHelper helper = DatabaseHelper.getInstance(context);

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, u.getName());
        values.put(COLUMN_SURNAME, u.getSurname());
        values.put(COLUMN_USERNAME, u.getUsername());
        values.put(COLUMN_EMAIL, u.getEmail());
        values.put(COLUMN_PASSWORD, u.getPassword());

        helper.getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    @Override
    public void removeUser(User u) {

        DatabaseHelper helper = DatabaseHelper.getInstance(context);

        String whereClause = COLUMN_USERNAME + "=?";
        String[] whereArgs = {u.getUsername()};

        helper.getWritableDatabase().delete(TABLE_NAME, whereClause, whereArgs);
    }

    @Override
    public void updateUser(User u) {

        DatabaseHelper helper = DatabaseHelper.getInstance(context);

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, u.getName());
        values.put(COLUMN_SURNAME, u.getSurname());
        values.put(COLUMN_EMAIL, u.getEmail());
        values.put(COLUMN_PASSWORD, u.getPassword());

        String whereClause = COLUMN_USERNAME + "=?";
        String[] whereArgs = {u.getUsername()};

        helper.getWritableDatabase().update(TABLE_NAME, values, whereClause, whereArgs);
    }

    @Override
    public boolean existsUser(String username) {

        DatabaseHelper helper = DatabaseHelper.getInstance(context);

        String whereClause = COLUMN_USERNAME + "=?";
        String[] whereArgs = {username};

        SQLiteDatabase database = helper.getReadableDatabase();

        long count = DatabaseUtils.queryNumEntries(database, TABLE_NAME, whereClause, whereArgs);

        return count > 0;
    }

    @Override
    public boolean logInSuccessful(String username, String password) {

        DatabaseHelper helper = DatabaseHelper.getInstance(context);

        String whereClause = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] whereArgs = {username, password};

        SQLiteDatabase database = helper.getReadableDatabase();

        long count = DatabaseUtils.queryNumEntries(database, TABLE_NAME, whereClause, whereArgs);

        return count > 0;
    }

    @Override
    public boolean logInSuccessfulByEmail(String email, String password) {

        DatabaseHelper helper = DatabaseHelper.getInstance(context);

        String whereClause = COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] whereArgs = {email, password};

        SQLiteDatabase database = helper.getReadableDatabase();

        long count = DatabaseUtils.queryNumEntries(database, TABLE_NAME, whereClause, whereArgs);

        return count > 0;
    }

    @Override
    public User getUser(String username) {

        User user = null;
        DatabaseHelper helper = DatabaseHelper.getInstance(context);

        String whereClause = COLUMN_USERNAME + "=?";
        String[] whereArgs = {username};

        Cursor cursor = helper.getReadableDatabase().query(TABLE_NAME, null, whereClause, whereArgs, null, null, null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                String userName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String userSurname = cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME));
                String userEmail = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String userPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

                user = new User(userName, userSurname, username, userEmail, userPassword);
            }
            cursor.close();
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = null;
        DatabaseHelper helper = DatabaseHelper.getInstance(context);

        Cursor cursor = helper.getReadableDatabase().query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                do {

                    String userName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    String userSurname = cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME));
                    String userUsername = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
                    String userEmail = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                    String userPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));

                    User user = new User(userName, userSurname, userUsername, userEmail, userPassword);
                    users.add(user);

                }while (cursor.moveToNext());
            }
            cursor.close();
        }

        return users;
    }
}
