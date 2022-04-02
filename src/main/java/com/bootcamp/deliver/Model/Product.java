package com.bootcamp.deliver.Model;

import javax.persistence.*;
import org.hibernate.annotations.ManyToAny;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  
  @Column(nullable = false, length = 45)
  public String productName;

  @Column(nullable = false, length = 255)
  public Double minNumberofpallet;

  @Column(nullable = false, length = 20)
  public Double maxNumberofproduct;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "file_path")
  private String filePath;

  @Column(name = "file_type")
  private String fileType;

  @Column(name = "file_size")
  private String fileSize;


}
