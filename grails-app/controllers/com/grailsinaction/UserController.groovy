package com.grailsinaction

class UserController {
    static scaffold = User

    def search(){}

    def results(String loginId){
        def users = User.where {
            loginId =~ "%${loginId}%"
        }.list()

        return [users: users,
                term: params.loginId,
                totalUsers: User.count()
        ]
    }


}
