package com.inrhythm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.inrhythm.pojo.InRhythmResponse;
import com.inrhythm.service.impl.JsonService;

public class App 
{
	public static void main(String[] args) 
	{
	
		System.out.println("STARTED");
		String url = "http://jsonplaceholder.typicode.com/posts";
		BufferedWriter out = null;

		try 
		{
			JsonService service = new JsonService();
			InRhythmResponse response = service.parseJson(url);
			
			String responsejson = new Gson().toJson(response.getPosts());
			FileWriter file = new FileWriter("c:\\test\\inrhythm.json");
			out = new BufferedWriter(file, 4096);
			out.write(responsejson);
			System.out.println("Step 5 : File created with updated Json - File Path - c:\\test\\inrhythm.json");
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				out.close();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("FINISHED");
	}

}
