package com.easypeach.shroop.modules.notification.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.easypeach.shroop.modules.member.domain.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(length = 255, nullable = false)
	private String title;

	@Column(length = 255, nullable = false)
	private String link;

	@Column(length = 255, nullable = false)
	private String message;

	@Column(nullable = false)
	private boolean checked;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDateTime createDate;

	public static Notification createNotification(
		final Member member,
		final String title,
		final String link,
		final String message,
		final boolean checked
	) {
		Notification notification = new Notification();
		notification.member = member;
		notification.title = title;
		notification.link = link;
		notification.message = message;
		notification.checked = checked;
		return notification;
	}

	public void changeChecked(final boolean checked) {
		this.checked = checked;
	}

}
