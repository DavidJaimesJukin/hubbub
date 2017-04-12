package com.grailsinaction

class PostController {

    static scaffold = Post

    def timeline(){
        def user = User.findByLoginId(params.id)

        if(!user){
            response.sendError(404)
        } else {
            [user: user]
        }
    }

}
