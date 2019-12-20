package ru.ankhell.config;

import java.util.Properties;

public class Config
{
    final Properties configFile;
    @SuppressWarnings("ConstantConditions")
    public Config(String filename)
    {
        configFile = new java.util.Properties();
        try {
            configFile.load(this.getClass().getClassLoader().
                    getResourceAsStream(filename));
        }catch(Exception eta){
            System.out.println("Can't load config file! Loading default config");
            configFile.put("URI","https://jsonplaceholder.typicode.com/");
            configFile.put("LOG","false");
        }
    }

    public String getProperty(String key)
    {
        return this.configFile.getProperty(key);
    }
}
