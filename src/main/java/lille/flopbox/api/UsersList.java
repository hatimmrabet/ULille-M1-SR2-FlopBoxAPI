package lille.flopbox.api;

import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class UsersList {
    
    private static UsersList instance = null;
    private HashMap<String, User> users;
    
    private UsersList()
    {
        JsonObject jsonUsers = FileManager.getJsonFileContent("users.json");
        this.users = new HashMap<>();
        for(String key :  jsonUsers.keySet())
        {
            User u = new User(jsonUsers.get(key).asJsonObject());
            this.users.put(key, u);
        }
    }

    public static UsersList getInstance()
    {
        if(instance == null)
            instance = new UsersList();
        return instance;
    }


    public HashMap<String,User> getUsers()
    {
        return this.users;
    }


    public User getUserByUsername(String username)
    {
        return this.users.get(username);
    }

    /**
     * recuperer les serveurs d'un utilisateurs
     * @param username
     * @return
     */
    public HashMap<String,String> getServeursByUsername(String username)
    {
        return this.users.get(username).serveurs;
    }
    
    /**
     * convertir la class UsersList a un object json qui contient des objet json de class User
     * @return
     */
    public JsonObject getUsersJSON()
    {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        for(User u : this.users.values()){
            builder.add(u.username, u.getUserJson());
        }
        return builder.build();
    }
}
