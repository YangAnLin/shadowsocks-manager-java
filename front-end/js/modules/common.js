layui.define(['jquery'], function (exports) {


    var $ = layui.jquery;

    var obj = {
        ajax: function (url, type, data, callback) {

            var localData =obj.getLocalData();
            //api 地址
            var apiURLPrefix = localData.url;
            //静态资源地址
            var staticURLPrefix = localData.staticUrl;

            if (url.indexOf('login') == -1) {
                //不是登录请求 验证token是否存在
                var token = localData.token;
                if (null == token || '' == token) {
                    if (self != top) {
                        parent.window.location.href = staticURLPrefix + '/login.html';
                    }else{
                        window.location.href = staticURLPrefix + '/login.html';
                    }

                    return;
                }
            }

            var ajaxConfig = {
                url: apiURLPrefix + url,
                type: type,
                contentType: "application/json;charset=utf-8",
                headers: {
                    //token
                    token: token
                },
                success: callback
            };
            if ('GET' != type) {
                ajaxConfig.dataType = 'json';
                ajaxConfig.data = JSON.stringify(data);
            }

            $.ajax(ajaxConfig);
        },
        getLocalData:function(){
            var localData = layui.data('chaos:login');
            return localData;
        },
        appURL: function () {
            var localData = obj.getLocalData();
            var apiURLPrefix = localData.url;
            return apiURLPrefix;
        },
        appStaticURL: function () {
            var localData = obj.getLocalData();
            var staticUrl = localData.staticUrl;
            return staticUrl;
        },
        isLogin:function(){
          if(obj.getToken()==null||obj.getToken()==''){
              return false;
          }else{
              return true;
          }
        },
        isAdmin:function(){
            if(!obj.isLogin()){
                return false;
            }

            var localData = obj.getLocalData();
            if(0==localData.role){
                return true;
            }else{
                return false;
            }
        },
        getToken:function(){
            var localData = obj.getLocalData();
            return localData.token;
        }
    };
    //输出接口
    exports('common', obj);
});