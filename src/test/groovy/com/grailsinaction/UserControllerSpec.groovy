package com.grailsinaction

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

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

    @Unroll
    void "Registration command object for #loginID validate correctly"(){

        given: "A mocked command object"
        def urc = mockCommandObject(UserRegistrationCommand)

        and: "A set of initial values from the spock test"
        urc.loginId = loginId
        urc.password = password
        urc.passwordRepeat = passwordRepeat
        urc.fullName = "Your name here"
        urc.email = "email@example.com"

        when: "The validator is invoked"
        def isValidRegistration = urc.validate()

        then: "The appropiate fields are flagged as errors"
        isValidRegistration == anticipatedValid
        urc.errors.getFieldError(fieldInError)?.code == errorCode

        where:
        loginId | password | passwordRepeat | anticipatedValid | fieldInError| errorCode
        "glen"  | "password" | "no-match"   | false            | "passwordRepeat" | "validator.invalid"
        "peter" | "password" | "password"   | true             | null             | null
        "a"     | "password" | "password"   | false            | "loginId"        | "size.toosmall"

    }
}

