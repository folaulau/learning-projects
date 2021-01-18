$(document).ready(function(){
    
    $("#signupEmail").val("folaudev+"+generateUUID()+"@gmail.com");
    $("#signupPassword").val("Test1234!");

    $("#signupBtn").click(function(){
        console.log("signing up...");
        var payload = {};
        payload['email'] = $("#signupEmail").val();
        payload['password'] = $("#signupPassword").val();

        $.ajax({
            url: 'http:/localhost:8888/api/users/signup',
            type: 'POST',
            data: JSON.stringify(payload),
            headers: {
                "x-api-key": 'test-web-api-key'
            },
            contentType: "application/json"
        }).then(function(data, status) {
            console.log("data");
            console.log(data);
            console.log("status");
            console.log(status);
        });
    });
});