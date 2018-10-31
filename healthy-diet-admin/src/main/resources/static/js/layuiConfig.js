var element = layui.element,
    layer = layui.layer,
    laydate = layui.laydate,
    laypage = layui.laypage,
    layform= layui.form
;
layer.config({
    skin: 'layer-custom-class'
});
layui.config({
    base: 'js/lib/layui/plugins/' //假设这是你存放拓展模块的根目录
}).extend({ //设定模块别名
    autocomplete: 'autocomplete/autocomplete' //相对于上述 base 目录的子目录
});
function initPage(domId, total, scope, queryCb){
    if(scope.total){
        return ;
    }
    scope.total = total;
    laypage.render({
        elem: domId || 'pageNav'
        , count: total
        , layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
        , jump: function (obj, first) {
            // console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
            // console.log(obj.limit); //得到每页显示的条数

            //首次不执行
            if(!first){
                scope.currPage = obj.curr;
                scope.pageSize = obj.limit;
                queryCb(scope.currPage, scope.pageSize);
            }
        }
        , theme: '#1E9FFF'
    });
}
/**
 * 文件上传初始化渲染
 * @param domId DOM对象
 * @param url
 * @param data 请求上传接口的额外参数
 * @param fileType 允许上传的文件后缀，如'zip|rar|7z'
 * @param size 设置文件最大可允许上传的大小，单位 KB
 */
function initUploader(domCls, url, data, fileType, size, succCb) {
    var upload = layui.upload;
    upload.render({
        elem: '.'+(domCls || 'uploadCls')
        , url: url
        , data: data
        , accept: 'file' //允许上传的文件类型
        , headers: {
            'Authorization': 'Bearer ' + sessionStorage.getItem('token')
        }
        , exts: fileType //最大允许上传的文件大小
        , size: size //最大允许上传的文件大小
        , before: function (obj) { //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
            layer.load(0, {shade: [0.2, '#393D49']});
        }
        , done: function (res, index, upload) { //上传后的回调
            if (succCb) {
                layer.closeAll('loading'); //关闭loading
                succCb(res);
            }
        }
        , error: function (index, upload) {
            layer.closeAll('loading'); //关闭loading
            layer.alert("上传失败，请重试");
        }
    })
}

function initTable(domId){
    var table = layui.table;
    table.init(domId, {
        height: 315 //设置高度
        ,limits: [10, 20, 30, 40, 50]
        //支持所有基础参数
    });

}