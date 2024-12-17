package me.cher1shrxd.watodoserver.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode {
    EXPIRED_JWT_TOKEN(401, "Expired JWT token"),
    INVALID_JWT_TOKEN(401, "Invalid JWT token"),
    UNSUPPORTED_JWT_TOKEN(401, "Unsupported JWT token"),
    MALFORMED_JWT_TOKEN(401, "Malformed JWT token"),
    INVALID_TOKEN_TYPE(401, "Invalid token type"),
    INVALID_REFRESH_TOKEN(401, "Invalid refresh token"),

    // AUTH
    USERNAME_DUPLICATION(409, "Username is already in use"),
    USER_NOT_FOUND(404, "User not found"),
    WRONG_PASSWORD(401, "Wrong password"),
    EMAIL_DUPLICATION(409, "Email is already in use"),
    PAT_ALREADY_EXIST(409, "PAT is already in use"),

    // SPRINT
    SPRINT_NOT_FOUND(404, "Sprint not found"),

    // WBS
    WBS_NOT_FOUND(404, "Wbs not found"),

    // PROJECT
    PROJECT_NOT_FOUND(404, "project not found"),
    NOT_PROJECT_MEMBER(403, "You're not project member"),
    ALREADY_JOINED_PROJECT(409, "You're already member"),

    // TASK
    TASK_NOT_FOUND(404, "Task not found"),
    BRANCH_ALREADY_CONNECTED(409, "Branch is already connected"),

    //GITHUB
    CANNOT_GET_BRANCHES(400, "Can not get branches"),

    // Global
    METHOD_ARGUMENT_NOT_SUPPORTED(405, "Method argument not supported"),
    NO_HANDLER_FOUND(404, "No handler found")
    ;

    private final int status;
    private final String message;
}
