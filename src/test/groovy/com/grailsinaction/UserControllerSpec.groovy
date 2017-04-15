package com.grailsinaction

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(UserController)
@Mock([User, Profile])
class UserControllerSpec extends Specification {
    void "Registring a user with known good parameters"(){
        given: "a set of parameters"
        params.with {
            loginId = "glen_a_smith"
            password = "winning"
            homepage = "https://blogs.bytecode.com.au/glen"
        }

        and: "A set of profile parameters"
        params['profile.fullName'] = 'Glen Smith'
        params['profile.email'] = 'glen@bytecode.com.au'
        params['profile.homepage'] = 'http://blogs.bytecode.com.au'

        when: "the user is registered"
        request.method = 'POST'
        controller.register()

        then: "The user is created and the browser is redirected"
        response.redirectedUrl == '/'
        User.count() == 1
        Profile.count() == 1

    }
}
