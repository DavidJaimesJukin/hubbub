package com.grailsinaction

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Specification
import spock.lang.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(PostService)
@Mock([User, Post])
class PostServiceSpec extends Specification {
    void "Valid posts get saved and added to the user"(){
        given: "A new user in the db"
        new User(loginId: 'chuck_norris', password: 'secret').save(failOnError: true)

        when: "A new post is created by the service"
        def newPost = service.createPost("chuck_norris", "First post!")

        then: "The post returned and added to the user"
        newPost.content == 'First post!'
        User.findByLoginId('chuck_norris').posts.size() == 1
    }
}
