<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>秒杀详情页</title>
    <link href="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<!-- Modal -->
<div class="modal fade" id="killPhoneModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone">秒杀电话：</span></h3>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label for="killPhoneKey" class="col-sm-2 control-label">手机号</label>
                        <div class="col-sm-10">
                            <input type="text" name="killPhone" class="form-control" id="killPhoneKey" placeholder="手机号">
                            <span class="help-block"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" class="btn btn-success" id="killPhoneButton">
                    <span class="glyphicon glyphicon-phone">
                        Submit
                    </span>
                </button>
            </div>
        </div>
    </div>
</div>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="pannel-heading">
                <h1>
                ${seckill.name}
                </h1>
            </div>
            <div class="panel-body">
                <h2 class="text-danger">
                    <%--显示time图标--%>
                    <span class="glyphicon glyphicon-time"></span>
                    <%--展示倒计时--%>
                    <span class="glyphicon" id="seckill-box"></span>
                </h2>
            </div>
        </div>
    </div>




<script src="${pageContext.request.contextPath }/static/js/jquery-1.12.4.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${pageContext.request.contextPath }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<!--cookie插件-->
<script src="${pageContext.request.contextPath }/static/js/jquery.cookie.js"></script>
<%--jQuery countDown倒计时插件--%>
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
<script src="/resources/script/seckill.js" type="text/javascript"></script>
    <script type="text/javascript">
         $(function () {
             seckill.detail.init({
                 seckillId:${seckill.seckillId},
                 startTime:${seckill.startTime.time},
                 endTime:${seckill.endTime.time}
             });
         })   
    </script>
</body>
</html>
