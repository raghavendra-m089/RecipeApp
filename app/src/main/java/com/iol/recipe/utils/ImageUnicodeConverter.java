package com.iol.recipe.utils;

import android.net.Uri;

public class ImageUnicodeConverter {
	private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

	public static String getImageUnicode(String imageURL) {
		String urlEncoded = Uri.encode(imageURL, ALLOWED_URI_CHARS);
		return urlEncoded;
	}

}
