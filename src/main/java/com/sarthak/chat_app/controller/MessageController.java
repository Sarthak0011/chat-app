package com.sarthak.chat_app.controller;

import com.sarthak.chat_app.dto.MessageDto;
import com.sarthak.chat_app.responses.ApiResponse;
import com.sarthak.chat_app.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;

    @GetMapping("/{conversationId}/messages/")
    public ResponseEntity<ApiResponse> getMessages(@PathVariable Long conversationId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size,
                                                   @RequestParam(defaultValue = "created_at") String sortBy,
                                                   @RequestParam(defaultValue = "false") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        List<MessageDto> messages = messageService.getMessages(conversationId, pageable);
        return ResponseEntity
                .status(OK)
                .body(new ApiResponse(true, messages, "Messages fetched.", null));
    }


}
