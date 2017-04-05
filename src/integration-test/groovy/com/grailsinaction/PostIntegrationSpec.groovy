package com.grailsinaction


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class PostIntegrationSpec extends Specification {
    void "Adding posts to user links post to user"(){
        given: "A brand new user"
        def user = new User(loginId: 'joe', password: 'secret')
        user.save(failOnError: true)

        when:"Several posts are added to the user"
        user.addToPosts(new Post(content: "first post...w00t"))
        user.addToPosts(new Post(content: "Second post...yas"))
        user.addToPosts(new Post(content: "third post...k"))

        then: "The user has a list of posts attached"
        3 == User.get(user.id).posts.size()
    }
}
