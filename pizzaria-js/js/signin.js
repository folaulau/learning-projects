$(document).ready(function(){
    
    $("#signinEmail").val("folaudev@gmail.com");
    $("#signinPassword").val("Test1234!");

    $("#signinBtn").click(function(){
        console.log("signing up...");
        var email = $("#signinEmail").val();
        var password = $("#signinPassword").val();
        console.log("password="+password);
        console.log("email="+email);
        var authorization = btoa(email+":"+password);

        console.log("authorization - Basic "+authorization);
        
        $.ajax({
            url: 'http:/localhost:8888/api/users/login?type=password',
            type: 'post',
            data: {
                
            },
            headers: {
                "Authorization": "Basic "+authorization,  
                "x-api-key": 'Header Value Two'
            },
            contentType: "application/json",
            fail: function( jqXHR, textStatus, errorThrown ) {
                console.log( 'Could not get posts, server response: ' + textStatus + ': ' + errorThrown );
            }
        }).then(function(data, status) {
            console.log("data");
            console.log(data);
            console.log("status");
            console.log(status);
        });
        // .catch(function(data, status){
        //     console.log("data");
        //     console.log(data);
        //     console.log("status");
        //     console.log(status);
        // });
    });
});