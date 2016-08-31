package com.inrhythm.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inrhythm.pojo.InRhythmResponse;
import com.inrhythm.pojo.Posts;
import com.inrhythm.service.IJsonService;

public class JsonService implements IJsonService
{
	private static int RECORD_TO_UPDATE = 3;

	public InRhythmResponse parseJson(String url) throws Exception 
	{
		Posts[] postArray = readJsonFromUrl(url);
		
		//Converting the array to set to identify unique object
		Set<Integer> userIds = new HashSet<Integer>();
		for(Posts post : postArray)
		{
			userIds.add(post.getUserId());
		}
		System.out.println("Step 2 : Unique User Count Identified : "+ userIds.size());
			
		//updating the 4th item title & Body to InRhythm
		postArray[RECORD_TO_UPDATE].setTitle("inRhythm");
		postArray[RECORD_TO_UPDATE].setBody("inRhythm");
		List<Posts> posts = Arrays.asList(postArray);
		System.out.println("Step 3 : Updated the Title and Body of 4th record");
		
		//setting the response object
		InRhythmResponse response = new InRhythmResponse();
		response.setUserCount(userIds.size());
		response.setPosts(posts);
		System.out.println("Step 4 : Response object created");
		
		return response;
	}
	
	
	public Posts[] readJsonFromUrl(String url) throws IOException 
	{
//		InputStream is = new URL(url).openStream();
		
		URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("User-Agent", 
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
//		connection.connect();
		
		Posts[] postArray = null;
        try 
        {
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();

            int cp;
            while ((cp = rd.read()) != -1) 
            {
                sb.append((char) cp);
            }
            
            ObjectMapper mapper = new ObjectMapper();
            postArray = mapper.readValue(sb.toString(), Posts[].class);
            System.out.println("Step 1 : JSON Retrieved from URL");
        } 
        catch (JSONException e) 
        {
        	System.out.println("Exception while retriving JSON");
            e.printStackTrace();
        } 
        finally 
        {
            connection.getInputStream().close();
        }
        return postArray;
    }

}
