//存放主要交互逻辑js代码
//javascript模块化
var seckill={
    URL:{
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        execution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    handlerSeckill: function (seckillId,node) {
        //秒杀逻辑
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId),{},function (result) {
            if(result&&result['success']){
                var exposer=result['data'];
                if(exposer['exposed']){
                    //开启秒杀
                    var md5=exposer['md5'];
                    var killUrl=seckill.URL.execution(seckillId,md5);
                    //绑定一次点击事件,防止接收太多请求
                    $("#killBtn").one('click',function () {
                        //执行秒杀请求的操作
                        //1.禁用按钮
                        $(this).addClass("disabled");
                        //2.发送秒杀请求
                        $.post(killUrl,{},function (result) {
                            if(result&&result['success']){
                                var killReuslt=result['data'];
                                var state=killReuslt['state'];
                                var stateInfo=killReuslt['stateInfo'];
                                //3.显示秒杀结果
                                node.html('<span class="label label-success">'+stateInfo+'</span>')
                            }
                        });
                    });
                    node.show();
                }else{
                    //未开启秒杀
                    var now=exposer['now'];
                    var satrt=exposer['start'];
                    var end=exposer['end'];
                    //重新计算计时逻辑
                    seckill.countdown(seckillId,now,satrt,end);
                }
            }else{
                console.log(result);
            }
        });
    },
    validatePhone: function (phone) {
        //isNaN是判断电话是不是数字，是数字返回true，不是数字返回false
        if(phone&&phone.length==11&&!isNaN(phone)){
            return true;
        }
        else{
            return false;
        }
    },
    countdown: function (seckillId,nowTime,startTime,endTime) {
        var seckillBox = $('#seckill-box');
        if (nowTime > endTime) {
            //秒杀结束
            seckillBox.html('秒杀结束!');
        } else if (nowTime < startTime) {
            //秒杀未开始,计时事件绑定
            var killTime = new Date(startTime + 1000);//todo 防止时间偏移
            seckillBox.countdown(killTime, function (event) {
                //时间格式
                var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒 ');
                seckillBox.html(format);
            }).on('finish.countdown', function () {
                //时间完成后回调事件
                //获取秒杀地址,控制现实逻辑,执行秒杀
                console.log('______fininsh.countdown');
                seckill.handlerSeckill(seckillId, seckillBox);
            });
        } else {
            //秒杀开始
            seckill.handlerSeckill(seckillId, seckillBox);
        }
    },
    //详情页秒杀逻辑
    detail:{
        //详情页初始化
        init:function (params) {
            //手机验证和登录，计时交互
            //规划我们的交互流程
            //cookie中查找手机号
            var killPhone =$.cookie('killPhone');
            var startTime=params['startTime'];
            var endTime=params['endTime'];
            var seckillId=params['seckillId'];
            if(!seckill.validatePhone(killPhone)){
                $("#killPhoneModal").modal({
                    show:true,
                    backdrop:'static',
                    keyboard:false
                });
                $("#killPhoneButton").click(function () {
                    var inputPhone=$("#killPhoneKey").val();
                    if(seckill.validatePhone(inputPhone)){
                        $.cookie('killPhone',inputPhone,{expires:7,path:'/seckill'});
                        location.reload();
                    }else{
                        $("#killPhoneMessage").hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                    }
                })
            }
            //已经登录
            //计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.ajax({
                url:seckill.URL.now(),
                type:"get",
                success:function (result) {
                    if (result && result['success']) {
                        var nowTime = result['data'];
                        //时间判断 计时交互
                        seckill.countdown(seckillId, nowTime, startTime, endTime);
                    } else {
                        console.log('result: ' + result);
                        alert('result: ' + result);
                    }
                }
            });
        }
        }
    }