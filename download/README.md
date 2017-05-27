# downloader
    Android downloader Lib
## 1.添加权限声明
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
## 2.libs引入
    如果直接引入代码需要下载http和common包&删除libs中的common.aar和http.aar
    对于仅使用下载器和http请求的可以在download的build文件中修改如下
    1.添加repositories
      repositories {
         flatDir {
             dirs 'libs'
         }
      }
    2.dependencies中添加
      compile(name:'common', ext:'aar')
      compile(name:'http', ext:'aar')
## 3.接入代码
### 3.1:SDK初始化
    Downloader.initialized(context, 2, 2.0f, false);
    //2：int maxThreadSize(最大线程数，如果小于等于0则为默认单线程下载), 
    //2.0f:float blockSizeMb(分块下载块大小), 
    //boolean autoDownloadAll(是否自动启动数据库记录任务全部下载)
    //建议在application中调起初始化。
### 3.2：任务下载
    两种接入方式
    1.无界面：支持下载Media、Image、apk、other
    Downloader.getInstance().addTask(url, title, iconUrl, verifyCode, fileSize, DownloadEntity.DldType.APP, downloadListener);
    2.有界面：仅支持下载apk，下载完成会自动调起安装，网络变换提示等
    DownloadApp.newInstance(context).setListener(downloadListener).download(url, title, iconUrl, md5, size);
### 3.3：下载器API接口描述
1. com.zhangwy.download.Downloader<br />
下载类型Media、Image、apk、other；支持多任务多线程下载，下载任务记入数据库，下载状态通知及校验文件，支持删除、暂停、重新开始下载
<table bgcolor="#aaa" border="0" cellpadding="5" cellspacing="1">
    <tr bgcolor="#ddd">
        <td>方法名</td>
        <td>方法描述</td>
    </tr>
    <tr bgcolor="#fff">
        <td><font style="color: rgb(255,198,109);">initialized</font>(Context context<span style="color: rgb(204,120,50);">, int </span>maxThreadSize<span style="color: rgb(204,120,50);">, float blockSizeMb<font style="color: rgb(204,120,50);">, boolean autoDownloadAll)</td>
        <td><font style="color: rgb(98,151,85);">初始化下载器，建议在application中调起</font><font style="color: rgb(204,120,50);">int </font>maxThreadSize<font style="color: rgb(204,120,50);">：最大同时下载任务数</font><font style="color: rgb(204,120,50);">float </font>blockSizeMb<font style="color: rgb(204,120,50);">：分块下载块大小</font><font style="color: rgb(204,120,50);">boolean </font>autoDownloadAll：自动启动数据库任务</td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">register</span>(DownloadListener listener)</td>
        <td><span style="color: rgb(98,151,85);">设置监听器，监听所有下载任务,仅下载管理器需要</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">unRegister</span>(DownloadListener listener)</td>
        <td><span style="color: rgb(98,151,85);">移除监听器</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">addTask</span>(String url<span style="color: rgb(204,120,50);">, </span>String title<span style="color: rgb(204,120,50);">, </span>String iconUrl<span style="color: rgb(204,120,50);">, </span>String verifyCode<span style="color: rgb(204,120,50);">, long </span>fileSize, DldType dldType)</td>
        <td><span style="color: rgb(98,151,85);">添加任务</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">addTask</span>(String url<span style="color: rgb(204,120,50);">, </span>String title<span style="color: rgb(204,120,50);">, </span>String iconUrl<span style="color: rgb(204,120,50);">, </span>String verifyCode<span style="color: rgb(204,120,50);">, long </span>fileSize, DldType dldType ,  DownloadListener listener)</td>
        <td><span style="color: rgb(98,151,85);">添加任务同时注册监听器，该监听器仅监听当前任务</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">reStartTaskByUrl</span>(String url)</td>
        <td><span style="color: rgb(98,151,85);">重新启动下载任务通过URL</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">reStartTaskById</span>(String id)</pre></td>
        <td><span style="color: rgb(98,151,85);">重新启动下载任务通过任务ID</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">stopTaskByUrl</span>(String url)</td>
        <td><span style="color: rgb(98,151,85);">暂停下载任务通过任务URL</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">stopTaskById</span>(String id)</td>
        <td><span style="color: rgb(98,151,85);">暂停下载任务通过任务ID</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">delTaskByUrl</span>(String url)</td>
        <td><span style="color: rgb(98,151,85);">删除下载任务通过任务URL</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">delTaskById</span>(String id)</td>
        <td><span style="color: rgb(98,151,85);">删除下载任务通过任务ID</span></td>
    </tr>
