package back.springbootdeveloper.seungchan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // 추가
public class BaseEntity {

  @Column(name = "update_date", nullable = false)
  @LastModifiedDate
  private LocalDateTime updateDate;

  @Column(name = "create_date", nullable = false)
  @CreationTimestamp
  private LocalDateTime createDate;
}
