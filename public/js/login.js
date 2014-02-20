var Login = function () {
    
    return {
        //main function to initiate the module
        init: function () {
           $('.login-form').validate({
	            errorElement: 'label', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                rememberme: {
	                    required: false
	                }
	            },

	            messages: {
	                username: {
	                    required: "登录账号不能空"
	                },
	                password: {
	                    required: "登录密码不能空"
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	                $('.alert-error', $('.login-form')).show();
                    $(".alert-error span").html("登录信息不全，请输入必要登录信息");
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.control-group').addClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
                    $(form).submit();
//	                window.location.href = "index.html";
	            }
	        });

	        $('.login-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.login-form').validate().form()) {
                        $('.login-form').submit();
//	                    window.location.href = "index.html";
	                }
	                return false;
	            }
	        });
        }

    };

}();