package com.grailsinaction


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class UserIntegrationSpec extends Specification {
    void "Saving you first user to the database"(){
        given:"a brand new user"
        def joe = new User(loginId: 'Joe', password: 'secret',homepage: 'http://www.grailsinaction.com')
        when:"The user is saved"
        joe.save()
        then:"is saed successfully and can be found in the database"
        joe.errors.errorCount == 0
        joe.id != null
        User.get(joe.id).loginId == joe.loginId
    }
}
