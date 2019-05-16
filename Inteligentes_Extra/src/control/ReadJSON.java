package control;

import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gson.Gson;

public class ReadJSON {
	
	public static Problem Read(String jsonFile) {
		Problem init = new Problem();
		Gson gson = new Gson();
		try {
			BufferedReader br = new BufferedReader(new FileReader(jsonFile));
			init = gson.fromJson(br, Problem.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return init;
	}
}
