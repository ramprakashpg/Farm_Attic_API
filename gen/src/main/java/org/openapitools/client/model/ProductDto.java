/*
 * Farm Attic API
 * Backend API for Farm Attic application
 *
 * The version of the OpenAPI document: 1.1.0
 * Contact: Fred@gigagantic-server.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.openapitools.client.model.ProductCategory;
import org.threeten.bp.OffsetDateTime;

/**
 * ProductDto
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-07-20T18:52:04.362234+05:30[Asia/Kolkata]")
public class ProductDto {
  public static final String SERIALIZED_NAME_PRODUCT_ID = "productId";
  @SerializedName(SERIALIZED_NAME_PRODUCT_ID)
  private UUID productId;

  public static final String SERIALIZED_NAME_USER_ID = "userId";
  @SerializedName(SERIALIZED_NAME_USER_ID)
  private UUID userId;

  public static final String SERIALIZED_NAME_PRODUCT_NAME = "productName";
  @SerializedName(SERIALIZED_NAME_PRODUCT_NAME)
  private String productName;

  public static final String SERIALIZED_NAME_PRODUCT_DESCRIPTION = "productDescription";
  @SerializedName(SERIALIZED_NAME_PRODUCT_DESCRIPTION)
  private String productDescription;

  public static final String SERIALIZED_NAME_QUANTITY = "quantity";
  @SerializedName(SERIALIZED_NAME_QUANTITY)
  private Integer quantity;

  public static final String SERIALIZED_NAME_PRODUCT_CATEGORY = "productCategory";
  @SerializedName(SERIALIZED_NAME_PRODUCT_CATEGORY)
  private ProductCategory productCategory;

  public static final String SERIALIZED_NAME_PRICE_PER_UNIT = "pricePerUnit";
  @SerializedName(SERIALIZED_NAME_PRICE_PER_UNIT)
  private Integer pricePerUnit;

  public static final String SERIALIZED_NAME_IMAGE_LIST = "imageList";
  @SerializedName(SERIALIZED_NAME_IMAGE_LIST)
  private List<List<byte[]>> imageList = new ArrayList<List<byte[]>>();

  public static final String SERIALIZED_NAME_UNIT = "unit";
  @SerializedName(SERIALIZED_NAME_UNIT)
  private String unit;

  public static final String SERIALIZED_NAME_EXPIRY_DATE = "expiryDate";
  @SerializedName(SERIALIZED_NAME_EXPIRY_DATE)
  private OffsetDateTime expiryDate;


  public ProductDto productId(UUID productId) {
    
    this.productId = productId;
    return this;
  }

   /**
   * Get productId
   * @return productId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public UUID getProductId() {
    return productId;
  }


  public void setProductId(UUID productId) {
    this.productId = productId;
  }


  public ProductDto userId(UUID userId) {
    
    this.userId = userId;
    return this;
  }

   /**
   * Get userId
   * @return userId
  **/
  @ApiModelProperty(required = true, value = "")

  public UUID getUserId() {
    return userId;
  }


  public void setUserId(UUID userId) {
    this.userId = userId;
  }


  public ProductDto productName(String productName) {
    
    this.productName = productName;
    return this;
  }

   /**
   * Get productName
   * @return productName
  **/
  @ApiModelProperty(required = true, value = "")

  public String getProductName() {
    return productName;
  }


  public void setProductName(String productName) {
    this.productName = productName;
  }


  public ProductDto productDescription(String productDescription) {
    
    this.productDescription = productDescription;
    return this;
  }

   /**
   * Get productDescription
   * @return productDescription
  **/
  @ApiModelProperty(required = true, value = "")

  public String getProductDescription() {
    return productDescription;
  }


  public void setProductDescription(String productDescription) {
    this.productDescription = productDescription;
  }


  public ProductDto quantity(Integer quantity) {
    
    this.quantity = quantity;
    return this;
  }

   /**
   * Get quantity
   * @return quantity
  **/
  @ApiModelProperty(required = true, value = "")

  public Integer getQuantity() {
    return quantity;
  }


  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }


  public ProductDto productCategory(ProductCategory productCategory) {
    
    this.productCategory = productCategory;
    return this;
  }

   /**
   * Get productCategory
   * @return productCategory
  **/
  @ApiModelProperty(required = true, value = "")

  public ProductCategory getProductCategory() {
    return productCategory;
  }


  public void setProductCategory(ProductCategory productCategory) {
    this.productCategory = productCategory;
  }


  public ProductDto pricePerUnit(Integer pricePerUnit) {
    
    this.pricePerUnit = pricePerUnit;
    return this;
  }

   /**
   * Get pricePerUnit
   * @return pricePerUnit
  **/
  @ApiModelProperty(required = true, value = "")

  public Integer getPricePerUnit() {
    return pricePerUnit;
  }


  public void setPricePerUnit(Integer pricePerUnit) {
    this.pricePerUnit = pricePerUnit;
  }


  public ProductDto imageList(List<List<byte[]>> imageList) {
    
    this.imageList = imageList;
    return this;
  }

  public ProductDto addImageListItem(List<byte[]> imageListItem) {
    this.imageList.add(imageListItem);
    return this;
  }

   /**
   * Get imageList
   * @return imageList
  **/
  @ApiModelProperty(required = true, value = "")

  public List<List<byte[]>> getImageList() {
    return imageList;
  }


  public void setImageList(List<List<byte[]>> imageList) {
    this.imageList = imageList;
  }


  public ProductDto unit(String unit) {
    
    this.unit = unit;
    return this;
  }

   /**
   * Get unit
   * @return unit
  **/
  @ApiModelProperty(required = true, value = "")

  public String getUnit() {
    return unit;
  }


  public void setUnit(String unit) {
    this.unit = unit;
  }


  public ProductDto expiryDate(OffsetDateTime expiryDate) {
    
    this.expiryDate = expiryDate;
    return this;
  }

   /**
   * Get expiryDate
   * @return expiryDate
  **/
  @ApiModelProperty(required = true, value = "")

  public OffsetDateTime getExpiryDate() {
    return expiryDate;
  }


  public void setExpiryDate(OffsetDateTime expiryDate) {
    this.expiryDate = expiryDate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductDto productDto = (ProductDto) o;
    return Objects.equals(this.productId, productDto.productId) &&
        Objects.equals(this.userId, productDto.userId) &&
        Objects.equals(this.productName, productDto.productName) &&
        Objects.equals(this.productDescription, productDto.productDescription) &&
        Objects.equals(this.quantity, productDto.quantity) &&
        Objects.equals(this.productCategory, productDto.productCategory) &&
        Objects.equals(this.pricePerUnit, productDto.pricePerUnit) &&
        Objects.equals(this.imageList, productDto.imageList) &&
        Objects.equals(this.unit, productDto.unit) &&
        Objects.equals(this.expiryDate, productDto.expiryDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, userId, productName, productDescription, quantity, productCategory, pricePerUnit, imageList, unit, expiryDate);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductDto {\n");
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    productName: ").append(toIndentedString(productName)).append("\n");
    sb.append("    productDescription: ").append(toIndentedString(productDescription)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    productCategory: ").append(toIndentedString(productCategory)).append("\n");
    sb.append("    pricePerUnit: ").append(toIndentedString(pricePerUnit)).append("\n");
    sb.append("    imageList: ").append(toIndentedString(imageList)).append("\n");
    sb.append("    unit: ").append(toIndentedString(unit)).append("\n");
    sb.append("    expiryDate: ").append(toIndentedString(expiryDate)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

