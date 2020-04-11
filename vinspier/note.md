
## 本地于域名地址

- 后台地址 127.0.0.1:9001 manage.vinspier.com
- 服务网关地址 127.0.0.1:8888 api.vinspier.com
- 本地上传 127.0.0.1:82 upload.vinspier.com
- 虚拟机文件服务器 192.168.124.27 image.vinspier.com
- 前台页面 主域名 127.0.0.1:10010 www.vinspier.com
- elasticSearch 192.168.124.18:9200

## nginx配置微服务地址和端口

- 启动：`start nginx.exe`
- 停止：`nginx.exe -s stop`
- 重新加载：`nginx.exe -s reload`

- 注册中心 8080 register-center
- 网关地址 8888 zull-gateway 路由前缀api
- 服务rest接口 81 item-service
- 文件上传服务

## elasticsearch搜索服务
- 用户 visnpier source /etc/profile
- 位置 /usr/local/vinspier/elasticsearch
- ./elasticsearch
- ps -ef | grep elasticsearch

kibana:http://localhost:5601/app/kibana#/home?_g=()