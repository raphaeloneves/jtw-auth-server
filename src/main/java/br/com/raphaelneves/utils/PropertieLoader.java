package br.com.raphaelneves.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by raphaeloneves on 09/06/2017.
 */
public class PropertieLoader {

    private Properties properties;
    private InputStream stream;

    public PropertieLoader(String propertieFileName){
        properties = new Properties();
        stream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(propertieFileName);
        try{
            properties.load(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPropertie(String propertie){
        return properties.getProperty(propertie);
    }


}
