package com.linkgem.domain.link;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.linkgem.domain.common.BaseEntity;
import com.linkgem.domain.gembox.GemBox;
import lombok.Getter;

@Getter
@Entity
public class Link extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "link_id")
	private Long id;

	private String url;

	private String memo;

	// TODO : USER Entity 와 1:1 연관관계 (or 아이디 참조방식)
	private Long writerId;

	// TODO : GEM_BOX Entity 와 1:N 연관관계
	//	@ManyToOne
	//	@JoinColumn(name = "ID")
	//	private GemBox gemBox;
	private Long gemBoxId;

	@ManyToOne
	@JoinColumn(name = "link_category_id")
	private Category category;
}
