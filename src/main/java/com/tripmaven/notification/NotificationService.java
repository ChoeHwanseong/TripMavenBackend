package com.tripmaven.notification;

import java.util.List;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripmaven.joinchatting.JoinChattingDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
	
	private final ObjectMapper objectMapper;
	private final NotificationRepository notificationRepository;

	public NotificationDto create(NotificationDto dto) {
		return NotificationDto.toDto(notificationRepository.save(dto.toEntity()));
	}

	public List<NotificationDto> readAll(Long myId) {
		List<NotificationEntity> notificationList = notificationRepository.findAllByMemberId(myId);
		return objectMapper.convertValue(notificationList, objectMapper.getTypeFactory().defaultInstance().constructCollectionType(List.class, NotificationDto.class));
	}

}
