<html>
    <head>
        <title>
            Timeline for ${user.profile ? user.profile.fullName: user.loginId}
        </title>

        <meta name="layout" content="main"/>
    </head>

    <body>
        <h1>Timeline for ${user.profile ? user.profile.fullName: user.loginId}</h1>

        <div id="allPosts">
            <g:each in="${user.posts}" var="post">
                <div class="postEntry">
                    <div class="postText">
                        ${post.content}
                    </div>

                    <div class="postDate">
                        ${post.dateCreated}
                    </div>
                </div>
            </g:each>
        </div>
    </body>
</html>