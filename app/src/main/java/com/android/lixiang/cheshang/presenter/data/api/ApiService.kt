package com.android.lixiang.cheshang.presenter.data.api

import com.android.lixiang.cheshang.presenter.data.bean.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @Headers("Content-Type:text/html;charset=utf-8", "Accept:application/json;")
    @GET("/user/getShopsByHrAccount")
    fun getShopsByHrAccount(@Query("hrAccount") param: String): Observable<GetShopsByHrAccountBean>

    @Headers("Content-Type:text/html;charset=utf-8", "Accept:application/json;")
    @GET("/app/mission/getMissionByHrAccount")
    fun getMissionByHrAccount(@Query("hrAccount") param: String): Observable<GetMissionByHrAccountBean>

    @Headers("Content-Type:text/html;charset=utf-8", "Accept:application/json;")
    @GET("/userShop/getShopByHrAccountAndPosition")
    fun getShopByHrAccountAndPosition(@Query("hrAccount") param: String, @Query("longitude") longitude: String, @Query("latitude") latitude: String): Observable<GetShopByHrAccountAndPositionBean>

    @POST("/user/login")
    @FormUrlEncoded
    fun login(@Field("hrAccount") targetSentence: String,
              @Field("tel") targetSentence2: String,
              @Field("deviceId") targetSentence3: String): Observable<LoginBean>

    @POST("/user/updateLoginInfo")
    @FormUrlEncoded
    fun updateLoginInfo(@Field("hrAccount") targetSentence: String,
                        @Field("deviceId") targetSentence2: String,
                        @Field("tel") targetSentence3: String): Observable<UpdateLoginInfoBean>

    @Headers("Content-Type:text/html;charset=utf-8", "Accept:application/json;")
    @GET("/user/getUserByHrAccount")
    fun getUserByHrAccount(@Query("hrAccount") param: String): Observable<GetUserByHrAccountBean>

    @POST("/information/addInformation")
    @FormUrlEncoded
    fun addInformation(@Field("infoId") infoId: String,
                       @Field("shopId") shopId: String,
                       @Field("shopName") shopName: String,
                       @Field("shopAddress") shopAddress: String,
                       @Field("type") type: String,
                       @Field("detail") detail: String,
                       @Field("hrAccount") hrAccount: String,
                       @Field("reporter") reporter: String): Observable<AddInfomationBean>

    @POST("/license/addLicense")
    @FormUrlEncoded
    fun addLicense(@Field("plateNumber") plateNumber: String,
                   @Field("shopId") shopId: String,
                   @Field("shopName") shopName: String,
                   @Field("shopAddress") shopAddress: String,
                   @Field("identiCode") identiCode: String,
                   @Field("brandModel") brandModel: String,
                   @Field("owner") owner: String,
                   @Field("tel") tel: String,
                   @Field("reporter") reporter: String,
                   @Field("hrAccount") hrAccount: String,
                   @Field("registDate") registDate: String,
                   @Field("engineNumber") engineNumber: String,
                   @Field("dueDate") dueDate: String,
                   @Field("insuranceCompany") insuranceCompany: String): Observable<AddLicenseBean>

    @POST("/attendance/addAttendance")
    @FormUrlEncoded
    fun addAttendance(@Field("hrAccount") hrAccount: String,
                      @Field("shopId") shopId: String,
                      @Field("attendanceFlag") attendanceFlag: String,
                      @Field("position") position: String,
                      @Field("longitude") longitude: String,
                      @Field("latitude") latitude: String): Observable<AddAttendanceBean>


    @POST("/attendance/updateReasonByCreateTime")
    @FormUrlEncoded
    fun updateReasonByCreateTime(@Field("createTime") hrAccount: String,
                      @Field("reason") shopId: String,
                      @Field("hrAccount") attendanceFlag: String): Observable<UpdateReasonByCreateTimeBean>


    @POST("/app/mission/getMissionList")
    @FormUrlEncoded
    fun getMissionList(@Field("hrAccount") hrAccount: String, @Field("shopId") shopId: String): Observable<GetMissionListBean>

    @Headers("Content-Type:text/html;charset=utf-8", "Accept:application/json;")
    @GET("/attendance/getAllAttendanceByHrAccount")
    fun getAllAttendanceByHrAccount(@Query("hrAccount") hrAccount: String): Observable<GetAllAttendanceByHrAccountBean>

    @POST("user/addRegistrationId")
    @FormUrlEncoded
    fun addRegistrationId(@Field("hrAccount") hrAccount: String,
                          @Field("registrationId") registrationId: String): Call<AddRegistrationIdBean>



    @POST("/attendance/updateAttendanceByHrAccount")
    @FormUrlEncoded
    fun updateAttendanceByHrAccount(@Field("hrAccount") hrAccount: String,
                                    @Field("shopId") shopId: String,
                                    @Field("attendanceFlag") attendanceFlag: String,
                                    @Field("position") position: String,
                                    @Field("longitude") longitude: String,
                                    @Field("latitude") latitude: String,
                                    @Field("oldCreateTime") oldCreateTime: String): Observable<UpdateAttendanceByHrAccountBean>

    @Headers("Content-Type:text/html;charset=utf-8", "Accept:application/json;")
    @GET("/license/getLicenseByHrAccount")
    fun getLicenseByHrAccount(@Query("hrAccount") hrAccount: String): Observable<GetLicenseByHrAccountBean>


    @Headers("Content-Type:text/html;charset=utf-8", "Accept:application/json;")
    @GET("/document/pushDocument")
    fun pushDocument(@Query("deptId") hrAccount: String): Observable<PushDocumentBean>


    @Headers("Content-Type:text/html;charset=utf-8", "Accept:application/json;")
    @GET("/app/push/getMessageList")
    fun getMessageList(@Query("hrAccount") hrAccount: String, @Query("pageSize") pageSize: String, @Query("pageNum") pageNum: String): Observable<GetMessageListBean>


    @POST("/app/mission/submitMissionAnswer")
    fun submitMissionAnswer(@Body submitMissionAnswer: Array<SubmitMissionAnswer>): Observable<SubmitMissionAnswerBean>

}
