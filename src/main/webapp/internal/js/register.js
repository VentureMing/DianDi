var buttonFlag = false;
function nextStep() {
    if (!buttonFlag) {
        if (checkInputAccount()) {
            $('#inputAccount').css('display', 'none');
            $('#inputBasicInfo').css('display', 'block');
            buttonFlag = true;
        }

    } else {
        if (checkInputBasic()) {
            register();
            buttonFlag = false;
        }
    }
}

// 为 '' 返回true
function checkInputEmptyWhenRegister(id) {
    var va = $('#' + id);

    if (va.val() === '') {
        errorInfoWhenRegister('<small>请输入</small>' + va.attr('placeholder'));
        return true;
    } else {
        return false;
    }
}

function checkInputAccount() {
    var accountCheck=true;
    if (checkInputEmptyWhenRegister('registerEmail') ||
        checkInputEmptyWhenRegister('registerPassword') ||
        checkInputEmptyWhenRegister('conformPassword')) {
        return false;
    }

    // 正则校验邮箱
    if (!isEmail($('#registerEmail').val())) {
        errorInfoWhenRegister('邮箱格式不正确');
        return false;
    }

    // 检查密码格式规范
    var pwd = $('#registerPassword').val();
    if (!isPassword(pwd)) {
        errorInfoWhenRegister('密码格式不正确，<small>密码由 6-12 位字母和数字组成</small>');
        return false;
    }

    // 检查两次密码是否一致
    var pwdc = $('#conformPassword').val();
    if (pwd !== pwdc) {
        errorInfoWhenRegister('两次密码不一致');
        return false;
    }

    //检查邮箱是否已经注册
    $.ajax({
        url: '/author/check=email?email=' + $('#registerEmail').val(),
        async: false,
        success: function (result) {
            if (result.code === 18) {
                errorInfoWhenRegister('邮箱已注册');
                accountCheck=false;
            }
        }
    });
    return accountCheck;
}

var maxNickNameCount = 15;


function checkInputBasic() {
    var basicCheck=true;
    if (checkInputEmptyWhenRegister('registerNickName') ||
        checkInputEmptyWhenRegister('registerPhone')) {
        return false;
    }

    // 检查昵称字数
    var nickName = $('#registerNickName').val();
    if (nickName.length > maxNickNameCount) {
        error('昵称不能超过 ' + maxNickNameCount + ' 个字，当前 '
            + nickName.length + ' 字', 'registerErrorMsg', true, 3000);
        return false;
    }

    // 正则校验电话
    var phone = $('#registerPhone').val();
    if (!isPhone(phone)) {
        errorInfoWhenRegister('电话号码格式不正确');
        return false;
    }
    //检查昵称重复
    $.ajax({
        url: '/author/check=nickName?nickName=' + nickName,
        async: false,
        success: function (result) {
            if (result.code === 18) {
                errorInfoWhenRegister('该昵称已经被注册');
                basicCheck=false;
            }
        }
    });
    //检查电话号码重复
    $.ajax({
        url: '/author/check=phone?phone=' + phone,
        async: false,
        success: function (result) {
            if (result.code === 18) {
                errorInfoWhenRegister('该手机号已经被注册');
                basicCheck=false;
            }
        }
    });
    return basicCheck;
}


function errorInfoWhenRegister(msg) {
    error(msg, 'registerErrorMsg', true, 3000);
}

//邮箱注册
function register() {
    var email = $('#registerEmail').val();
    var password = $('#registerPassword').val();
    var nickName = $('#registerNickName').val();
    var phone = $('#registerPhone').val();

    $.post(
        '/author',
        {
            email: email,
            password: password
        },
        function (result) {

            if (result.code === 0) {
                login(result.data);
            }

            //添加个人基本信息
            function addBasic(authorId) {
                $.post(
                    '/author/'+authorId+'/basicInfo',
                    {
                        phone: phone,
                        nickName: nickName
                    },
                    function (result) {
                        if (result.code === 0) {
                            self.location=document.referrer;
                        }
                    }, 'json'
                )
            }

            function login(authorId) {
                $.post(
                    '/author/login/way=email',
                    {
                        email: email,
                        password: password
                    }, function (result) {
                        if (result.code === 0) {
                            addBasic(authorId);
                        }
                    }, 'json');
            }

        }, 'json');
}

// // ----------------------------- 登录对话框回调
// function funAfterLoginSuccess(result, name) {
//     location.href = '/' + name + '/archives';
// }
//
// function funAfterLoginFail(result) {
// }
