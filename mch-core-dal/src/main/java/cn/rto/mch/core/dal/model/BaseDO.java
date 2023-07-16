package cn.rto.mch.core.dal.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ximenchuixue
 * @since 2021/4/15
 */
@Data
@SuperBuilder
public class BaseDO implements Serializable {

    private static final long serialVersionUID = -572554459386571415L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer isDel = 0;

    private String createdBy;
    private Date createdAt;

    private String updatedBy;
    private Date updatedAt;

    private Integer curVersion;


}
