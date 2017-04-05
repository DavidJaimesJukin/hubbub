package com.grailsinaction

class Post {
    String content
    Date datecreated
    static constraints = {
        content blank: false
    }

    static belongsTo = [user: User]
}
