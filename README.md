# ZhaChat
闸总聊天室——JavaSE项目网络聊天室

此项目采用C/S架构，服务端可局域网和互联网连接


服务器输入开启的端口，客户端输入服务端IP+端口，即可连接聊天，可实现多人聊天

客户端输入信息给服务端，服务端转发给各个客户端

每个用户的客户端连接，用户列表出现已连接的用户昵称


服务端与客户端UI界面采用GUI实现，收发信息为socket下IO流传输操作
