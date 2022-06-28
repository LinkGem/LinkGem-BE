package com.linkgem.domain.gembox;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.linkgem.domain.common.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(
    name = "gem_box",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "gembox_name_userid_UK",
            columnNames = {"name", "user_id"}
        )
    }
)
@Entity
public class GemBox extends BaseEntity {

    public static final String GEMBOX_NAME_REGEX = "^[a-zA-Zㄱ-ㅎ가-힣0-9 \\s]{1,8}$";

    public static final Long MAX_GEMBOX = 8L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "user_id", nullable = true)
    private Long userId;

    public GemBox(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }
}
