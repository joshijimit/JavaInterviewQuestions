package com.evolvision.android.utility;

import java.text.Normalizer;

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

	            highlighted.setSpan(new BackgroundColorSpan(Color.YELLOW), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

	            start = normalizedText.indexOf(search, spanEnd);
	        }

	        return highlighted;
	    }
	}
}
