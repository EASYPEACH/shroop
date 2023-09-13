package com.easypeach.shroop.modules.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

	@DisplayName("등급 점수 100점 초과 시 100점으로 유지")
	@Test
	void gradeScoreLimitTest() {
		Member member = Member.createMember(
			"test123123",
			"test123123!",
			"test123123",
			"01000001111",
			Role.ROLE_USER,
			0L);

		member.addGradeScore(101L);

		Assertions.assertThat(member.getGradeScore()).isEqualTo(100);

	}
}
