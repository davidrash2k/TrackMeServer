package edu.trackme.util;

import java.security.SecureRandom;
import java.util.Random;

public class UserCodeGeneration {
	
	public UserCodeGeneration(){
	
	}
	
	public String generateCode(){
	
		do{
			Random random = new SecureRandom();
			char[] result = new char[6];
			char[] CHARSET_NUMERIC = "0123456789".toCharArray();
			for (int i = 0; i < result.length; i++) {
				// picks a random index out of character set > random character
				int randomCharIndex = random.nextInt(CHARSET_NUMERIC.length);
				result[i] = CHARSET_NUMERIC[randomCharIndex];
			}
		
		
			if(doesCodeExist(String.valueOf(result)) == -1)
				return String.valueOf(result);
		
		}while(true);
			
	}
	
	public int doesCodeExist(String code){
		int doesExist = -1;
		
		//check in DB
		
		return doesExist;
	}
	
}
