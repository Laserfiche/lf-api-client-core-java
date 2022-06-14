package com.laserfiche.api.client.model;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;

public class ProblemDetails extends HashMap<String, Object> {
  @SerializedName("type")
  private String type = null;

  @SerializedName("title")
  private String title = null;

  @SerializedName("status")
  private Integer status = null;

  @SerializedName("detail")
  private String detail = null;

  @SerializedName("instance")
  private String instance = null;

   /**
   * Get type
   * @return type
  **/
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

   /**
   * Get title
   * @return title
  **/
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

   /**
   * Get status
   * @return status
  **/
  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

   /**
   * Get detail
   * @return detail
  **/
  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

   /**
   * Get instance
   * @return instance
  **/
  public String getInstance() {
    return instance;
  }

  public void setInstance(String instance) {
    this.instance = instance;
  }
}
