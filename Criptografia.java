import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia{
    public Criptografia(){

    }

    public String getMD5(String input) {
        try {
            						// Se instancia MessageDigest con MD5
            byte[] messageDigest = MessageDigest.getInstance("MD5").digest(input.getBytes());		// Se obtiene la huella digital en un array de bytes

            BigInteger number = new BigInteger(1, messageDigest);	// Se convierte en un BigInteger el array de bytes
            String hashtext = number.toString(16);	// 
            while (hashtext.length() < 32) {		// Se convierte el mensaje a hexadecimal
                hashtext = "0" + hashtext;			//
            }										//
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }	

	public String encriptar(String s) throws UnsupportedEncodingException{
        return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));
    }
    
    public String desencriptar(String s) throws UnsupportedEncodingException{
        byte[] decode = Base64.getDecoder().decode(s.getBytes());
        return new String(decode, "utf-8");
    }

    public String sort(String s) { 
        int n = s.length(); 
        char arr[]=s.toCharArray();
        for (int i = 0; i < n-1; i++) { 
            int min_idx = i; 
            for (int j = i+1; j < n; j++) 
                if (((int)arr[j]) < ((int)arr[min_idx])) 
                    min_idx = j; 
            char temp = arr[min_idx]; 
            arr[min_idx] = arr[i]; 
            arr[i] = temp; 
        }         
        return new String(arr);
    }

    public String intercalar(String s1, String s2){
        String res="";
        for(int i=0, j=s2.length()-1; (i<s1.length() || j>=0); i++, j--){           
            if(i<s1.length()){
                if((Math.abs(Math.sqrt(i)))%2==0)
                    res+=s1.charAt(i);
                else
                    res=s1.charAt(i)+res;
            }                
            if(j>=0){
                if((Math.abs(Math.sqrt(j)))%2!=0)
                    res+=s2.charAt(j);
                else
                    res=s2.charAt(j)+res;
            }                
        }
        return res;
    }

    public String mezcla(String pass, String ma){
    	int n=(4096+pass.length());
    	String union=ma+pass;
    	String pares="";
    	String nones="";

    	for(int i=0;i<n; i++){
    		if(((int)(union.charAt(i))%2)==0)
    			pares+=union.charAt(i);
    		else
    			nones+=union.charAt(i);
    	}

        union=intercalar(pares, nones);

    	return union;
    }

    public String md5(String mensaje){
        String msg = getMD5(mensaje); 
        return msg;        
    }
}