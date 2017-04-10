<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:stylesheet src="application.css"/>
    <asset:stylesheet src="hubbub.css"/>

    <g:layoutHead/>
</head>
<body>
    <div id="hd">
        <g:link uri="/">
            <g:img id="logo" src="headerlogo.png" alt="hubbub logo"/>
        </g:link>
    </div>
    <div>
        <g:layoutBody/>
    </div>

    <div class="footer" role="contentinfo">
        <div id="footerText">Hubbub - Social Networking on Grails</div>
    </div>

    <div id="spinner" class="spinner" style="display:none;">
        <g:message code="spinner.alt" default="Loading&hellip;"/>
    </div>

    <asset:javascript src="application.js"/>

</body>
</html>
