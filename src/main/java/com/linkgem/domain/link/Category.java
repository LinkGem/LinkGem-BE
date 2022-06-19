package com.linkgem.domain.link;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.linkgem.domain.common.BaseEntity;
import lombok.Getter;

@Getter
@Table(name = "link_category")
@Entity
public class Category extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "link_category_id")
	private Long id;

	private Long jobId;

	private String name;

	private Long order;
}
