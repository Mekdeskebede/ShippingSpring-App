package com.bootcamp.deliver.Model;

import javax.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor


@Entity
@Table(name = "Users")
@DynamicInsert
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "address_id", referencedColumnName = "id")
  private Address address;

  @Column(nullable = false, unique = true, length = 45)
  private String email;

  @Column(nullable = false, length = 45)
  private String firstname;

  @Column(nullable = false, length = 45)
  private String lastname;

  @Column(nullable = false, length = 45)
  private String Companyname;

  @Column(nullable = false, unique = true, length = 64)
  private String password;

  @Column
  private String role;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "file_path")
  private String filePath;

  @Column(name = "file_type")
  private String fileType;

  @Column(name = "file_size")
  private String fileSize;


}
 