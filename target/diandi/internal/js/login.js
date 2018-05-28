/**
 * 登录
 * @param funAfterLoginSuccess 登录成功时回调，参数为result
 * @param funAfterLoginFail 登录失败时回调，参数为错误码和错误信息
 */
function emailLogin() {


    if (checkInputEmptyWhenLogin('loginEmail') ||
        checkInputEmptyWhenLogin('loginEmailPassword')) {
        return;
    }
    // 邮箱登录
    var email = $('#loginEmail').val();
    var pwd = $('#loginEmailPassword').val();
    // 正则校验邮箱
    if (!isEmail(email)){
        errorInfoWhenLogin('请输入正确格式的邮箱');
        return ;
    }

    if (!isPassword(pwd)) {
        errorInfoWhenLogin('密码格式不正确，<small>密码由 6-12 位字母和数字组成</small>');
        return;
    }

    disableButton(false, 'emailLoginButton', '正在登录...', "button-disable");
    $.post(
        '/author/login/way=email',
        {
            email: email,
            password: pwd
        },
        function (result) {
            if (result.code === 0) {
                disableButton(false, 'emailLoginButton', '登录成功', "button-disable");

                setTimeout(function () {
                    disableButton(true, 'emailLoginButton', '登录', "button-disable");
                    self.location=document.referrer;
                }, 1000);

            } else {
                errorInfoWhenLogin(result.msg);
                disableButton(true, 'emailLoginButton', '登录', "button-disable");
            }
        }
    );

}

// 为 '' 返回true
function checkInputEmptyWhenLogin(id) {
    var va = $('#' + id);

    if (va.val() === '') {
        errorInfoWhenLogin('<small>请输入</small>' + va.attr('placeholder'));
        return true;
    } else {
        return false;
    }
}

function errorInfoWhenLogin(msg) {
    error(msg, 'loginErrorMsg', true, 1000);
}
