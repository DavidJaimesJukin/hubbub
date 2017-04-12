package com.grailsinaction


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class QueryIntegrationSpec extends Specification {
    void "Simple property comparison"(){
        when: "Users are selected by a password match"
        def users = User.where{
            password == 'testing'
        }.list(sort: 'loginId')

        then: "The users with that password are returned"
        users*.loginId == ['Frankie']
    }

    void "Multiple criteria"(){
        when:"A user is selected by loginId or password"
        def users = User.where {
        loginId == 'Frankie' || password == 'crickey'
        }.list(sort: 'loginId')

        then: "The matching loginIds are returned"
        users*.loginId == ['Dillon', 'Frankie', 'Sara']
    }

    void "Query on association"(){
        when:"The 'following' collection is queried "
        def users = User.where {
            following.loginId == 'Sara'
        }.list(sort: 'loginId')

        then: "A list of the followers of the given username is returned"
        users*.loginId == ['Phil']
    }

    void "Query against a range value"(){
        given: "The current date and time"
        def now = new Date()

        when: "The 'datecreated' property is queried"
        def users = User.where {
            dateCreated in (now - 1)..now
        }.list(sort: 'loginId', order: 'desc')

        then: "The users created in the specified date range are returned"
        users*.loginId == ['Phil', 'Peter', 'Glen', 'Frankie', 'chuck_norris', "admin"]
    }

    void "Return a single instance"(){
        when: "A specific user is queried with get()"
        def user = User.where {
            loginId == 'Phil'
        }.get()

        then: "The users is returned"
        user.password == 'thomas'

    }
}
