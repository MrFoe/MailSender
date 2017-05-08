package com.mycompany.mailsender;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
/**
 *
 * @author Shaplygin
 */
public class ReadToWho {
    

    public ReadToWho(String fileName){
        this.fileName = fileName;
    }
    
    public String readFile(){
        String result = "";
        try{
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null){
                result += line;
                line = reader.readLine();
            }
        }catch (FileNotFoundException exp){
            exp.printStackTrace();
        }catch (IOException exp){
            exp.printStackTrace();
        }
        return result;
    }
    
    private String fileName;
}

