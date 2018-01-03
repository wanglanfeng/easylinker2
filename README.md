![](github_assets/logo.png)
# EasyLinker V2.0
### 学习群：475512169
### 安装教程 :http://www.easylinker.xyz/t/4
### EasyLinker-EMQ : https://git.coding.net/ChildeWWH/EMQ_Easylinker.git
# 前言

>本人是电子信息工程理工科男一枚，标准技术宅。本应该去研究CPU架构和汇编语言的我，却迷上了WEB互联网开发。加上平时喜欢折腾一些极客玩具，渐渐的对硬件也来了兴趣。
平日里酷爱折腾一些极客玩具的我，再加上本人身处硬件相关专业，平时接触到了很多不一样的问题。问题总是围绕在身边。
比如，某同学在做STM32单片机的时候抱怨：“我要是把拿到的数据用软件存储起来就好了，到时候直接拿出来观察结果数据。”再者还有：“要是能把单片机的数据传输到网络里面就好了，可以随便查看。”
这些都是来自硬件工程师的抱怨和难处：硬件不负责数据的可视化呈现，他们更喜欢用各种仪器去查看。
我想：能否解决这个问题？让这些硬件工程师随时可以查看数据，用手机，用浏览器，APP，都可以观测结果，即打造一个“通用的平台，用来让硬件把数据传递到互联网。
于是自己着手试了一下，在很长的时间里，尝试了很多方法，也解决了一部分问题，但是总是不完美，
期间用了Python在树莓派上实现一个数据呈现平台，但是不理想，折腾许久，
最终决定用Java来实现这个平台。这就是这个项目的来源，含义就是：让一切联网变得更加容易.  
>

## 关于新版本

>很长一段时间内，都想做一个完整的项目，但是总是被各种事情打断
,EasyLinker也是策划很久的项目，但是还是因为我的实习工作打乱了计划。
想起来很可惜，但是又不甘心，于是就百忙之中抽空做起来，挤出时间来完善。
进度比较慢，但是进步就行。之前做的V1因为架构设计失误，导致版本无法继续，为了后续稳定更新，
我决定放弃以前的版本重新开始.  
>  

## 新版特点

>  新版特点  
1.新版本全部采用REST接口形式形式，方便扩展;  
2.加入了Swagger-API插件动态显示文档的功能;  
3.增加设备管理器，随时查看设备的资源和状态;  
4.拟增加Node.js脚本支持，便于用户自定义策略;  
5.增加物理网常用API接口(天气，温度，湿度等等);   
6.附带一个微型博客，用户共享示例; 
7.附带一个APP来管理后台设备;
>
## Python客户端代码示例(控制树莓派40号引脚的电平)
```
#说明：需要安装paho-mqt库,命令:pip install paho-mqtt
#[8305132bc868fa613dfa2fa3eac6053a] 这个是设备的openId
import RPi.GPIO as GPIO
import paho.mqtt.client as mqtt
import time
GPIO.setmode(GPIO.BOARD)
GPIO.setup(40, GPIO.OUT)

def on_connect(client, userdata, flags, rc):
    print("Connected Success! "+str(rc))
    client.subscribe("device/8305132bc868fa613dfa2fa3eac6053a")
    file = open("/sys/class/thermal/thermal_zone0/temp")
    temp = float(file.read()) / 1000
    data={ "qos":1, "retain":"true","unit":"C", "message":str(temp)}
    client.publish("device/8305132bc868fa613dfa2fa3eac6053a", str(data))     
    file.close()
    time.sleep(6)

def on_message(client, userdata, msg):
    print("received:"+str(msg.payload))
    if(str(msg.payload)=="ON"):
        GPIO.output(40, GPIO.LOW)
    elif(str(msg.payload)=="OFF"):
        GPIO.output(40, GPIO.HIGH)

client = mqtt.Client("8305132bc868fa613dfa2fa3eac6053a")
client.on_connect = on_connect
client.on_message = on_message
client.username_pw_set("8305132bc868fa613dfa2fa3eac6053a","8305132bc868fa613dfa2fa3eac6053a")
client.connect("192.168.3.64", 1883, 60)
client.loop_forever()
```



