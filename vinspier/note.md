
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

### cookie写入问题
+ 服务的响应头中需要携带Access-Control-Allow-Credentials并且为true。
+ 响应头中的Access-Control-Allow-Origin一定不能为*，必须是指定的域名
+ 浏览器发起ajax需要指定withCredentials 为true

### cookie也是有域 的限制
+ 一个网页，只能操作当前域名下的cookie，但是现在我们看到的地址是0.0.1
+ 跟踪CookieUtils getDomainName(HttpServletRequest request)
  获取domain是通过服务器的host来计算的
  
### 解决host地址的变化 
我们使用了nginx反向代理，当监听到api.vinspier.com的时候，会自动将请求转发至127.0.0.1:8080，即Zuul。
而后请求到达我们的网关Zuul，Zuul就会根据路径匹配，我们的请求是/api/auth，根据规则被转发到了 127.0.0.1:90 ，即我们的授权中心。
+ 首先去更改nginx配置，让它不要修改我们的host：proxy_set_header Host $host;
+ 但是Zuul还会有一次转发，所以要去修改网关的配置 zuul.add-host-header=true 允许携带请求本身的header信息

### Zuul的敏感头过滤
Zuul内部有默认的过滤器，会对请求和响应头信息进行重组，过滤掉敏感的头信息：
这里会通过一个属性为SensitiveHeaders的属性，来获取敏感头列表，然后添加到IgnoredHeaders中，
这些头信息就会被忽略。而这个SensitiveHeaders的默认值就包含了set-cookie：
+ 全局设置： 
  zuul.sensitive-headers=
  指定路由设置：
  zuul.routes.<routeName>.sensitive-headers=
  zuul.routes.<routeName>.custom-sensitive-headers=true