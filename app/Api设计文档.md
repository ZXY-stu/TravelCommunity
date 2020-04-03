# TravelCommunityService
# 项目服务器
## 暂时实现部分功能
  - 实现用户登陆，注册，设置用户基本信息包括昵称，年龄，头像，爱好等等
  - 实现用户发送自身动态信息，包括文字、图片以及视频动态信息的发表，类似微信朋友圈
  - 实现用户互相评论、点赞，参考微信朋友圈的评论和点赞。
  - 实现好友查询，添加好友，按好友分组显示动态信息
## 数据库表 
  - user   用户表，保存了用户相关信息
  - userRelation 用户关系表，保存了用户之间的相互关系
  - commentsMsg   评论表 ,保存了所有用户评论信息
  - personDynamic  朋友动态表 ,保存了所有注册用户发出的朋友圈动态
  - DynamicAccessShield 朋友圈屏蔽表
  - DynamicAccessAllow 朋友圈开放表
  - like 朋友点赞表
### 数据库相关的模块见data/db/entity.kt
## 用户模块 
### 参数说明 
- Model.BackInfo.describe返回值分为两部分
- {Report + Content}字符串
- Report 指的是处理结果
- Content 指的是内容数据，需要将其处理成Josn的格式加到Report后面一起返回
- 因此，BackInfo返回内容 = Report + Content 
- 若需要传回必要数据，返回Report + Content 
- 若无有效数据或者不需要额外的回传数据，仅返回Report
### 登录Api
- @GET("api/user")
- Call<Model.BackInfo>  login()
- 参数1：@Query("account") String
- 参数2：@Query("password") String  
  - GET请求，返回值{Model.BackInfo}
  - 登陆成功，设置Model.BackInfo.describe = "TC001"+Content，并返回
  - 登陆异常，设置Model.BackInfo.describe = "TC002"，并返回
  - 无此账号，设置Model.BackInfo.describe = "TC003" 并返回
  - Content为User表中的基本信息，不需要回传密码
### 注册Api 
- @POST("api/user")
- Call<Model.BackInfo> register()
- 参数：@Body("user") User
  - POST请求,返回值{Model.BackInfo}
  - 注册成功，设置Model.BackInfo.describe = "TC101"后返回
  - 异常失败，设置Model.BackInfo.describe = "TC102"后返回
### 注销Api
- @DELETE("api/user/{account}")
- Call<Model.BackInfo> logout()
- 参数：@Path("account") String
  - DELETE请求，返回{Model.BackInfo}
  - 注销成功，设置Model.BackInfo.describe = "TC201"后返回
  - 异常失败，设置Model.Backinfo.describe = "TC202"后返回
  - 无此账号，设置Model.backInfo.describe = "TC203"后返回
### 更新用户信息Api
- @POST("api/user/{account}")
- Call<Model.BackInfo> updateUser()
- 参数1：@Path("account") String
- 参数2：@PartMap Map<String,RequestBody> contentArgs
   - POST请求，支持图文同时上传，返回{Model.BackInfo} 
   - 成功，设置Model.BackInfo.describe = "TC301"后返回
   - 异常失败,设置Model.BackInfo.describe = "TC302"后返回
   - 无此账号,设置Model.BackInfo.describe = "TC303"后返回
### 查询好友APi
- @GET("api/user/{userInfo}")
- Call<Model.BackInfo> queryUser()
- 参数：@Path("userInfo") String
  - GET请求，userInfo可以是昵称，可以是用户名,返回{Model.BackInfo}
  - 成功，设置Model.BackInfo.describe = "TC401" + Content
  - 无此用户，设置Model.BackInfo.describe = "TC402"
  - 异常失败，设置Model.BackInfo.describe = "TC403"
  - Content为好友的基本信息，不包含隐私信息如身份证，密码等等
### 添加好友附带确认APi
- @POST("api/user/{friendAccount}")
- Call<Model.BackInfo> addUserWithBack()
- 参数1: @Path("friendAccount") String
- 参数2：@QueryMap Map<String,String> addWithBackArgs
- addWithBack包含{userAccount="用户账号",groupId="设置所属分组ID",isRequireUser="是否是发起人"}
  - POST请求，friendAccount为对方账号，userAccount为用户账号，返回{Model.BackInfo}
  - 成功申请添加好友,设置Model.BackInfo.describe = "TC501" （此时，isRequireUser == "true"）
  - 成功确认添加好友，设置Model.BackInfo.describe = "TC502" (此时，isRequireUser == "false")
  - 异常失败,设置Model.BackInfo.describe = "TC503"
### 获取用户好友APi
- @GET("api/user/lists")
- Call<Model.BackInfo> getFriends()
- 参数1：@Query("account") String
  - GET请求，获取account账户的好友，返回{Model.backInfo}
  - 成功，设置Model.BackInfo.describe = "TC1101" + Content 后返回
  - 失败，设置Model.BackInfo.describe = "TC1102"后返回
  - Content为获取到的好友列表内容
