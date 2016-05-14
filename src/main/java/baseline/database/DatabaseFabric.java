package baseline.database;

import sbt.datapipe.DataPipe;

import java.util.Properties;

/**
 * Created by Home on 10.11.15.
 */
public class DatabaseFabric {


    public static DataPipe fromURN(String urn){
        String[] path = urn.split(":");
        Properties props = new Properties();
        if(path.length > 1){
            if(path[0].equals("jdbc")){
                String[] user_conn = urn.split("/");
                String[] user_pass = user_conn[1].split("@");
                props.setProperty("url",user_conn[0]);
                props.setProperty("login",user_pass[0]);
                props.setProperty("password",user_pass[0]);
                props.setProperty("jdbc","yes");
            }else if(path[0].equals("jndi")){
                props.setProperty("jndi","yes");
                props.setProperty("url",path[1]);

            }

                return new DataPipe(props);
        }
        return null;
    }

    public static DataPipe fromUrl(String url, String login, String password){
        String[] path = url.split(":");
        Properties props = new Properties();
        if(path[0].equalsIgnoreCase("jdbc")){
            props.setProperty("jdbc","yes");
        }else{
            props.setProperty("jndi","yes");
        }
        props.setProperty("url",url);
        props.setProperty("login",login);
        props.setProperty("password",password);
        return new DataPipe(props);
    }

    public static String createURN(String url, String login, String password){
        String[] path = url.split(":");
        if(path[0].equalsIgnoreCase("jdbc")){
            return url+"/"+login+"@"+password;
        }else{
            return "jndi:"+url;
        }
    }

    public static Boolean checkURN(String urn){
        String[] path = urn.split(":");
        if(path[0].equalsIgnoreCase("jdbc") || path[0].equalsIgnoreCase("jndi"))
            return true;
        else return false;
    }

}
