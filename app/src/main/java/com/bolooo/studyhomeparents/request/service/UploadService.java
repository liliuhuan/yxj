package com.bolooo.studyhomeparents.request.service;

import com.bolooo.studyhomeparents.utils.Constants;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * 上传文件使用
 * nanfeifei
 * 2017/2/27 15:03
 *
 * @version 3.7.0
 */
public interface UploadService {
  /**
   * 上传单图
   * @param description
   * @param file
   * @return
   */
  @Multipart
  @POST(Constants.UPLOAD_FILE_URL) Call<ResponseBody> uploadFile(
      @Part("description") RequestBody description, @Part MultipartBody.Part file);

  /**
   * 多文件上传
   * @param params
   * @return
   */
  @Multipart
  @POST(Constants.UPLOAD_FILE_URL)
  Call<String> uploadFiles(@PartMap Map<String, RequestBody> params);
}