## 动态模块  
### 动态模块主要实现包括用户图文或者视频内容的上传和删除以及查询，发布内容按时间排序，查询内容按发布时间获取
### 参数说明
- permissionId表示朋友圈访问权限类型
- when(permissionId)
- == 0 -> 所有用户都可见
- == 1 -> 指定好友可见
- == 2 -> 除了指定好友，其它都可见 
### 图文或者视频文字上传API
- @POST("api/user/dynamic")
- Call<Model.BackInfo> upLoadDynamic()
- 参数1：@Query("account") String
- 参数2：@QueryMap Map<String,String> permissionArgs 
- 参数3：@PartMap Map<String,RequestBody> contentArgs
- permissionArgs表示访问权限列表,user_n表示指定的用户，user_n如果没有，则表示均可见
- {permissionId="权限类型",user_1="",user_2="",...}
- contentArgs表示内容列表，包含图片、文字、视频上传的RequestBody
  - POST请求，支持图文或者视频文字同时上传，返回<Model.BackInfo>
  - 成功，设置Model.BackInfo.describe = "TC601"后返回
  - 异常失败，设置Model.BackInfo.describe = "TC602"后返回
  - 无此账号，设置Model.BackInfo.describe = "TC603"后返回
### 删除动态API
- @DELETE("api/user/dynamic/{dynamicId}")
- Call<Model.BackInfo> deleteDynamic()
- 参数：@Path("dynamicId") Int
  - DELETE请求，删除动态，返回<Model.BackInfo>
  - 成功，设置Model.BackInfo.describe = "TC701"后返回
  - 异常失败，设置Model.BackInfo.describe = "TC702"后返回
### 查询动态API
- @GET("api/user/dynamic")
- Call<Model.BackInfo> getDynamics() 
- 参数：@QueryMap  Map<String,String>  queryDynamicArgs   
- 参数包含内容：{account="用户名"},{limitNumbers="动态数量"}
- 当{account.isEmpty == true}时，查询所有好友用户的按时间排序的limitNumbers条动态 
- 当{account.isEmpty == false}时，查询account用户的按时间排序的limitNumbers条动态  
  - GET请求，返回{Model.BackInfo}
  - 成功，设置Model.BackInfo.describe = "TC801" + Content后返回
  - 失败，设置Model.BackInfo.describe = "TC802"后返回
  - Content包含了指定数量的PersonDynamic表中的所有内容
## 评论模块 
### 评论APi
- @POST("api/user/comments")
- Call<Model.BackInfo> comments()
- 参数1: @Body("message") String
- 参数2: @QueryMap Map<String,String> commentsArgs
- 参数commentsArgs包含
- {dynamicId="评论哪一条", userNickName="评论人的昵称"}
- {friendNickName="被评论人的昵称"，time="评论时间"}
  - POST请求，返回{Model.BackInfo}
  - 成功，设置Model.BackInfo.describe = "TC901"后返回
  - 失败,设置Model.BackInfo.describe = "TC902" 后返回
### 评论查询APi 
- @GET("api/user/comments")
- Call<Model.BackInfo> queryComments()
- 参数: @QueryMap queryCommentsArgs
- queryCommentsArgs主要有{myAccount="我的账号"，dynamicId="查询的动态id"，limitNumbers="获取评论的数量"}
  - 通过查询访问控制列表，获得可访问的用户账户列表,然后查询属于dynamicId的评论人
  - GET请求，返回{Model.BackInfo}
  - 成功，设置Model.BackInfo.describe = "TC1001" + Content 后返回
  - 失败，设置Model.BackInfo.describe = "TC1002" 后返回
  - Content为CommentsMsg中的相关数据内容
### 评论删除APi
- @DELETE("api/user/comments")
- Call<Model.BackInfo> deleteComments()
- 参数：@Query("dynamicId") Int 
  - Delete请求，删除dynamicId所属的评论，返回{Model.BackInfo}
  - 成功，设置Model.BackInfo.describe = "TC1201"后返回
  - 失败，设置Model.BackInfo.describe = "TC1202"后返回
 ## 点赞模块
 ### 点赞APi
 - @POST("api/user/like")
 - Call<Model.BackInfo> addLike()
 - 参数：@QueryMap Map<String，String> likeArgs
 - likeArgs={dynamicId= "",account= ""}
 - POST请求，返回{Model.BackInfo}
 - 成功，设置Model.BackInfo.describe = "TC1301"后返回
 - 失败，设置Model.BackInfo.describe = "TC1302"后返回
 ### 取消点赞APi
 - @DELETE("api/user/like")
 - Call<Model.BackInfo> cancelLike()
 - 参数：@Query("dynamicId") String
   - DELETE请求，删除指定dynamic的点赞，返回{Model.BackInfo}
   - 成功，设置Model.BackInfo.describe = "TC1401"后返回
   - 失败，设置Model.BackInfo.describe = "TC1402"后返回
