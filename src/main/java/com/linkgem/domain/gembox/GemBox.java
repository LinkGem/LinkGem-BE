package com.linkgem.domain.gembox;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.linkgem.domain.common.BaseEntity;
import com.linkgem.domain.link.Link;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    public static final String DEFAULT_GEMBOX_NAME = "기본 잼박스";

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "user_id", nullable = true)
    private Long userId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "gem_box_id")
    private List<Link> links = new ArrayList<>();

    @Column(name = "is_default", nullable = false, columnDefinition = "boolean default false")
    private Boolean isDefault;

    @Builder
    private GemBox(String name, Long userId, boolean isDefault) {
        this.name = name;
        this.userId = userId;
        this.isDefault = isDefault;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void addLink(Link link) {
        links.add(link);
    }

    public boolean isEqual(Long id) {
        if (id == null) {
            return false;
        }

        return this.id.equals(id);
    }
}
