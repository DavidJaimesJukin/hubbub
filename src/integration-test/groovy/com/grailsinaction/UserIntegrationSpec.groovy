package com.grailsinaction


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class UserIntegrationSpec extends Specification {
    void "Saving you first user to the database"(){
        given:"a brand new user"
        def joe = new User(loginId: 'Joe', password: 'secret')
        when:"The user is saved"
        joe.save()
        then:"is saed successfully and can be found in the database"
        joe.errors.errorCount == 0
        joe.id != null
        User.get(joe.id).loginId == joe.loginId
    }

    void "Updating a saved users changes its properties"(){
        given: "An existing user"
        def existingUser = new User(loginId: 'joe', password: 'secret')
        existingUser.save(failOnError:true)
        when: "A property is changed"
        def foundUser = User.get(existingUser.id)
        foundUser.password = 'seasame'
        foundUser.save(failOnError:true)
        then:"The change is reflected in the database"
        User.get(existingUser.id).password == 'seasame'
    }

    void "Deleting an existing user removes it from the database"(){
        given: "An existing user"
        def user = new User(loginId: 'joe', password: 'secret')
        user.save(failOnError: true)

        when: "the user is deleted"
        def foundUser = User.get(user.id)
        foundUser.delete(flush:true)

        then:"The user is removed from the database"
        !User.exists(foundUser.id)
    }
//TODO: integrate the tests for the profile class
    void "Saving a user with invalid properties causes an error"(){
        given: "A user which fails several field validations"
        def user = new User(loginId: 'joe', password: 'tiny')
        def profile = new Profile(user: user, homepage: 'not-a-url')
        user.profile = profile

        when: "The user and the profile are validated"
        user.validate()
        //profile.validate()

        then:
        user.hasErrors()

        "size.toosmall" == user.errors.getFieldError("password").code
        "tiny" == user.errors.getFieldError("password").rejectedValue

       // profile.hasErrors()
        //"url.invalid" == user.errors.getFieldError("homepage").code
        //"not-a-url" == user.errors.getFieldError("homepage").rejectedValue
    }

    void "Recovering from a failed save by fixing invalid properties"(){
        given: "A user that has invalid properties"
        def chuck = new User(loginId: 'chuck', password: 'tiny')
        assert chuck.save() == null
        assert chuck.hasErrors()

        when:"We fix the invalid properties"
        chuck.password = 'fistfist'
        //def profile = new Profile(user: chuck)
        //chuck.profile = profile
        //chuck.profile.homepage = 'http://www.chucknorrisfacts.com'
        chuck.validate()

        then:"Then the user saves and validates fine"
        !chuck.hasErrors()
        chuck.save()
    }

    void "Enusre a user can follow other users"(){
        given: "A set of baseline users"
        def joe = new User(loginId: 'joe', password: 'secret').save()
        def jane = new User(loginId: 'jane', password: 'secret').save()
        def jill = new User(loginId: 'jill', password: 'secret').save()

        when: "Joe follows Jane & Jill, and Jill follows Jane"
        joe.addToFollowing(jane)
        joe.addToFollowing(jill)
        jill.addToFollowing(jane)

        then: "The follower counts should match following poeple"
        2 == joe.following.size()
        1 == jill.following.size()
    }
}
