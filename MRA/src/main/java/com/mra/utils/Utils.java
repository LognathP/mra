package com.mra.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Utils {
	

public static List<String> valueToListOfString(String value,String delimiter)
{
	 List<String> result = new ArrayList<>();
	 StringTokenizer stringtokens = new StringTokenizer(value,delimiter);
	 while(stringtokens.hasMoreTokens())
     {
		 result.add(stringtokens.nextToken().trim().replaceAll("[\\[\\](){}]", ""));
     }
    return result;
}

}
