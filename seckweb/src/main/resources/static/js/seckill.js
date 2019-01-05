function a() {

}
//模板化开发方式
var seckillObj = {
    //获取秒杀商品的地址，也就是上面的标识符号UUid生成的一个randomNmae
    contextPath : "",

    url :{
       randomURL : function () {
           return seckillObj.contextPath + "/secking/random/";
       },
        seckingURL : function () {
            return seckillObj.contextPath + "/seckweb/goods/";
        }
    },


    func : {
        initGoods : function (currentTime,startTime,endTime,id) {
            if (startTime > currentTime){
                //秒杀未开始
                //使用jquery的倒计时插件实现倒计时
                var killTime = new Date(startTime + 1000);//防止时间偏移
                $("#seckillTip").countdown(killTime, function (event) {
                    //时间格式
                    var format = event.strftime('距秒杀开始还有: %D天 %H时 %M分 %S秒');
                    $("#seckillTip").html("<span style='color:red;'>"+format+"</span>");
                }).on('finish.countdown', function () {
                    //倒计时结束后回调事件，已经开始秒杀，用户可以进行秒杀了，有两种方式：
                    //1、刷新当前页面 location.reload();
                   // 或者
                    location.reload();
                    //2、调用秒杀开始的函数
                });
            }else if (endTime < currentTime) {
                //秒杀结束
                $("#seckillTip").html("<span style='color:red;'>秒杀已经结束</span>");
            }else {
                //秒杀开始
                seckillObj.func.startSeckill(id);
            }
        },
        
        startSeckill : function (id) {
        //开始秒杀了
            $.ajax({
                url : seckillObj.url.randomURL() + id,
                type : "post",
                dataType : "json",
                success : function (data) {
                    if(data.errorCode == 0){ // 这里加下面构成了双重验证判断
                        var random = data.data;
                        if (random != null){
                            $("#seckillTip").html('<button type="button" id="seckillBtn">立即秒杀</button>')

                            $("#seckillBtn").click(function () {
                                $("#seckillBtn").attr("disabled",true);
                                //秒杀开始执行调用后台
                                seckillObj.func.execSeckill(random,id);

                            });
                        }

                    }
                }
            })
        },
        //秒杀开始执行调用后台
        execSeckill : function (random,id) {
            $.ajax({
                url : seckillObj.url.seckingURL()+id+"/"+random,
                dataType : "json",
                success : function (responseMessage) {
                    if (responseMessage.errorCode == 0) {
                        //秒杀成功的中间状态的，是中间结果
                        $("#seckillTip").html("<span style='color:red;'>"+ responseMessage.errorMessage +"</span>");

                        //由于此时拿到的结果是一个中间结果，所以我们还需要发送请求，获取最终结果
                        window.setInterval(function () {
                            //查询最终秒杀结果
                            seckillObj.func.queryResult();
                        }, 3000);

                    } else {
                        $("#seckillTip").html("<span style='color:red;'>"+ responseMessage.errorMessage +"</span>");
                    }
                }
            })
        },
        queryResult : function () {






        }

    }
}









