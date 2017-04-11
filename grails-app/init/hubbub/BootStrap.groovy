package hubbub

import com.grailsinaction.Profile
import com.grailsinaction.User
import grails.util.Environment

class BootStrap {

    def init = { servletContext ->
        switch (Environment.current){
            case Environment.DEVELOPMENT:
                createAdminUserIfRequired()
                break
            case Environment.PRODUCTION:
                println("No specials config is required")
                break
        }
    }
    def destroy = {
    }

    void createAdminUserIfRequired(){
        if(User.count() == 0){
            println("Fresh database creating new ADMIN user")
            def profile = new Profile(fullName: 'Adiminstrator', email: "admin@yourhost.com")
            def user = new User(loginId: 'Admin', password: 'secret', profile: profile)
            user.save(failOnError:true)
        } else{
            println("Existing admin user, skipping creation")
        }
    }
}
