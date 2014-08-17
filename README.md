RChat-Server
============

Roulette Chat Server （轮盘聊天服务器）

#命令返回(Command returns)   

###错误返回(Error returns)
1. {code: 502, msg: 'Bad Params'} 参数错误   
2. {code: 503, msg: 'Parameters Content Error'} 参数内容错误   
3. {code: 505, msg: 'Parameters Form Error'} 参数格式错误   
   
###失败消息返回(Failure message returns)   
1. {code: 304, msg: 'Repeat Search'} 已建立链接，重复链接失败   
2. {code: 404, msg: 'Other Side Not Found'} 无法建立链接   
3. {code: 410, msg: 'Other Side Disconnect'} 链接断开 

###成功消息返回(Successful message returns)   
1. {code: 200, msg: '接收的消息(received message)'} 接受消息   
2. {code: 201, msg: 'Interconnect Success'} 链接建立成功   
3. {code: 202, msg: 'Disconnect Success'} 链接断开成功    
   
#请求格式(Request Format)
1. {"status": 0} 寻找尝试建立链接 (Looking and try to establish a link)    
2. {"status": 1, "content": "发送的内容(content)"} 发送消息(Send a message)     
3. {"status": -1} 断开链接 （Unlink）   
