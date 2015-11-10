#Volley简介
网络请求框架

##类简介

* 1：RequestQueue:线程任务调度。
	+ 1：NetworkDispatcher[]:网络操作线程列表
	+ 2：CacheDispatcher：缓存使用接口

* 2：Request:任务;
	* 类描述
		+ 任务所需的参数：url等
		+ 任务的返回值处理
		+ 取消任务等操作
	* 子类
		+ 1：ImageRequest：请求图片的任务
		+ 2：StringRequest：请求String字符串的任务
		+ 3：JsonRequest：请求json的任务
			+ 1：JsonArrayRequest：请求jsonArray的任务
			+ 2：JsonObjectRequest：请求jsonObject的任务
* 3：RetryPolicy：重试策略
* 4：NetworkDispatcher：网络请求调度线程
* 5：CacheDispatcher：缓存请求调度线程
* 6：



~~**URLConnection conn = openConnection();conn.setRequestProperty("User-agent",userAgent);**~~