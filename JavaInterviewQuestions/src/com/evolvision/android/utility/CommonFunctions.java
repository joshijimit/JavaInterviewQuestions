package com.evolvision.android.utility;

import java.text.Normalizer;

import com.evolvision.android.MainActivity;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;

public class CommonFunctions {

	public static CharSequence highlight(String search, String originalText) {
	    // ignore case and accents
	    // the same thing should have been done for the search text
	    String normalizedText = Normalizer.normalize(originalText, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

	    int start = normalizedText.indexOf(search);
	    if (start < 0) {
	        // not found, nothing to to
	        return originalText;
	    } else {
	        // highlight each appearance in the original text
	        // while searching in normalized text
	        Spannable highlighted = new SpannableString(originalText);
	        while (start >= 0) {
	            int spanStart = Math.min(start, originalText.length());
	            int spanEnd = Math.min(start + search.length(), originalText.length());
	            if(MainActivity.isNightMode)
	            	highlighted.setSpan(new BackgroundColorSpan(Color.GREEN), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	            else
	            	highlighted.setSpan(new BackgroundColorSpan(Color.YELLOW), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

	            start = normalizedText.indexOf(search, spanEnd);
	        }

	        return highlighted;
	    }
	}
	
	public static String buildShareQuestion(String question,String ans){
		
		StringBuilder builder = new StringBuilder();
		builder.append(question);
		builder.append("\n\n");
		builder.append(ans);
		builder.append("\n\n");
		builder.append("Shared from Java Interview Question.");
		builder.append("\n");
		builder.append("To download this application please visit below URL.");
		builder.append("\n\n");
		builder.append("https://play.google.com/store/apps/details?id=com.evolvision.android");
		builder.append("\n\n");
		builder.append("Regards,");
		builder.append("\n");
		builder.append("Evolving Vision Team.");
		
		return builder.toString();
	}
}
