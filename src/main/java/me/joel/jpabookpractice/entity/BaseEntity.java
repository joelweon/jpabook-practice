package me.joel.jpabookpractice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Date : 19. 4. 8
 * author : joel
 */

@MappedSuperclass
@Getter @Setter
public class BaseEntity {

    private Date createDate;
    private Date lastModifiedDate;
}
