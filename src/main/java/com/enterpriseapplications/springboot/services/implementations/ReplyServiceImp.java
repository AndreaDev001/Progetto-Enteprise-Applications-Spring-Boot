package com.enterpriseapplications.springboot.services.implementations;


import com.enterpriseapplications.springboot.GenericModelAssembler;
import com.enterpriseapplications.springboot.config.exceptions.InvalidFormat;
import com.enterpriseapplications.springboot.data.dao.ReplyDao;
import com.enterpriseapplications.springboot.data.dao.ReviewDao;
import com.enterpriseapplications.springboot.data.dao.UserDao;
import com.enterpriseapplications.springboot.data.dto.input.create.CreateReplyDto;
import com.enterpriseapplications.springboot.data.dto.input.update.UpdateReplyDto;
import com.enterpriseapplications.springboot.data.dto.output.ReplyDto;
import com.enterpriseapplications.springboot.data.entities.Reply;
import com.enterpriseapplications.springboot.data.entities.Review;
import com.enterpriseapplications.springboot.data.entities.User;
import com.enterpriseapplications.springboot.services.interfaces.ReplyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReplyServiceImp implements ReplyService
{
    private final ReplyDao replyDao;
    private final ReviewDao reviewDao;
    private final UserDao userDao;
    private final ModelMapper modelMapper;
    private final GenericModelAssembler<Reply,ReplyDto> modelAssembler;
    private final PagedResourcesAssembler<Reply> pagedResourcesAssembler;

    public ReplyServiceImp(ReplyDao replyDao,ReviewDao reviewDao,UserDao userDao,ModelMapper modelMapper,PagedResourcesAssembler<Reply> pagedResourcesAssembler) {
        this.replyDao = replyDao;
        this.reviewDao = reviewDao;
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.modelAssembler = new GenericModelAssembler<>(Reply.class,ReplyDto.class,modelMapper);
    }

    @Override
    public PagedModel<ReplyDto> getReplies(Pageable pageable) {
        Page<Reply> replies = this.replyDao.findAll(pageable);
        return this.pagedResourcesAssembler.toModel(replies,modelAssembler);
    }

    @Override
    public ReplyDto getReply(UUID reviewID) {
        Reply reply = this.replyDao.findByReview(reviewID).orElseThrow();
        return this.modelMapper.map(reply,ReplyDto.class);
    }

    @Override
    @Transactional
    public ReplyDto createReply(CreateReplyDto createReplyDto) {
        User requiredUser = this.userDao.findById(UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Review requiredReview  = this.reviewDao.findById(createReplyDto.getReviewID()).orElseThrow();
        if(requiredUser.getId().equals(requiredReview.getWriter().getId()))
            throw new InvalidFormat("error.reply.invalidWriter");
        Reply reply = new Reply();
        reply.setWriter(requiredUser);
        reply.setReview(requiredReview);
        reply.setText(createReplyDto.getText());
        this.replyDao.save(reply);
        return this.modelMapper.map(reply,ReplyDto.class);
    }

    @Override
    @Transactional
    public ReplyDto updateReply(UpdateReplyDto updateReplyDto) {
        Reply reply = this.replyDao.findById(updateReplyDto.getReplyID()).orElseThrow();
        reply.setText(updateReplyDto.getText());
        this.replyDao.save(reply);
        return this.modelMapper.map(reply,ReplyDto.class);
    }

    @Override
    public PagedModel<ReplyDto> getWrittenReplies(UUID userID, Pageable pageable) {
        Page<Reply> replies = this.replyDao.findByWriter(userID, pageable);
        return this.pagedResourcesAssembler.toModel(replies,modelAssembler);
    }

    @Override
    @Transactional
    public void deleteReply(UUID replyID) {
        this.replyDao.findById(replyID).orElseThrow();
        this.replyDao.deleteById(replyID);
    }
}
