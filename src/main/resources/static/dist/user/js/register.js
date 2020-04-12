$(function () {
    // 手机号码验证
    $.validator.addMethod("isPhone", function (value, element) {
        var length = value.length;
        return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
    }, "请正确填写您的手机号码。");
    // 匹配密码，以字母开头，长度在6-12之间，必须包含数字和特殊字符。
    $.validator.addMethod("isPwd", function (value, element) {
        var str = value;
        if (str.length < 6 || str.length > 18)
            return false;
        if (!/^[a-zA-Z]/.test(str))
            return false;
        if (!/[0-9]/.test(str))
            return fasle;
        return this.optional(element) || /[^A-Za-z0-9]/.test(str);
    }, "以字母开头，长度在6-12之间，必须包含数字和特殊字符。");

    $("#register-form").validate({
        errorElement: 'span',
        errorClass: 'help-block',
        onfocusout: function (element) {
            $(element).valid();
        },
        rules: {
            username: "required",
            password: {
                required: true,
                isPwd: true
            },
            confirm_password: {
                required: true,
                isPwd: true,
                equalTo: "#password"
            },
            phone: {
                required: true,
                isPhone: true
            },
            nickname: "required"
        },
        messages: {
            username: "请输入登录名",
            nickname: "请输入姓名",
            password: {
                required: "请输入密码",
                minlength: $.validator.format("密码不能小于{0}个字符")
            },
            confirm_password: {
                required: "请输入确认密码",
                minlength: "确认密码不能小于5个字符",
                equalTo: "两次输入密码不一致不一致"
            },
            phone: {
                required: "请输入手机号码"
            }
        },
        //自定义错误消息放到哪里
        errorPlacement: function (error, element) {
            element.next().remove();//删除显示图标
            element.after('<span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>');
            element.closest('.form-group').append(error);//显示错误消息提示
        },
        //给未通过验证的元素进行处理
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error has-feedback');
        },
        //验证通过的处理
        success: function (label) {
            var el = label.closest('.form-group').find("input");
            el.next().remove();//与errorPlacement相似
            el.after('<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>');
            label.closest('.form-group').removeClass('has-error').addClass("has-feedback has-success");
            label.remove();
        },
        submitHandler: function () {
            alert("Submitted!")
        }
    })
});