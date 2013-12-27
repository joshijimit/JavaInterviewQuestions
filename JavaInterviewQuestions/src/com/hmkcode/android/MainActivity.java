package com.hmkcode.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button mBtn1 = (Button) findViewById(R.id.button1);
        mBtn1.setOnClickListener(this);
		
       /* MySQLiteHelper db = new MySQLiteHelper(this);
        try {
			db.createDataBase();
			db.openDataBase();
			db.getAllBooks();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			db.close();
		}*/
       
       /* *//**
         * CRUD Operations
         * *//*
        // add Books
        db.addBook(new Book("Android Application Development Cookbook", "Wei Meng Lee"));   
        db.addBook(new Book("Android Programming: The Big Nerd Ranch Guide", "Bill Phillips and Brian Hardy"));       
        db.addBook(new Book("Learn Android App Development", "Wallace Jackson"));
        
        // get all books
        List<Book> list = db.getAllBooks();
        
        // delete one book
        db.deleteBook(list.get(0));*/
        
        // get all books
      

        
	}
	
	
	
	@Override
	public void onClick(View v) {
		Log.i("clicks","You Clicked Start");
        Intent i=new Intent(MainActivity.this, MainActivity2.class);
        startActivity(i);
	}
}
