package com.grailsinaction

class Post {
    String content
    Date datecreated
    static constraints = {
        content blank: false
        //dateCreated nullable: true
    }

    static belongsTo = [user: User]
    static hasMany = [tags: Tag]
    static mapping = {
        sort datecreated: 'desc'
    }
}
