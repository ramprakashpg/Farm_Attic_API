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
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Gets or Sets ProductCategory
 */
@JsonAdapter(ProductCategory.Adapter.class)
public enum ProductCategory {
  
  FRUITS("FRUITS"),
  
  VEGETABLES("VEGETABLES"),
  
  CROPS("CROPS"),
  
  FERTILIZERS("FERTILIZERS");

  private String value;

  ProductCategory(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static ProductCategory fromValue(String value) {
    for (ProductCategory b : ProductCategory.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }

  public static class Adapter extends TypeAdapter<ProductCategory> {
    @Override
    public void write(final JsonWriter jsonWriter, final ProductCategory enumeration) throws IOException {
      jsonWriter.value(enumeration.getValue());
    }

    @Override
    public ProductCategory read(final JsonReader jsonReader) throws IOException {
      String value = jsonReader.nextString();
      return ProductCategory.fromValue(value);
    }
  }
}

