/**
 * @package     com.choga3gan.delivery.global.domain
 * @class       Auditable
 * @description 생성일, 생성자, 수정일, 수정자, 삭제일, 삭제자 등 데이터 감사를 위한 필드를 포함하는 클래스
 *              데이터 감사가 필요한 엔티티 생성 시 상속하여 사용
 *
 * @author      허진경
 * @since       2025.10.30
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025.10.29    허진경          최초 생성
 * </pre>
 */

package com.choga3gan.delivery.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    protected String createdBy;

    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @LastModifiedBy
    protected String updatedBy;

    protected LocalDateTime deletedAt;

    protected String deletedBy;

    public void softDelete(String username) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = username;
    }
}
