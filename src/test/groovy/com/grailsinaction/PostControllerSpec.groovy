package com.grailsinaction

import grails.test.mixin.TestFor
import grails.test.mixin.Mock
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(PostController)
@Mock([User, Post])
class PostControllerSpec extends Specification {

    void "Get users timeline given their id"(){
        given: " A user with posts in the db"
        User chuck = new User(loginId: 'chuck_norris', password: 'secret')
        chuck.addToPosts(new Post(content: 'first post'))
        chuck.addToPosts(new Post(content: 'second post'))
        chuck.save(failOnError: true)

        and: "A loginig parameter"
        params.id = chuck.loginId

        when: "The timeline is invoked"
        def model = controller.timeline()

        then:"The user is in the returned model"
        model.user.loginId == 'chuck_norris'
        model.user.posts.size() == 2
    }
}