</table>

2. com.zhangwy.download.Downloader.DownloadListener<br />
下载监听接口
<table bgcolor="#aaa" border="0" cellpadding="5" cellspacing="1">
    <tr bgcolor="#ddd">
        <td>方法名</td>
        <td>方法描述</td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">onAddTask</span>(String taskId<span style="color: rgb(204,120,50);">, </span>String taskUrl, boolean <span style="white-space: pre-wrap;font-family: Arial , sans-serif;">status</span>)<span style="color: rgb(204,120,50);">;</span></td>
        <td><span style="color: rgb(98,151,85);">添加任务状态回调；status状态</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">onStart</span>(String taskId<span style="color: rgb(204,120,50);">, </span>String taskUrl)<span style="color: rgb(204,120,50);">;</span></td>
        <td><span style="color: rgb(98,151,85);">开始下载通知回调</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">onProgressChanged</span>(String taskId<span style="color: rgb(204,120,50);">, </span>String taskUrl<span style="color: rgb(204,120,50);">, float </span>progress)<span style="color: rgb(204,120,50);">;</span></td>
        <td><span style="color: rgb(98,151,85);">下载进度通知回调</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">onStop</span>(String taskId<span style="color: rgb(204,120,50);">, </span>String taskUrl)<span style="color: rgb(204,120,50);">;</span></td>
        <td><span style="color: rgb(98,151,85);">暂停下载通知回调</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">onSuccess</span>(String taskId<span style="color: rgb(204,120,50);">, </span>String taskUrl<span style="color: rgb(204,120,50);">, </span>String localPath)<span style="color: rgb(204,120,50);">;</span></td>
        <td><span style="color: rgb(98,151,85);">下载完成通知回调；localPath文件地址</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">onFailed</span>(String taskId<span style="color: rgb(204,120,50);">, </span>String taskUrl<span style="color: rgb(204,120,50);">, int </span>errCode<span style="color: rgb(204,120,50);">, </span>String errMsg)<span style="color: rgb(204,120,50);">;</span></td>
        <td><span style="color: rgb(98,151,85);">下载失败通知回调</span></td>
    </tr>
</table>

3. 带界面下载器API接口描述<br />
com.zhangwy.download.DownloadApp<br />
应用下载。仅支持下载apk，下载完成会自动调起安装，网络变换提示等，一个DownloadApp仅支持下载一个任务，多个任务需要创建多个DownloadApp实体。
<table bgcolor="#aaa" border="0" cellpadding="5" cellspacing="1">
    <tr bgcolor="#ddd">
        <td>方法名</td>
        <td>方法描述</td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">newInstance</span>(Context context)</td>
        <td><span style="color: rgb(98,151,85);">创建DownloadApp实体，</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">setListener</span>(Downloader.DownloadListener listener)</td>
        <td><span style="color: rgb(98,151,85);">设置监听器，当下载开始、停止、进度、完成均有回调</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">download</span>(String url<span style="color: rgb(204,120,50);">, </span>String title<span style="color: rgb(204,120,50);">, </span>String iconUrl<span style="color: rgb(204,120,50);">, </span>String verifyCode<span style="color: rgb(204,120,50);">, long </span>fileSize)</td>
        <td><span style="color: rgb(98,151,85);">任务执行接口</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">reStart</span>(String url)</td>
        <td><span style="color: rgb(98,151,85);">重新开启下载接口，仅在停止下载情况下才需要调起</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">stop</span>(String url)</td>
        <td><span style="color: rgb(98,151,85);">停止下载接口</span></td>
    </tr>
    <tr bgcolor="#fff">
        <td><span style="color: rgb(255,198,109);">delete</span>(String url)</td>
        <td><span style="color: rgb(98,151,85);">删除任务接口</span></td>
    </tr>
</table>
