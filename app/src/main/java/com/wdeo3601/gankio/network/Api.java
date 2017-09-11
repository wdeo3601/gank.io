package com.wdeo3601.gankio.network;



import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author/Date: venjerLu / 8/9/2016  18:32 15:56
 * Email:       alwjlola@gmail.com
 * Description:
 */
public interface Api {
//    @Headers(Constants.ACCEPT + "v1.1")
//    @POST("sms/")
//    Observable<Data<String>> getVerificationCode(
//            @Body VerificationCode verificationCode);
//
//    @POST("sessions/")
//    Observable<Data<Login>> login(@Body User user);
//
//    @GET("images/")
//    Observable<Data<Photos>> getAlbum(@Query("uuid") String uuid,
//                                      @Query("page") int page);
//
//    @POST("conversations/")
//    Observable<Data<String>> postConversations(
//            @Body ConversationCreate conversationCreate
//    );
//
//    /**
//     * 检查是否能聊天
//     *
//     * @param uuid
//     * @return
//     */
//    @POST("conversations/check")
//    Observable<CheckConversationInfo> checkConversation(@Body Uuid uuid);
//
//    @GET("blacklist/")
//    Observable<Data<List<BlackList>>> getBlackLists();
//
//    @POST("blacklist/")
//    Observable<Data<String>> postBlackLists(@Body Uuid uuid);
//
//    @POST("blacklist/delete")
//    Observable<Data<String>> deleteBlackLists(@Body Uuid uuid);
//
//    @GET("address_book")
//    Observable<Data<AddressBook>> getAddress();
//
//    @POST("address_book")
//    Observable<Data<String>> addAddress(@Body AddAndUpdateAddress address);
//
//    @POST("address_book/update/")
//    Observable<Data<String>> updateAddress(@Body AddAndUpdateAddress address);
//
//    @FormUrlEncoded
//    @POST("address_book/delete/")
//    Observable<Data<String>> deleteAddress(
//            @Field("id") int id);
//
//    @FormUrlEncoded
//    @POST("address_book/default/")
//    Observable<Data<String>> defaultAddress(
//            @Field("id") int id);
//
//    /**
//     * 请求生成支付宝订单
//     */
//    @POST("payments/alipay")
//    Observable<Data<String>> getAliPayOrder(@Body PayItem item);
//
//    /**
//     * 请求生成微信订单
//     */
//    @POST("payments/wxpay")
//    Observable<Data<WxPayOrder>> getWxPayOrder(@Body PayItem item);
//
//    /**
//     * 接收支付宝异步通知
//     */
//    @POST("notifications/alipay")
//    Observable<Data<String>> getAliNotificaton();
//
//    /**
//     * 接收微信异步通知
//     */
//    @POST("notifications/wxpay")
//    Observable<Data<String>> getWxNotificaton();
//
//    @GET("address_book/region")
//    Observable<Data<ProvinceCityDistrict>> getProvinceCityDistrict();
//
//    @GET("filters")
//    Observable<Data<MeetFilter>> getFilter();
//
//    @POST("filters")
//    Observable<Data<String>> postFilter(@Query("type") String type,
//                                        @Body MeetFilter meetFilter);
//
//    @GET("cities")
//    Observable<Data<CityList>> getCities();
//
//    @GET("users/find")
//    Observable<Data<Friends>> findFriend(@Query("longitude") long longitude,
//                                         @Query("latitude") long latitude, @Query("page") int page);
//
//    @GET("users/find")
//    Observable<Data<Friends>> findFriend(@Query("page") int page);
//
//    /**
//     * 获取速配列表（升级到 v1.1）
//     *
//     * @return
//     */
//    @Headers(Constants.ACCEPT + "v1.1")
//    @GET("users/meet")
//    Observable<Data<List<Meet>>> meet();
//
//    @POST("users/vote")
//    Observable<Data<String>> vote(@Body Vote vote);
//
//    @POST("users/report")
//    Observable<Data<String>> report(@Body Report report);
//
//    /**
//     * 获取用户信息，我的、用户主页（升级到 v1.1）
//     *
//     * @param uuid
//     * @return
//     */
//    @Headers(Constants.ACCEPT + "v1.1")
//    @GET("users/{uuid}")
//    Observable<Data<PersonalInfo>> getUserInfo(@Path("uuid") String uuid);
//
//    /**
//     * 用于在会话界面获取用户聊天引导里边的数据
//     *
//     * @param uuid
//     * @return
//     */
//    @GET("conversations/topic_guide")
//    Observable<Data<String>> getOtherInterests(@Query("uuid") String uuid);
//
//    @GET("interests")
//    Observable<Data<List<String>>> getInterests();
//
//    @POST("interests")
//    Observable<Data<String>> postInterests(@Query("uuid") String uuid,
//                                           @Body Interest interest);
//
//    @POST("interests/delete")
//    Observable<Data<String>> deleteInterests(@Body Interest interest);
//
//    @POST("images")
//    Observable<Data<String>> postImage(
//            @Header("Content-Disposition") String contentDisposition, @Query("type") String type,
//            @Query("uuid") String uuid, @Body RequestBody file);
//
//    @POST("images/delete")
//    Observable<Data<String>> deleteImage(@Body MD5 md5);
//
//    @POST("images/first_photo")
//    Observable<Data<String>> setFirstPhoto(@Body MD5 md5);
//
//    @Headers(Constants.ACCEPT + "v1.1")
//    @POST("images/avatar")
//    Observable<Data<String>> setAvatar(@Body MD5 md5);
//
//    @GET("users/bill")
//    Observable<Data<Bills>> getBill(@Query("page") int page);
//
//    @POST("certificates/photo")
//    Observable<Data<String>> certifyPhoto(
//            @Header("Content-Disposition") String contentDisposition, @Body RequestBody fileBytes);
//
//    @POST("certificates/id_number")
//    Observable<Data<String>> certifyRealName(@Body CertifyRealNameModel realNameModel);
//
//    @Multipart
//    @POST("certificates/car")
//    Observable<Data<String>> certifyCar(
//            @Part("model") RequestBody model, @Part MultipartBody.Part file);
//
//    @Multipart
//    @POST("certificates/house")
//    Observable<Data<String>> certifyHouse(
//            @Part("mortgage") RequestBody mortgage, @Part MultipartBody.Part file);
//
//    @Multipart
//    @POST("certificates/degree")
//    Observable<Data<String>> certifyDegree(
//            @Part("school") RequestBody school, @Part MultipartBody.Part file);
//
//    @POST("favorites")
//    Observable<Data<String>> addAsFavorite(@Body Uuid uuid);
//
//    @POST("favorites/delete")
//    Observable<Data<String>> deleteFavorite(@Body Uuid uuid);
//
//    /**
//     * 找回密码中的修改密码
//     */
//    @Headers(Constants.ACCEPT + "v1.1")
//    @POST("users/update")
//    Observable<Data<UserInfo>> updatePassword(
//            @Header("Authorization") String token, @Body Password password);
//
//    /**
//     * 登录后设置-修改密码
//     */
//    @Headers(Constants.ACCEPT + "v1.1")
//    @POST("users/update")
//    Observable<Data<UserInfo>> updatePassword(@Body Password password);
//
//    @FormUrlEncoded
//    @Headers(Constants.ACCEPT + "v1.1")
//    @POST("users/update")
//    Observable<Data<UserInfo>> updateUserInfo(
//            @FieldMap() HashMap<String, String> map);
//
//    @Headers(Constants.ACCEPT + "v1.1")
//    @POST("users/update")
//    Observable<Data<UserInfo>> updateLocation(@Body Location city);
//
//    @Headers(Constants.ACCEPT + "v1.1")
//    @POST("users/update")
//    Observable<Data<UserInfo>> updateLivingplace(@Body
//                                                         LivingPlaceModel livingPlaceModel);
//
//    @GET("users/visitor")
//    Observable<Data<Visitors>> visitor(@Query("page") int page);
//
//    /**
//     * 获取配对数据
//     */
//    @GET("users/matched")
//    Observable<Data<MatchedList>> matched(@Query("page") int page);
//
//    /**
//     * 获取喜欢我数据
//     */
//    @GET("users/liked_me")
//    Observable<Data<LikedMeList>> likeMe(@Query("page") int page);
//
//    /**
//     * 获取我最爱的用户
//     */
//    @GET("favorites")
//    Observable<Data<List<FavoriteOne>>> favorite();
//
//    /**
//     * 获取我最爱的用户
//     */
//    @GET("favorites/me")
//    Observable<Data<FavoriteMeList>> favoriteMe(@Query("page") int page);
//
//    /**
//     * 获取充值卡
//     */
//    @Headers(Constants.ACCEPT + "v1.1")
//    @GET("diamonds/refill")
//    Observable<Data<RechargeItems>> getRechargeCards(@Query("type") String type);
//
//    /**
//     * 短信验证码验证
//     */
//    @Headers(Constants.ACCEPT + "v1.1")
//    @POST("sms/verify")
//    Observable<Data<SMSVerfyResponse>> smsVerify(@Body User user);
//
//    /**
//     * 短信验证码验证找回密码
//     */
//    @Headers(Constants.ACCEPT + "v1.1")
//    @POST("sms/verify")
//    Observable<Data<VerifyFindPwd>> smsVerifyFindPwd(@Body User user);
//
//    /**
//     * 短信验证码修改密码
//     */
//    @Headers(Constants.ACCEPT + "v1.1")
//    @POST("sms/verify")
//    Observable<Data<String>> smsVerifyChangePwd(@Body User user);
//
//    /**
//     * 第一次开启竞价排名
//     */
//    @POST("ppc")
//    Observable<Data<String>> openPPCFirst(@Body ChangePPC changePpc);
//
//    /**
//     * 关闭竞价排名
//     */
//    @POST("ppc/close")
//    Observable<Data<String>> closePPC();
//
//    @POST("ppc/update")
//    Observable<Data<String>> updatePPC(@Body ChangePPC changePpc);
//
//    /**
//     * 获取竞价排名的数据
//     */
//    @GET("ppc")
//    Observable<Data<PPC>> getPpc();
//
//    /**
//     * 检测昵称
//     */
//    @POST("users/verify_nickname")
//    Observable<Data<String>> checkNickName(@Body NickName nickName);
//
//    /**
//     * 上传注册信息
//     */
//    @Headers(Constants.ACCEPT + "v1.1")
//    @Multipart
//    @POST("users")
//    Observable<Data<RegisterModel>> uploadRegisterInfo(
//            @Part("mobile") RequestBody mobile, @Part("captcha") RequestBody captcha,
//            @Part("gender") RequestBody gender, @Part("purpose") RequestBody purpose,
//            @Part("want_gender") RequestBody want_gender, @Part("age_min") RequestBody age_min,
//            @Part("age_max") RequestBody age_max, @Part("nickname") RequestBody nickname,
//            @Part("birthday") RequestBody birthday, @Part MultipartBody.Part avatar,
//            @Part("sexual_orientation") RequestBody sexualOrientation,
//            @Part("oauth_type") RequestBody oauth_type, @Part("open_id") RequestBody open_id);
//
//    /**
//     * 随便看看
//     */
//    @GET("trials/meet")
//    Observable<Data<TravelMeet>> trials(@Query("gender") String gender);
//
//    /**
//     * 退出
//     */
//    @GET("sessions/delete")
//    Observable<Data<String>> delete();
//
//    /**
//     * 付费
//     */
//    @POST("diamonds/cost")
//    Observable<Data<String>> cost(@Body Cost cost);
//
//    @POST("mutes")
//    Observable<Data<String>> mutesMatched(@Body Mute mute);
//
//    @POST("mutes/favorite_me")
//    Observable<Data<String>> mutesFavoriteMe(@Body Uuid uuid);
//
//    //region 已弃用
//    @GET("guides/liked_me")
//    Observable<Data<GuideCostUser>> guideLikeMe(@Query("uuid") String uuid);
//
//    @GET("guides/favorite_me")
//    Observable<Data<GuideCostUser>> guideFavoriteMe(
//            @Query("uuid") String uuid);
//    //endregion
//
//    /**
//     * 获取群发价格以及系统默认的群发文本，用户选择默认的文本可以不经审核直接发送。
//     */
//    @GET("mass/settings")
//    Observable<Data<MassModel>> getMassTextAndPrice();
//
//    /**
//     * 获取群发状态，能否退款，能否更新群发内容。
//     */
//    @GET("mass/status")
//    Observable<Data<MassStatusModel>> getMassStatus(@Query("mass_id") String mass_id);
//
//
//    /**
//     * 获取群发消息的接收人
//     */
//    @GET("mass/{massId}")
//    Observable<Data<MassReceiverModel>> getMassReceiver(
//            @Path("massId") String mass_id, @Query("page") int page);
//
//    /**
//     * 点击创建群发
//     */
//    @POST("mass")
//    Observable<Data<CreateMassResultModel>> createMassByClick(
//            @Body CreateMassByClickModel massModel);
//
//    /**
//     * 拖动创建群发
//     */
//    @POST("mass")
//    Observable<Data<CreateMassResultModel>> createMassByBatch(
//            @Body CreateMassByBatchModel massModel);
//
//    /**
//     * 修改群发消息重新提交
//     */
//    @POST("mass/update")
//    Observable<Data<CreateMassResultModel>> updateMassText(
//            @Body UpdateMassTextModel massModel);
//
//    /**
//     * 群发退款
//     */
//    @POST("mass/refund")
//    Observable<Data<String>> massRefund(@Body MassRefundModel massModel);
//
//    /**
//     * 注册设备
//     */
//    @POST("devices/register")
//    Observable<Data<String>> registerDevices(@Body DeviceModel massModel);
//
//    /**
//     * 获取服务价格
//     */
//    @GET("attractions/settings")
//    Observable<Data<AttractionsPriceModel>> getAttractionsPrice();
//
//    /**
//     * 获取颜值提升报告
//     */
//    @GET("attractions/reports")
//    Observable<Data<AttractionsReportModel>> getAttractionsReports();
//
//    /**
//     * 更新用户GPS坐标，返回城市
//     */
//    @POST("users/gps")
//    Observable<Data<GPSCityModel>> getCityByGPS(@Body GPS gps);
//
//    /**
//     * 上传通讯录数据
//     */
//    @POST("contacts/receive")
//    Observable<Data<UnshieldContactsList>> uploadContacts(
//            @Body Data<List<ContactsModel>> data);
//
//    /**
//     * 恢复屏蔽某个用户
//     *
//     * @param contactsModel
//     * @return
//     */
//    @POST("contacts/block")
//    Observable<Data<String>> shield(@Body ContactsModel contactsModel);
//
//    /**
//     * 解除屏蔽某个用户
//     *
//     * @param contactsModel
//     * @return
//     */
//    @POST("contacts/unblock")
//    Observable<Data<String>> unShield(@Body ContactsModel contactsModel);
//
//    /**
//     * 关闭屏蔽通讯录功能
//     */
//    @GET("contacts/close")
//    Observable<Data<String>> clodeBlockContacts();
//
//    /**
//     * app被杀死期间用户收到暂态消息，再次打开时获取暂态消息未读数量
//     */
//    @GET("synchronization")
//    Observable<Data<TransientMessageNumberModel>> getTransientMessageNumber();
//
//    /**
//     * 获取用户的在线状态
//     *
//     * @return
//     */
//    @GET("users/{uuid}/statuses")
//    Observable<Data<String>> getUserStatus(@Path("uuid") String uuid);
//
//    /**
//     * 正式版检查更新的接口
//     *
//     * @param versionCode
//     * @return
//     */
//    @POST("versions/android")
//    Observable<Data<UpdateVersionInfo>> checkReleaseUpdate(@Body VersionCode versionCode);
//
//    /**
//     * 向TA提问api
//     *
//     * @param putQuestionModel
//     * @return
//     */
//    @POST("questions")
//    Observable<Data<List<String>>> putQuestion(@Body PutQuestionModel putQuestionModel);
//
//    /**
//     * 获取我的提问的列表
//     *
//     * @param type no_answer、answered
//     * @param page 页码
//     * @return
//     */
//    @GET("questions")
//    Observable<Data<QuestionsListModel>> getQuestionsList(@Query("type") String type, @Query("page") int page);
//
//    /**
//     * 获取问题详情
//     *
//     * @param question_id
//     * @return
//     */
//    @GET("questions/{question_id}")
//    Observable<Data<QuestionDetailModel>> getQuestionDetail(@Path("question_id") int question_id);
//
//    /**
//     * 我的提问-已回答的等待确认的问题的申诉
//     *
//     * @param questionId
//     * @return
//     */
//    @POST("questions/complaint")
//    Observable<Data<String>> toAppeal(@Body QuestionId questionId);
//
//    /**
//     * 我的提问-已回答的等待确认的问题的确认
//     *
//     * @param questionId
//     * @return
//     */
//    @POST("questions/confirmation")
//    Observable<Data<String>> confirmAnswer(@Body QuestionId questionId);
//
//    /**
//     * 获取我的回答的列表
//     *
//     * @param type no_answer、answered
//     * @param page 页码
//     * @return
//     */
//    @GET("answers")
//    Observable<Data<AnswerListModel>> getAnswerList(@Query("type") String type, @Query("page") int page);
//
//    /**
//     * 获取我的回答详情
//     *
//     * @param question_id 问题id
//     * @return
//     */
//    @GET("answers/{question_id}")
//    Observable<Data<AnswerDetailModel>> getAnswerDetail(@Path("question_id") int question_id);
//
//    /**
//     * 拒绝回答问题
//     *
//     * @param questionId 问题id
//     * @return
//     */
//    @POST("answers/reject")
//    Observable<Data<String>> rejectAnswer(@Body QuestionId questionId);
//
//    /**
//     * 提交问题的回答
//     *
//     * @param questionAnswerRequestModel
//     * @return
//     */
//    @POST("answers")
//    Observable<Data<String>> submitQuestionAnswer(@Body QuestionAnswerRequestModel questionAnswerRequestModel);
//
//    /**
//     * 样式主页 获取公开回答的列表
//     *
//     * @param uuid
//     * @param page
//     * @return
//     */
//    @GET("users/answer/")
//    Observable<Data<PublicAnswers>> getPublicAnswers(@Query("uuid") String uuid, @Query("page") int page);
//
//    /**
//     * 公开回答，点赞
//     *
//     * @param questionId
//     * @return
//     */
//    @POST("answers/like")
//    Observable<Data<String>> fabulous(@Body QuestionId questionId);
//
//    /**
//     * 获取我的礼物-收到、送出的列表
//     *
//     * @param type receive：我收到的礼物的列表，send：我送出的礼物的列表
//     * @param page 分页加载传页数
//     * @return
//     */
//    @GET("gifts/my/")
//    Observable<Data<GiftList>> getMyGift(@Query("type") String type, @Query("page") int page);
//
//    /**
//     * 出售礼物
//     *
//     * @param saleGiftId
//     * @return
//     */
//    @POST("gifts/sale")
//    Observable<Data<String>> saleGift(@Body RequestSaleGiftId saleGiftId);
//
//    /**
//     * 获取礼物商城的礼物列表
//     *
//     * @param type simple：简版礼物，luxury：奢版礼物
//     * @return
//     */
//    @GET("gifts/")
//    Observable<Data<List<GiftShop>>> getGiftShopList(@Query("type") String type);
//
//    /**
//     * 赠送礼物
//     *
//     * @param requestSendGift
//     * @return
//     */
//    @POST("gifts/send")
//    Observable<Data<String>> sendGift(@Body RequestSendGift requestSendGift);
//
//    /**
//     * 获取 vip 礼包列表
//     *
//     * @return
//     */
//    @GET("packs")
//    Observable<Data<List<VipGift>>> getVipGifts();
//
//    /**
//     * 领取vip礼包
//     *
//     * @param requestVipId
//     * @return
//     */
//    @POST("packs/receive")
//    Observable<Data<String>> receiveVipGift(@Body RequestVipId requestVipId);
//
//    /**
//     * 获取我的道具列表
//     *
//     * @return
//     */
//    @GET("users/prop")
//    Observable<Data<List<PropCard>>> getMyProps();
//
//    /**
//     * 获取vip等级信息
//     *
//     * @param uuid
//     * @return
//     */
//    @GET("users/vip/")
//    Observable<Data<VipLevel>> getVipLevel(@Query("uuid") String uuid);
//
//    /**
//     * 邀请对方为我付费
//     *
//     * @param payFoeMeRequestModel
//     * @return
//     */
//    @POST("diamonds/pay_for_me")
//    Observable<Data<String>> PayForMe(@Body PayForMeRequestModel payFoeMeRequestModel);
//
//    /**
//     * 群发勾搭确认付费接口，用于需要道具卡抵扣趣币的地方
//     *
//     * @param action         需要确认付费的付费类型
//     * @param receiver_count 群发的用户数量
//     * @return
//     */
//    @GET("diamonds/message/")
//    Observable<Data<String>> confirmMassPay(@Query("action") String action
//            , @Query("receiver_count") int receiver_count);
//
//    /**
//     * 增加最爱人数上限确认付费接口，用于需要道具卡抵扣趣币的地方
//     *
//     * @param action 需要确认付费的付费类型
//     * @return
//     */
//    @GET("diamonds/message/")
//    Observable<Data<String>> confirmAddFavoritePay(@Query("action") String action);
//
//    /**
//     * 送礼物确认付费接口，用于需要道具卡抵扣趣币的地方
//     *
//     * @param action 需要确认付费的付费类型
//     * @return
//     */
//    @GET("diamonds/message/")
//    Observable<Data<String>> confirmSendGiftPay(@Query("action") String action, @Query("gift_id") int gift_id, @Query("uuid") String uuid);
//
//    /**
//     * 获取编辑关于我的选项/已经当前选中的值
//     *
//     * @return
//     */
//    @GET("users/info_option")
//    Observable<Data<InfoOptionsModel>> getInfoOption();
//
//    /**
//     * 邀请对方填写隐私资料
//     *
//     * @param uuid
//     * @return
//     */
//    @POST("invitations/privacy")
//    Observable<Data<String>> invitePrivacy(@Body Uuid uuid);
//
//    /**
//     * 微信登录
//     *
//     * @param weChatLoginRequest
//     * @return
//     */
//    @POST("sessions/social_login")
//    Observable<Data<WeChatLoginResponse>> weChatLogin(@Body WeChatLoginRequest weChatLoginRequest);
//
//    /**
//     * qq登录
//     *
//     * @param qqLoginRequest
//     * @return
//     */
//    @POST("sessions/social_login")
//    Observable<Data<QQLoginResponse>> qqLogin(@Body QQLoginRequest qqLoginRequest);
//
//    /**
//     * 获取推荐的说说
//     *
//     * @param max_id
//     * @return
//     */
//    @GET("statuses/home_time_line")
//    Observable<Data<TalkListModel>> getPublicTalkList(@Query("max_id") int max_id);
//
//    /**
//     * 发表说说
//     *
//     * @return
//     */
//    @Multipart
//    @POST("statuses")
//    Observable<Data<String>> publishTalk(@Part("content") RequestBody content
//            , @Part("longitude") RequestBody longitude
//            , @Part("latitude") RequestBody latitude
//            , @Part() List<MultipartBody.Part> parts);
//
//    /**
//     * 发表评论
//     *
//     * @param model
//     * @return
//     */
//    @POST("comments")
//    Observable<Data<TalkCommentModel>> publishComment(@Body PublishCommentRequestModel model);
//
//    /**
//     * 删除自己的评论
//     * @param talkIdRequestModel
//     * @return
//     */
//    @POST("comments/delete")
//    Observable<Data> deleteComment(@Body TalkIdRequestModel talkIdRequestModel);
//
//    /**
//     * 获取说说列表
//     *
//     * @param max_id
//     * @param uuid
//     * @return
//     */
//    @GET("statuses/user_time_line")
//    Observable<Data<OneUserTalkListModel>> getTalkList(@Query("max_id") int max_id, @Query("uuid") String uuid);
//
//    /**
//     * 获取说说详情（喜欢/评论）
//     *
//     * @param talk_id
//     * @param uuid
//     * @return
//     */
//    @GET("statuses/{talk_id}")
//    Observable<Data<TalkDetailModel>> getTalkDetail(@Path("talk_id") int talk_id, @Query("uuid") String uuid);
//
//    /**
//     * 单独获取评论
//     *
//     * @param talk_id 说说ID
//     * @param max_id  加载更多
//     * @return
//     */
//    @GET("comments")
//    Observable<Data<TalkCommentListModel>> getTalkComments(@Query("status_id") int talk_id, @Query("max_id") int max_id);
//
//    /**
//     * 单独获取喜欢
//     *
//     * @param status_id 说说ID
//     * @param max_id    下一页最大ID
//     * @return
//     */
//    @GET("statuses/like_list")
//    Observable<Data<FabulousListModel>> getFabulousList(@Query("status_id") int status_id, @Query("max_id") int max_id);
//
//    /**
//     * 消息列表（获取动态）
//     * 获取自己的已通过审核的说说评论/喜欢
//     *
//     * @param max_id 0 获取最新
//     * @return
//     */
//    @GET("comments/related_to_me")
//    Observable<Data<TalkMessageListModel>> getTalkMessage(@Query("max_id") int max_id);
//
//    /**
//     * 清空动态
//     * @return
//     */
//    @POST("comments/notifications")
//    Observable<Data> clearTalkMessage();
//
//    /**
//     * 喜欢了说说
//     *
//     * @param talkIdRequestModel
//     * @return
//     */
//    @POST("statuses/like")
//    Observable<Data<String>> fabulousTalk(@Body TalkIdRequestModel talkIdRequestModel);
//
//    /**
//     * 删除说说
//     *
//     * @param talkIdRequestModel
//     * @return
//     */
//    @POST("statuses/delete")
//    Observable<Data<String>> deleteTalk(@Body TalkIdRequestModel talkIdRequestModel);
//
//    /**
//     * 任务/奖励
//     * 获取任务列表
//     *
//     * @return
//     */
//    @GET("users/task")
//    Observable<Data<TaskAndRewardListModel>> getTaskList();
//
//    /**
//     * 获取奖励列表
//     *
//     * @return
//     */
//    @GET("rewards")
//    Observable<Data<TaskAndRewardListModel>> getRewardList();
//
//    /**
//     * 领取奖励
//     *
//     * @param getRewardRequestModel
//     * @return
//     */
//    @POST("rewards/receive")
//    Observable<Data<String>> getReward(@Body GetRewardRequestModel getRewardRequestModel);
}
