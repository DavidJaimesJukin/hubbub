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

    void "Ensure posts lined to a user can be retrieved"(){
        given: "A user with several posts"
        def user = new User(loginId: 'joe', password: 'secret')
        user.save(failOnError:true)
        user.addToPosts(new Post(content: 'First'))
        user.addToPosts(new Post(content: 'Second'))
        user.addToPosts(new Post(content: 'Third'))

        when:"The user is retrieved by their id"
        def foundUser = User.get(user.id)
        def sortedPostContent = foundUser.posts.collect{
            it.content
        }.sort()

        then: "The posts appear on the retrieved user"
        sortedPostContent == ['First', 'Second', 'Third']
    }

    void "Exercise tagging several posts with various tags"(){
        given: "A user with a set of tags"
        def user = new User(loginId: 'joe', password: 'secret')
        user.save(failOnError: true)
        def tagGroovy = new Tag(name: 'groovy')
        def tagGrails = new Tag(name: 'grails')
        user.addToTags(tagGroovy)
        user.addToTags(tagGrails)

        when:"The user tags two fresh posts"
        def groovyPost = new Post(content: "A groovy post")
        user.addToPosts(groovyPost)
        groovyPost.addToTags(tagGroovy)

        def bothPost = new Post(content: "A groovy and grails post")
        user.addToPosts(bothPost)
        bothPost.addToTags(tagGroovy)
        bothPost.addToTags(tagGrails)

        then:
        user.tags*.name.sort() == ['grails', 'groovy']
        1 == groovyPost.tags.size()
        2 == bothPost.tags.size()
    }
}
