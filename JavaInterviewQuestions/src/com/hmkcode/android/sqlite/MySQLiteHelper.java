package com.hmkcode.android.sqlite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hmkcode.android.model.Categories;
import com.hmkcode.android.model.Questions;

public class MySQLiteHelper extends SQLiteOpenHelper {

	// DB Path
	private static String DB_PATH = "/data/data/com.hmkcode.android/databases/";

	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "Interviews";

	private SQLiteDatabase myDataBase;

	private final Context myContext;

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {
		
		Log.d("App Started", "Java Interview Que" );
		boolean dbExist = checkDataBase();

		if (dbExist) {
			Log.d("App Started", "DB exists" );
			String mPath = DB_PATH + DATABASE_NAME;
			SQLiteDatabase db_Read2 = SQLiteDatabase.openDatabase(mPath, null,
					SQLiteDatabase.OPEN_READONLY);
			int versionOfActiveDatabase = db_Read2.getVersion();
			db_Read2.close();

			if (DATABASE_VERSION > versionOfActiveDatabase) {
				// Force call to upgrade the database
				// SQLiteDatabase parameter is not used so passing in referenced
				// to closed db not an issue
				onUpgrade(db_Read2, versionOfActiveDatabase, DATABASE_VERSION);				
				
				try {
					SQLiteDatabase db =this.getReadableDatabase();
					if (db.isOpen()){
						db.close();
					}
					copyDataBase();
				} catch (IOException e) {

					throw new Error("Error copying database");

				}
			}
		} else {
			Log.d("App Started", "DB not exists" );
			File f = new File(DB_PATH);
			if (!f.exists()) {
				f.mkdir();
			}
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			
			try {
				this.close();
				SQLiteDatabase db =this.getReadableDatabase();
				if (db.isOpen()){
					db.close();
				}
				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

		try {

			// Path to the just created empty db
			String outFileName = DB_PATH + DATABASE_NAME;

			// Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outFileName);

			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}

			SQLiteDatabase checkDB = null; // get a reference to the db..

			try {
				String myPath = DB_PATH + DATABASE_NAME;
				checkDB = SQLiteDatabase.openDatabase(myPath, null,
						SQLiteDatabase.OPEN_READWRITE);

				// once the db has been copied, set the new version..
				checkDB.setVersion(DATABASE_VERSION);
			} catch (SQLiteException e) {
				// database does’t exist yet.
			}

			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();

		} catch (IOException e) {

			// Also if your databse does not copy over a new version, it’s
			// because the checkDataBase() will check, see a databaser and
			// return true, copy() will never be called. For a hack, throw
			// copy() in:

		}

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DATABASE_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.CREATE_IF_NECESSARY);
		// myDataBase = SQLiteDatabase.openDatabase(myPath, null,
		// SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	// Add your public helper methods to access and get content from the
	// database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd
	// be easy
	// to you to create adapters for your views.

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*
		 * // SQL statement to create Category table String
		 * CREATE_Category_TABLE = "CREATE TABLE Categories ( " +
		 * "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "title TEXT, "+
		 * "author TEXT )";
		 * 
		 * // create Categories table db.execSQL(CREATE_Category_TABLE);
		 */

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		this.close();
		myContext.deleteDatabase(DATABASE_NAME);

	}

	// ---------------------------------------------------------------------

	// Categories table name
	private static final String TABLE_Categories = "Categories";

	// Categories Table Columns names
	private static final String KEY_ID = "_ID";
	private static final String KEY_TITLE = "CategoryName";
	private static final String KEY_AUTHOR = "CategoryDesc";

	private static final String[] COLUMNS = { KEY_ID, KEY_TITLE, KEY_AUTHOR };

	public void addCategory(Categories Category) {
		Log.d("addCategory", Category.toString());
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, Category.getCategoryName()); // get title
		values.put(KEY_AUTHOR, Category.getCategoryDesc()); // get author

		// 3. insert
		db.insert(TABLE_Categories, // table
				null, // nullColumnHack
				values); // key/value -> keys = column names/ values = column
							// values

		// 4. close
		db.close();
	}

	public Categories getCategory(int id) {

		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();

		// 2. build query
		Cursor cursor = db.query(TABLE_Categories, // a. table
				COLUMNS, // b. column names
				" id = ?", // c. selections
				new String[] { String.valueOf(id) }, // d. selections args
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit

		// 3. if we got results get the first one
		if (cursor != null)
			cursor.moveToFirst();

		// 4. build Category object
		Categories Category = new Categories();
		Category.set_ID(Integer.parseInt(cursor.getString(0)));
		Category.setCategoryName(cursor.getString(1));
		Category.setCategoryDesc(cursor.getString(2));

		Log.d("getCategory(" + id + ")", Category.toString());

		// 5. return Category
		return Category;
	}

	// Get All Categories
	public List<Categories> getAllCategories() {
		List<Categories> categories = new LinkedList<Categories>();

		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_Categories;

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build Category and add it to list
		Categories Category = null;
		if (cursor.moveToFirst()) {
			do {
				Category = new Categories();
				Category.set_ID(Integer.parseInt(cursor.getString(0)));
				Category.setCategoryName(cursor.getString(1));
				Category.setCategoryDesc(cursor.getString(2));

				// Add Category to Categories
				categories.add(Category);
			} while (cursor.moveToNext());
		}

		Log.d("getAllCategories()", categories.toString());

		// return Categories
		return categories;
	}

	// Get All Categories
	public List<Questions> getQustionsByCategory(String categoryName) {
		List<Questions> questionsList = new LinkedList<Questions>();

		// 1. build the query
		String query = "select q._ID,questions,answer from questions q inner join Answers a on q._id = a.questionid where a.iscorrect = 1 and categoryid = "
				+ "(select _ID from categories where categoryname = '"
				+ categoryName + "')";

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build Category and add it to list
		Questions questions = null;
		if (cursor.moveToFirst()) {
			do {
				questions = new Questions();
				questions.set_ID(cursor.getInt(0));
				questions.setQuestion(cursor.getString(1));
				questions.setAnswer(cursor.getString(2));

				// Add Category to Categories
				questionsList.add(questions);
			} while (cursor.moveToNext());
		}

		Log.d("getAllCategories()", questionsList.toString());

		// return Categories
		return questionsList;
	}

	// Updating single Category
	public int updateCategory(Categories category) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put("title", category.getCategoryName()); // get title
		values.put("author", category.getCategoryDesc()); // get author

		// 3. updating row
		int i = db.update(TABLE_Categories, // table
				values, // column/value
				KEY_ID + " = ?", // selections
				new String[] { String.valueOf(category.get_ID()) }); // selection
																		// args

		// 4. close
		db.close();

		return i;

	}

	// Deleting single Category
	public void deleteCategory(Categories category) {

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. delete
		db.delete(TABLE_Categories, KEY_ID + " = ?",
				new String[] { String.valueOf(category.get_ID()) });

		// 3. close
		db.close();

		Log.d("deleteCategory", category.toString());

	}
}
