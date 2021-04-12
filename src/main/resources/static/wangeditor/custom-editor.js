//自定义编辑器菜单
const E = window.wangEditor
const editor = new E("#article")
editor.config.uploadImgServer = '/upload'
//配置指定文件名
editor.config.uploadFileName = 'file'
//设置上传的文件大小
editor.config.uploadImgMaxSize = 20 * 1024 * 1024
//设置输入框高度
editor.config.height = 500
//如果图片不大，可以用base64存储
//editor.customConfig.uploadImgShowBase64 = true
editor.create()

document.getElementById('submit').addEventListener('click', function () {
    // 读取 html
    var text = editor.txt.html()

    //此处发送请求保存控制台
    $.ajax({
        type: "POST",
        traditional: true,
        // contentType: "application/json; charset=utf-8",
        url: "/send-content",
        data: {
            "content": text
        },
        success:function(data){
            console.log("data===="+data.status);
            console.log("data===="+data.data);
        },
        error:function(data){
            //responseText ：服务器响应返回的文本信息
            console.log("登陆回调失败，原因为"+data.status)
        }
    })

}, false)