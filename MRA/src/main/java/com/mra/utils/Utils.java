package com.mra.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public static void closeDB(Connection con,ResultSet rs,PreparedStatement ps) throws SQLException
{
	if(con!=null)
	{
		con.close();
	}
	if(rs!=null)
	{
		rs.close();
	}
	if(ps!=null)
	{
		ps.close();
	}
		
	
}
}
