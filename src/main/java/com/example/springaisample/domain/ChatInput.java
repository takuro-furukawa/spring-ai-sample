package com.example.springaisample.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatInput implements Serializable {
    private static final long serialVersionUID = 1L;

    private String message;
}
