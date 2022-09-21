package project.services.rdp_sender;

import java.util.HashMap;
import java.util.Map;

public class password_checker {

    private static volatile password_checker passwordChecker;
    private static volatile Map<String,String> passwords;
    private password_checker() {
    }

    public static password_checker get_instance(){
        if (passwordChecker==null){

            synchronized (password_checker.class){
                if (passwordChecker==null){
            passwordChecker=new password_checker();
            passwords=new HashMap<>();
            }
            }
        }

        return passwordChecker;
    }

    public void add_password(String user,String password){
        synchronized (password_checker.class){
            passwords.put(user,password);

        }
    }

    public boolean check_password(String user,String new_password){
        synchronized (password_checker.class){
            if(!passwords.containsKey(user) || !passwords.get(user).equals(new_password)){
            return false;
            }
        return true;
        }
    }
}
