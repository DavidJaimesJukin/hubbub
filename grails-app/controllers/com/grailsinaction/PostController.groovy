package com.grailsinaction

class PostController {

    static scaffold = Post

    def timeline(){
        def user = User.findbyLoginId(params.id)

        if(!user){
            response.sendError(404)
        } else {
            [user: user]
        }
    }

}
